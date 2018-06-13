/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuan.data.messages.Header;
import com.zuan.data.messages.sd.SdHeader;
import com.zuan.data.messages.sd.SdMessage;
import com.zuan.data.model.SignalData;
import com.zuan.data.model.SignalValue;
import com.zuan.parser.Parser;
import com.zuan.parser.ParserConfiguration;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.Codecs;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.imp.ParserConfigurationImpl;
import com.zuan.parser.imp.ParserImp;
import com.zuan.parser.imp.ParserMetaData;
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
  private static final Codec<Header> HEADER_CODEC = Codecs.create(Header.class);

  /** The Constant SD_HEADER_CODEC. */
  private static final Codec<SdHeader> SD_HEADER_CODEC = Codecs.create(SdHeader.class);

  /** The Constant OBS_HEADER_SIZE. */
  private static final int OBS_HEADER_SIZE;

  /** The codec map. */
  private static Map<String, Codec< ? >> codecMap = new TreeMap<>();

  static {
    OBS_HEADER_SIZE = HEADER_CODEC.getSize().eval(null) / BITS_PER_BYTE;
  }

  /** The parser. */
  private static final Parser PARSER = new ParserImp();

  /**
   * Instantiates a new parser utils.
   */
  private ParserUtils() {
    super();
  }

  /**
   * Gets the sd value.
   *
   * @param binary
   *          the binary
   * @param signals
   *          the signals
   * @param length
   *          the length
   * @return the sd value
   * @throws ParserException
   *           the parser exception
   */
  public static Set<SignalValue> getSignalValues(@Nonnull final byte[] binary,
      final List<SignalData> signals, final int length) throws ParserException {
    if (binary.length != length) {
      throw new ParserException(
          "Binaty length is invalid: " + binary.length + " != " + length);
    }
    final ParserConfiguration parserConfiguration = new ParserConfigurationImpl();
    signals.forEach(signal -> {
      try {
        parserConfiguration.addSignal(new ParserMetaData(signal));
      } catch (Exception e) { // NOSONAR
        LOGGER.warn("{}", e.getMessage());
      }
    });
    return PARSER.parseEntireDataToSet(binary, parserConfiguration);
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
  public static Header decodeHeader(final byte[] buffer) throws ParserException {
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
  public static SdHeader parseSdDataHeaderInfo(byte[] buffer) throws ParserException {
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
