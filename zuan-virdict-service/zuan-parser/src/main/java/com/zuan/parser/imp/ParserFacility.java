/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuan.parser.ValueType;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.signal.SignalInfomation;

/**
 * The Class ParserFacility.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class ParserFacility {

  /** The logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ParserFacility.class);

  /** The Constant listDestinationTypeNotScalingAndPlus. */
  private static final Map<ValueType, ValueType> listDestinationTypeNotScalingAndPlus =
      getListDestinationTypeNotScalingAndPlus();

  /**
   * Instantiates a new facility.
   */
  private ParserFacility() {
    // singleton
  }

  /**
   * Gets the list destination type not scaling and plus.
   *
   * @return the list destination type not scaling and plus
   */
  public static Map<ValueType, ValueType> getListDestinationTypeNotScalingAndPlus() {
    Map<ValueType, ValueType> result = new EnumMap<>(ValueType.class);
    result.put(ValueType.BOOL, ValueType.BOOL);
    result.put(ValueType.STRING, ValueType.STRING);
    result.put(ValueType.BYTEARRAY, ValueType.BYTEARRAY);
    result.put(ValueType.COMBINE, ValueType.COMBINE);
    return result;
  }

  /**
   * Transform byte data.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object transformByteData(final Object value, final SignalInfomation signal) {
    return (Byte) value * signal.getScalingFactor() + signal.getPlusValue();
  }

  /**
   * Transform short data.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object transformShortData(final Object value, final SignalInfomation signal) {
    return (Short) value * signal.getScalingFactor() + signal.getPlusValue();
  }

  /**
   * Transform int data.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object transformIntData(final Object value, final SignalInfomation signal) {
    return (Integer) value * signal.getScalingFactor() + signal.getPlusValue();
  }

  /**
   * Transform long data.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object transformLongData(final Object value, final SignalInfomation signal) {
    return (Long) value * signal.getScalingFactor() + signal.getPlusValue();
  }

  /**
   * Transform float data.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object transformFloatData(final Object value, final SignalInfomation signal) {
    return (Float) value * signal.getScalingFactor() + signal.getPlusValue();
  }

  /**
   * Transform double data.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object transformDoubleData(final Object value, final SignalInfomation signal) {
    return (Double) value * signal.getScalingFactor() + signal.getPlusValue();
  }

  /**
   * Parses the data item.
   *
   * @param buffer
   *          the buffer
   * @param signal
   *          the signal
   * @param bitPosition
   *          the bit position
   * @return the object
   */
  public static Object parseDataItem(final BitBuffer buffer, final SignalInfomation signal,
      final long bitPosition) {
    Object objResult = null;
    switch (signal.getDestinationType()) {
    case BOOL:
      objResult = BitBufferFacility.readAsBoolean(buffer, bitPosition);
      break;
    case BYTE:
      objResult = BitBufferFacility.readAsByte(buffer, bitPosition, signal.getBitLength());
      break;
    case SHORT:
      objResult = BitBufferFacility.readAsShort(buffer, bitPosition, signal.getBitLength());
      break;
    case INT:
      objResult = BitBufferFacility.readAsInt(buffer, bitPosition, signal.getBitLength());
      break;
    case LONG:
      objResult = BitBufferFacility.readAsLong(buffer, bitPosition, signal.getBitLength());
      break;
    case STRING:
      objResult = BitBufferFacility.readAsString(buffer, bitPosition, signal.getBitLength());
      break;
    case FLOAT:
      objResult = BitBufferFacility.readAsFloat(buffer, bitPosition, signal.getBitLength());
      break;
    case DOUBLE:
      objResult = BitBufferFacility.readAsDouble(buffer, bitPosition, signal.getBitLength());
      break;
    case BYTEARRAY:
      objResult = BitBufferFacility.readAsBuffer(buffer, bitPosition, signal.getBitLength());
      break;
    case COMBINE:
      objResult = BitBufferFacility.readAsCombine(buffer, bitPosition, signal.getBitLength(),
          signal.getBitLengthforCombineType());
      break;
    default:
      LOGGER.error("Sinal code: {}, error: not handled", signal.getSignalCode());
      break;
    }
    return objResult;
  }

  /**
   * Transform data.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object transformData(final Object value, final SignalInfomation signal) {
    Object objResult = null;
    switch (signal.getDestinationType()) {
    case BYTE:
      objResult = ParserFacility.transformByteData(value, signal);
      break;
    case SHORT:
      objResult = ParserFacility.transformShortData(value, signal);
      break;
    case INT:
      objResult = ParserFacility.transformIntData(value, signal);
      break;
    case LONG:
      objResult = ParserFacility.transformLongData(value, signal);
      break;
    case FLOAT:
      objResult = ParserFacility.transformFloatData(value, signal);
      break;
    case DOUBLE:
      objResult = ParserFacility.transformDoubleData(value, signal);
      break;
    default:
      LOGGER.error("Signal code {} Shouldn't apply a scale for {} type",
          signal.getSignalCode(), signal.getDestinationType());
      break;
    }

    return objResult;
  }

  /**
   * Convert value to double.
   *
   * @param value
   *          the value
   * @return the double
   */
  public static double convertValueToDouble(final Object value) {
    Double dResult;
    switch (value.getClass().getCanonicalName()) {
    case "java.lang.Byte":
    case "java.lang.Short":
    case "java.lang.Integer":
    case "java.lang.Long":
    case "java.lang.Double":
      dResult = Double.valueOf(value.toString());
      break;
    default:
      dResult = Double.NaN;
      LOGGER.error("Cannot convert the {} value to double", value);
      break;
    }
    return dResult;
  }

  /**
   * Checks if is less than min.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return true, if is less than min
   */
  public static boolean isLessThanMin(final Object value, final SignalInfomation signal) {
    boolean bResult;
    final Double convertedValue = convertValueToDouble(value);
    if (Double.isNaN(convertedValue)) {
      bResult = false;
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Shouldn't apply a check min value on other than types: "
            + "byte, short, int, long and double");
      }
    } else {
      bResult = Double.compare(convertedValue, signal.getMinValue()) >= 0;
    }
    return bResult;
  }

  /**
   * Checks if is greater than max.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return true, if is greater than max
   */
  public static boolean isGreaterThanMax(final Object value, final SignalInfomation signal) {
    boolean bResult;
    final Double convertedValue = convertValueToDouble(value);
    if (Double.isNaN(convertedValue)) {
      bResult = false;
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Shouldn't apply a check max value on other than types: "
            + "byte, short, int, long and double");
      }
    } else {
      bResult = Double.compare(convertedValue, signal.getMaxValue()) <= 0;
    }
    return bResult;
  }

  /**
   * Parses the double value to destination type.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object parseDoubleValueToDestinationType(final Double value,
      final SignalInfomation signal) {
    Object objResult = null;
    switch (signal.getDestinationType()) {
    case BYTE:
      objResult = value.byteValue();
      break;
    case SHORT:
      objResult = value.shortValue();
      break;
    case INT:
      objResult = value.intValue();
      break;
    case LONG:
      objResult = value.longValue();
      break;
    case FLOAT:
      objResult = value.floatValue();
      break;
    case DOUBLE:
      objResult = value;
      break;
    default:
      LOGGER.error("Cannot cast value {} of signal code {} to {} destination type", value,
          signal.getSignalCode(), signal.getDestinationType());
      break;
    }
    return objResult;
  }

  /**
   * Parser value to destination type.
   *
   * @param value
   *          the value
   * @param signal
   *          the signal
   * @return the object
   */
  public static Object parserValueToDestinationType(final Object value,
      final SignalInfomation signal) {

    // Check current type for reverting to if different
    if (!Double.isNaN(signal.getScalingFactor()) || !Double.isNaN(signal.getPlusValue())) {
      return parseDoubleValueToDestinationType((Double) value, signal);
    }

    return value;
  }

  /**
   * Process data.
   *
   * @param signal
   *          the signal
   * @param value
   *          the value
   * @return the object
   */
  public static Object processDecodedValue(final SignalInfomation signal, final Object value) {
    Object objResult = value;
    // Check scale, plus
    if (objResult != null
        && listDestinationTypeNotScalingAndPlus.get(signal.getDestinationType()) == null) {
      if (!Double.isNaN(signal.getScalingFactor()) || !Double.isNaN(signal.getPlusValue())) {
        // Scale the output value
        objResult = transformData(objResult, signal);
      }

      if (objResult != null && !Double.isNaN(signal.getMinValue())
          && !isLessThanMin(objResult, signal)) {
        // Checks min of the output value
        objResult = null;
      }

      if (objResult != null && !Double.isNaN(signal.getMaxValue())
          && !isGreaterThanMax(objResult, signal)) {
        // Checks max of the output value
        objResult = null;
      }

      if (objResult != null) {
        objResult = parserValueToDestinationType(objResult, signal);
      }
    }

    return objResult;
  }
}
