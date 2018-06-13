/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuan.data.model.SignalValue;
import com.zuan.parser.Parser;
import com.zuan.parser.ParserConfiguration;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.buffer.DefaultBitBuffer;
import com.zuan.parser.signal.SignalInfomation;

/**
 * The Class SdParserImp.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class ParserImp implements Parser {

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ParserImp.class);

  /** The Constant VALUE. */
  private static final String VALUE = "value";

  /**
   * Parses the.
   *
   * @param packet
   *          the packet
   * @param config
   *          the config
   * @return the object
   */
  public Object parse(final byte[] packet, final SignalInfomation config) {
    // Create BitBuffer object with the inputed byte array
    final BitBuffer buffer = new DefaultBitBuffer(ByteBuffer.wrap(packet));
    return parse(buffer, config);
  }

  /**
   * Parses the.
   *
   * @param buffer
   *          the buffer
   * @param config
   *          the config
   * @return the object
   */
  public Object parse(final BitBuffer buffer, final SignalInfomation config) {
    Object objDecodedValue = null;
    if (BitBufferFacility.checkAvailableData(buffer, config)) {
      // Parse the data item and put its result to the signal
      objDecodedValue = ParserFacility.parseDataItem(buffer, config,
          BitBufferFacility.calculateBitPos(config));
      if (objDecodedValue != null) {
        // Transform, check min, and check max and then convert to destination
        // type if any
        objDecodedValue = ParserFacility.processDecodedValue(config, objDecodedValue);
      }

      // check MVEL expression
      final Serializable serialExpression = config.getExpresstion();
      if (serialExpression == null) {
        return objDecodedValue;
      }
      objDecodedValue = executeMVEL(objDecodedValue, serialExpression);
    }
    return objDecodedValue;
  }

  /**
   * Execute mvle.
   *
   * @param obj
   *          the obj
   * @param serialExpression
   *          the serial expression
   * @return the object
   */
  private Object executeMVEL(final Object obj, final Serializable serialExpression) {
    Map<String, Object> vars = new HashMap<>();
    vars.put(VALUE, obj);
    return MVEL.executeExpression(serialExpression, vars);
  }

  /**
   * Checks if is parser can not parse.
   *
   * @param packet
   *          the packet
   * @param configuration
   *          the configuration
   * @return true, if is parser can not parse
   */
  private boolean isParserCanNotParse(final byte[] packet,
      final ParserConfiguration configuration) {
    return packet == null || configuration == null;
  }

  /**
   * Parses the entire data to sd map.
   *
   * @param packet
   *          the packet
   * @param configuration
   *          the configuration
   * @return the map
   */
  @SuppressWarnings("unchecked")
  @Override
  public Set<SignalValue> parseEntireDataToSet(final byte[] packet,
      final ParserConfiguration configuration) {

    // is Parser can not parse
    if (isParserCanNotParse(packet, configuration)) {
      return new HashSet<>();
    }

    // Create BitBuffer object with the inputed byte array
    final BitBuffer buffer = new DefaultBitBuffer(ByteBuffer.wrap(packet));

    final Set<SignalValue> result = new HashSet<>();
    for (SignalInfomation signalConf : configuration.getSignals()) {
      final String signalCode = signalConf.getSignalCode();
      try {
        final Object objDecodedValue = parse(buffer, signalConf);
        final SignalValue object = new SignalValue();
        object.setBitLength(signalConf.getBitLength());
        object.setBitOffset(signalConf.getBitOffset());
        object.setByteOffset(signalConf.getByteOffset());
        object.setSignalName(signalConf.getSignalName());
        object.setSignalCode(signalCode);
        object.setValue(objDecodedValue);
        result.add(object);
      } catch (Exception e) { // NOSONAR
        LOGGER.warn("Couldn't get value of signal[{}], cause: {}", signalCode, e.getMessage());
      }
    }
    return result;
  }

  /**
   * Parses the entire data to map.
   *
   * @param packet
   *          the packet
   * @param configuration
   *          the configuration
   * @return the map
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, SignalValue> parseEntireDataToMap(final byte[] packet,
      final ParserConfiguration configuration) {
    // is Parser can not parse
    if (isParserCanNotParse(packet, configuration)) {
      return new HashMap<>();
    }
    // Create BitBuffer object with the inputed byte array
    final BitBuffer buffer = new DefaultBitBuffer(ByteBuffer.wrap(packet));
    final Map<String, SignalValue> result = new HashMap<>();
    for (SignalInfomation signalConf : configuration.getSignals()) {
      final String signalCode = signalConf.getSignalCode();
      try {
        final Object objDecodedValue = parse(buffer, signalConf);
        final SignalValue object = new SignalValue();
        object.setBitLength(signalConf.getBitLength());
        object.setBitOffset(signalConf.getBitOffset());
        object.setByteOffset(signalConf.getByteOffset());
        object.setSignalName(signalConf.getSignalName());
        object.setSignalCode(signalCode);
        object.setValue(objDecodedValue);
        if (StringUtils.isBlank(signalCode)) {
          result.put(signalConf.getSignalName(), object);
        } else {
          result.put(signalCode, object);
        }
      } catch (Exception e) { // NOSONAR
        LOGGER.warn("Couldn't get value of signal[{}], cause: {}", signalCode, e.getMessage());
      }
    }
    return result;
  }
}
