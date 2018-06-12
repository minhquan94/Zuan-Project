/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.zuan.data.messages.HeaderInformationMess;
import com.zuan.data.messages.sd.SdHeaderInformation;
import com.zuan.data.messages.sd.SdMessage;
import com.zuan.data.model.SdSignalData;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.Codecs;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.common.SdLength;
import com.zuan.parser.common.SdObject;
import com.zuan.parser.imp.Parser;
import com.zuan.parser.imp.ParserConfiguration;
import com.zuan.parser.imp.ParserConfigurationImpl;
import com.zuan.parser.imp.ParserMetaData;
import com.zuan.parser.imp.SdParserImp;
import com.zuan.parser.imp.exception.ParserException;

/**
 * The Class ParserUtils.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class ParserUtils {

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ParserUtils.class);

  /** The Constant BITS_PER_BYTE. */
  private static final int BITS_PER_BYTE = 8;

  /** The Constant HEADER_CODEC : codec of HeaderInformationMess. */
  private static final Codec<HeaderInformationMess> HEADER_CODEC =
      Codecs.create(HeaderInformationMess.class);

  /** The Constant SD_HEADER_CODEC. */
  private static final Codec<SdHeaderInformation> SD_HEADER_CODEC =
      Codecs.create(SdHeaderInformation.class);

  /** The Constant SD_NUMBER_POSITION. */
  private static final int SD_NUMBER_POSITION = 12;

  /** The Constant OBS_HEADER_SIZE. */
  private static final int OBS_HEADER_SIZE;

  /** The codec map. */
  private static Map<String, Codec< ? >> codecMap = new TreeMap<>();

  static {
    OBS_HEADER_SIZE = HEADER_CODEC.getSize().eval(null) / BITS_PER_BYTE;
  }

  /** The parser. */
  private static final Parser PARSER = new SdParserImp();

  /**
   * Instantiates a new parser utils.
   */
  private ParserUtils() {
    super();
  }

  /**
   * Gets the sd object value.
   *
   * @param sdBinary
   *          the sd binary
   * @param trainProject
   *          the train project
   * @param signals
   *          the signals
   * @param mapLength
   *          the map length
   * @return the sd object value
   * @throws ParserException
   *           the parser exception
   */
  public static Set<SdObject> getSdObjectValue(final byte[] sdBinary,
      final String trainProject, final List<SdSignalData> signals,
      final List<SdLength> mapLength) throws ParserException {
    return getSdObjectValue(sdBinary, null, trainProject, signals, SD_NUMBER_POSITION,
        mapLength);
  }

  /**
   * Gets the sd object value.
   *
   * @param sdBinary
   *          the sd binary
   * @param trainProject
   *          the train project
   * @param signals
   *          the signals
   * @param sdNumberPosition
   *          the sd number position
   * @param mapLength
   *          the map length
   * @return the sd object value
   * @throws ParserException
   *           the parser exception
   */
  public static Set<SdObject> getSdObjectValue(final byte[] sdBinary,
      final String trainProject, final List<SdSignalData> signals, final int sdNumberPosition,
      final List<SdLength> mapLength) throws ParserException {
    return getSdObjectValue(sdBinary, null, trainProject, signals, sdNumberPosition,
        mapLength);
  }

  /**
   * Gets the sd object value.
   *
   * @param sdBinary
   *          the sd binary
   * @param signalCode
   *          the signal code
   * @param trainProject
   *          the train project
   * @param signals
   *          the signals
   * @param mapLength
   *          the map length
   * @return the sd object value
   * @throws ParserException
   *           the parser exception
   */
  public static Set<SdObject> getSdObjectValue(final byte[] sdBinary,
      final Set<String> signalCode, final String trainProject,
      final List<SdSignalData> signals, final List<SdLength> mapLength)
      throws ParserException {
    return getSdObjectValue(sdBinary, signalCode, trainProject, signals, SD_NUMBER_POSITION,
        mapLength);
  }

  /**
   * Gets the sd value.
   *
   * @param sdBinary
   *          the sd binary
   * @param signalCode
   *          the signal code
   * @param trainProject
   *          the train project
   * @param signals
   *          the signals
   * @param sdNumberPosition
   *          the sd number position
   * @param mapLength
   *          the map length
   * @return the sd value
   * @throws ParserException
   *           the parser exception
   */
  public static Set<SdObject> getSdObjectValue(final byte[] sdBinary,
      final Set<String> signalCode, final String trainProject,
      final List<SdSignalData> signals, final int sdNumberPosition,
      final List<SdLength> mapLength) throws ParserException {
    final Set<SdObject> reval = new HashSet<>();
    // Get SD Number
    int sdNumber = sdBinary[sdNumberPosition];
    int sdLength = 0;
    for (SdLength e : mapLength) {
      if (e.getTrainProject().equalsIgnoreCase(trainProject)) {
        sdLength = e.getMapping().get(String.valueOf(sdNumber));
        break;
      }
    }
    if (sdBinary.length != sdLength) {
      LOGGER.error("Invalid SD{} length {} != {}", sdNumber, sdBinary.length, sdLength);
      return reval;
    }
    final ParserConfiguration parserConfiguration = new ParserConfigurationImpl();
    signals.forEach(signal -> {
      try {
        final boolean isValidSdNumber =
            StringUtils.equals(String.valueOf(sdNumber), signal.getOriginatingFile());
        final boolean isFilterSignal =
            CollectionUtils.isEmpty(signalCode) || (!CollectionUtils.isEmpty(signalCode)
                && signalCode.contains(signal.getSignalCode()));
        if (isValidSdNumber && isFilterSignal) {
          parserConfiguration.addSignal(new ParserMetaData(signal));
        }
      } catch (Exception e) { // NOSONAR
        LOGGER.warn("{}", e.getMessage());
      }
    });
    return PARSER.parseEntireDataToSet(sdBinary, parserConfiguration);
  }

  /**
   * Gets the sd binary.
   *
   * @param byteArr
   *          the binary input
   * @return the sd binary
   * @throws ParserException
   *           the parser exception
   */
  public static byte[] getSdBinary(final byte[] byteArr) throws ParserException {
    byte[] sdBinary;
    if (byteArr[0] == (byte) 0xff && byteArr[1] == (byte) 0x06) {
      final SdMessage sdMessage = decode(byteArr, SdMessage.class);
      sdBinary = sdMessage.getSdRecord();
    } else {
      sdBinary = byteArr;
    }
    return sdBinary;
  }

  /**
   * Gets the obs header size.
   *
   * @return the obs header size
   */
  public static int getObsHeaderSize() {
    return OBS_HEADER_SIZE;
  }

  /**
   * Decode header.
   *
   * @param buffer
   *          the byte buffer
   * @return the header information mess
   * @throws ParserException
   *           the parser exception
   */
  public static HeaderInformationMess decodeHeader(final byte[] buffer)
      throws ParserException {
    try {
      return Codecs.decode(HEADER_CODEC, buffer);
    } catch (DecodingException e) {
      throw new ParserException(e.getMessage(), e);
    }
  }

  /**
   * Decode byte array.
   *
   * @param <T>
   *          the generic type
   * @param buffer
   *          the buffer
   * @param clazz
   *          the class
   * @return the object
   * @throws ParserException
   *           the parser exception
   */
  @SuppressWarnings("unchecked")
  public static <T> T decode(final byte[] buffer, final Class<T> clazz)
      throws ParserException {
    final String canonicalName = clazz.getCanonicalName();
    Codec<T> codec = (Codec<T>) codecMap.get(canonicalName);
    if (codec == null) {
      codec = Codecs.create(clazz);
      codecMap.put(canonicalName, codec);
    }
    T obj = null;
    try {
      obj = Codecs.decode(codec, buffer);
    } catch (DecodingException e) {
      throw new ParserException(e.getMessage(), e);
    }
    return obj;
  }

  /**
   * Encode object to byte array.
   *
   * @param <T>
   *          the generic type
   * @param object
   *          the object
   * @return the byte[]
   * @throws ParserException
   *           the parser exception
   */
  @SuppressWarnings("unchecked")
  public static <T> byte[] encode(final Object object) throws ParserException {
    Class<T> clazz = (Class<T>) object.getClass();
    final String canonicalName = clazz.getCanonicalName();
    Codec<T> codec = (Codec<T>) codecMap.get(canonicalName);
    if (codec == null) {
      codec = Codecs.create(clazz);
      codecMap.put(canonicalName, codec);
    }
    byte[] buf = null;
    try {
      buf = Codecs.encode((T) object, codec);
    } catch (IOException e) {
      throw new ParserException(e.getMessage(), e);
    }
    return buf;
  }

  /**
   * Parses the sd data header info.
   *
   * @param buffer
   *          the buffer
   * @return the sd data header info
   * @throws ParserException
   *           the parser exception
   */
  public static SdHeaderInformation parseSdDataHeaderInfo(byte[] buffer)
      throws ParserException {
    try {
      return Codecs.decode(SD_HEADER_CODEC, buffer);
    } catch (DecodingException e) {
      throw new ParserException(e.getMessage(), e);
    }
  }

  /**
   * Gets the bytes sd header info.
   *
   * @param sdRecord
   *          the sd record
   * @return the bytes sd header info
   */
  public static byte[] getBytesSdHeaderInfo(byte[] sdRecord) {
    int sdHeaderSize = SD_HEADER_CODEC.getSize().eval(null) / BITS_PER_BYTE;
    return Arrays.copyOf(sdRecord, sdHeaderSize);
  }
}
