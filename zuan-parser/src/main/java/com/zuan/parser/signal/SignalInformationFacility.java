/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.signal;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.zuan.parser.ValueType;
import com.zuan.parser.imp.exception.ApplyingMaxValueException;
import com.zuan.parser.imp.exception.ApplyingMinValueException;
import com.zuan.parser.imp.exception.ApplyingPlusValueException;
import com.zuan.parser.imp.exception.ApplyingScalingFactorException;
import com.zuan.parser.imp.exception.BitLengthException;
import com.zuan.parser.imp.exception.BitOffSetException;
import com.zuan.parser.imp.exception.ParserException;
import com.zuan.parser.imp.exception.SwappingMinMaxException;

/**
 * The Class SignalInformationFacility.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class SignalInformationFacility {

  /** The Constant MAX_BOOL_BIT_LENGTH. */
  public static final int MAX_BOOL_BIT_LENGTH = 1;

  /** The Constant MAX_BYTE_BIT_LENGTH. */
  public static final int MAX_BYTE_BIT_LENGTH = 8;

  /** The Constant MAX_SHORT_BIT_LENGTH. */
  public static final int MAX_SHORT_BIT_LENGTH = 16;

  /** The Constant MAX_FLOAT_BIT_LENGTH. */
  public static final int MAX_FLOAT_BIT_LENGTH = 32;

  /** The Constant MAX_INT_BIT_LENGTH. */
  public static final int MAX_INT_BIT_LENGTH = 32;

  /** The Constant MAX_DOUBLE_BIT_LENGTH. */
  public static final int MAX_DOUBLE_BIT_LENGTH = 64;

  /** The Constant MAX_LONG_BIT_LENGTH. */
  public static final int MAX_LONG_BIT_LENGTH = 64;

  /** The Constant MAX_LONG_BIT_LENGTH. */
  public static final int MAX_BIT_OFFSET = 7;

  /** The Constant MAX_CALENDAR_BIT_LENGTH. */
  public static final int MAX_CALENDAR_BIT_LENGTH = 56;

  /** The Constant MAX_HOUR_MINUTES_BIT_LENGTH. */
  public static final int MAX_HOUR_MINUTES_BIT_LENGTH = 16;

  /** The Constant MAX_SERVICE_CAR_NUMBER_BIT_LENGTH. */
  public static final int MAX_SERVICE_CAR_NUMBER_BIT_LENGTH = 8;

  /** The Constant MIN_CALENDAR_BIT_LENGTH. */
  public static final int MIN_CALENDAR_BIT_LENGTH = 24;

  /**
   * Instantiates a new facility.
   */
  private SignalInformationFacility() {
    // singleton
  }

  /**
   * Gets the exception list of applying scale value.
   *
   * @return the exception list of applying scale value
   */
  public static Set<ValueType> getExceptionListOfApplyingScaleValue() {
    final LinkedHashSet<ValueType> exceptionList = new LinkedHashSet<>();
    exceptionList.add(ValueType.NONE);
    exceptionList.add(ValueType.STRING);
    exceptionList.add(ValueType.BYTEARRAY);
    exceptionList.add(ValueType.BOOL);
    exceptionList.add(ValueType.COMBINE);
    return exceptionList;
  }

  /**
   * Gets the exception list of applying min max value.
   *
   * @return the exception list of applying min max value
   */
  public static Set<ValueType> getExceptionListOfApplyingMinMaxValue() {
    final LinkedHashSet<ValueType> exceptionList = new LinkedHashSet<>();
    exceptionList.add(ValueType.NONE);
    exceptionList.add(ValueType.STRING);
    exceptionList.add(ValueType.BYTEARRAY);
    exceptionList.add(ValueType.BOOL);
    exceptionList.add(ValueType.COMBINE);
    return exceptionList;
  }

  /**
   * Gets the maximum bit length of value type.
   *
   * @return the maximum bit length of value type
   */
  public static Map<ValueType, Integer> getMaximumBitLengthOfValueType() {
    final Map<ValueType, Integer> datatypeMapping = new ConcurrentHashMap<>();
    datatypeMapping.put(ValueType.BOOL, SignalInformationFacility.MAX_BOOL_BIT_LENGTH);
    datatypeMapping.put(ValueType.BYTE, SignalInformationFacility.MAX_BYTE_BIT_LENGTH);
    datatypeMapping.put(ValueType.SHORT, SignalInformationFacility.MAX_SHORT_BIT_LENGTH);
    datatypeMapping.put(ValueType.FLOAT, SignalInformationFacility.MAX_FLOAT_BIT_LENGTH);
    datatypeMapping.put(ValueType.INT, SignalInformationFacility.MAX_INT_BIT_LENGTH);
    datatypeMapping.put(ValueType.DOUBLE, SignalInformationFacility.MAX_DOUBLE_BIT_LENGTH);
    datatypeMapping.put(ValueType.LONG, SignalInformationFacility.MAX_LONG_BIT_LENGTH);

    return datatypeMapping;
  }

  /**
   * Validate byte array.
   *
   * @param signalInfo
   *          the signal info
   * @throws BitLengthException
   *           the bit length exception
   */
  public static void validateByteArray(final SignalInfomation signalInfo)
      throws BitLengthException {

    final boolean isDestinationTypeByteArray =
        signalInfo.getDestinationType() == ValueType.BYTEARRAY;

    if (isDestinationTypeByteArray
        && (signalInfo.getBitLength() % SignalInformationFacility.MAX_BYTE_BIT_LENGTH != 0)) {
      throw new BitLengthException(String.format(
          "Signal Code %s that ValueType is ByteArray but BitLength %s isn't divisible by 8",
          signalInfo.getSignalCode(), signalInfo.getBitLength()));
    }

  }

  /**
   * Validate bit off set.
   *
   * @param signalInfo
   *          the signal configuration
   * @throws BitOffSetException
   *           the bit off set exception
   */
  public static void validateBitOffSet(final SignalInfomation signalInfo)
      throws BitOffSetException {
    // validate min bit offset
    // OR not define bit off set
    if (signalInfo.getBitOffset() < 0) {
      throw new BitOffSetException(
          String.format("signal code %s has bit off set < 0", signalInfo.getSignalCode()));
    }

    // validate max bit offset
    if (signalInfo.getBitOffset() > SignalInformationFacility.MAX_BIT_OFFSET) {
      throw new BitOffSetException(String.format("signal code %s has bit off set > %s",
          signalInfo.getSignalCode(), SignalInformationFacility.MAX_BIT_OFFSET));
    }
  }

  /**
   * Validate bit length.
   *
   * @param signalInfo
   *          the signal info
   * @throws BitLengthException
   *           the bit length exception
   */
  public static void validateBitLength(final SignalInfomation signalInfo)
      throws BitLengthException {
    if (signalInfo.getBitLength() < 1) {
      throw new BitLengthException(
          String.format("signal code %s bit length < 1", signalInfo.getSignalCode()));
    }
  }

  /**
   * Check define min max field.
   *
   * @param signalInfo
   *          the signal info
   * @throws ParserException
   *           the parser exception
   */
  public static void checkDefineMinMaxField(final SignalInfomation signalInfo)
      throws ParserException {
    // invalid define MinValue
    try {
      if (Double.compare(signalInfo.getMinValue(), Double.NaN) != 0) {
        throw new ApplyingMinValueException(String.format(
            "signal code %s - shouldn't define min value %s for destination type %s",
            signalInfo.getSignalCode(), signalInfo.getMinValue(),
            signalInfo.getDestinationType()));
      }
      // invalid define MaxValue
      if (Double.compare(signalInfo.getMaxValue(), Double.NaN) != 0) {
        throw new ApplyingMaxValueException(String.format(
            "signal code %s - shouldn't define max value %s for destination type %s",
            signalInfo.getSignalCode(), signalInfo.getMaxValue(),
            signalInfo.getDestinationType()));
      }
    } catch (ApplyingMinValueException | ApplyingMaxValueException e) {
      throw new ParserException(e.getMessage(), e);
    }
  }

  /**
   * Validate min max field.
   *
   * @param signalInfo
   *          the signal info
   * @throws SwappingMinMaxException
   *           the swapping min max exception
   */
  public static void validateMinMaxField(final SignalInfomation signalInfo)
      throws SwappingMinMaxException {
    // Min > Max OR Max < Min
    if (Double.compare(signalInfo.getMinValue(), signalInfo.getMaxValue()) > 0
        && Double.compare(signalInfo.getMinValue(), Double.NaN) != 0
        && Double.compare(signalInfo.getMaxValue(), Double.NaN) != 0) {
      throw new SwappingMinMaxException(
          String.format("signal code %s has min value %s bigger than max value %s",
              signalInfo.getSignalCode(), signalInfo.getMinValue(), signalInfo.getMaxValue()));
    }
  }

  /**
   * Check scaling factor.
   *
   * @param signalInfo
   *          the signal configuration
   * @throws ApplyingScalingFactorException
   *           the applying scaling factor exception
   */
  public static void checkScalingFactor(final SignalInfomation signalInfo)
      throws ApplyingScalingFactorException {
    // invalid define scaling
    if (Double.compare(signalInfo.getScalingFactor(), Double.NaN) != 0) {
      throw new ApplyingScalingFactorException(String.format(
          "signal code %s - shouldn't define ScalingFactor %s for destination type %s",
          signalInfo.getSignalCode(), signalInfo.getScalingFactor(),
          signalInfo.getDestinationType()));
    }
  }

  /**
   * Check plus value.
   *
   * @param signalInfo
   *          the signal configuration
   * @throws ApplyingPlusValueException
   *           the applying plus value exception
   */
  public static void checkPlusValue(final SignalInfomation signalInfo)
      throws ApplyingPlusValueException {
    // invalid define plus value
    if (Double.compare(signalInfo.getPlusValue(), Double.NaN) != 0) {
      throw new ApplyingPlusValueException(String.format(
          "signal code %s - shouldn't define plus value %s for destination type %s",
          signalInfo.getSignalCode(), signalInfo.getPlusValue(),
          signalInfo.getDestinationType()));
    }
  }

  /**
   * Validate combine digits.
   *
   * @param signalInfo
   *          the signal configuration
   * @throws BitLengthException
   *           the bit length exception
   */
  public static void validateCombineDigits(final SignalInfomation signalInfo)
      throws BitLengthException {
    int bitLengthForCombine = signalInfo.getBitLengthforCombineType();
    if (signalInfo.getBitLength() % bitLengthForCombine != 0
        && signalInfo.getDestinationType().equals(ValueType.COMBINE)) {
      throw new BitLengthException(String.format(
          "Signal Code %s that ValueType is COMBINE but BitLength %s isn't divisible by %s",
          signalInfo.getSignalCode(), signalInfo.getBitLength(), bitLengthForCombine));
    }
  }

  /**
   * Validate custom destination type.
   *
   * @param signalInfo
   *          the signal configuration
   * @throws BitLengthException
   *           the bit length exception
   */
  public static void validateCustomDestinationType(final SignalInfomation signalInfo)
      throws BitLengthException {
    // Validate Byte Array
    validateByteArray(signalInfo);
    // Validate Combine_Digits
    validateCombineDigits(signalInfo);
  }
}
