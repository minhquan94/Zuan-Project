/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.zuan.parser.imp.exception.ApplyingMaxValueException;
import com.zuan.parser.imp.exception.ApplyingMinValueException;
import com.zuan.parser.imp.exception.ApplyingPlusValueException;
import com.zuan.parser.imp.exception.ApplyingScalingFactorException;
import com.zuan.parser.imp.exception.BitLengthException;
import com.zuan.parser.imp.exception.BitOffSetException;
import com.zuan.parser.imp.exception.ParserException;

/**
 * The Class SignalConfigurationFacility.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class SignalConfigurationFacility {

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
  private SignalConfigurationFacility() {
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
    datatypeMapping.put(ValueType.BOOL, SignalConfigurationFacility.MAX_BOOL_BIT_LENGTH);
    datatypeMapping.put(ValueType.BYTE, SignalConfigurationFacility.MAX_BYTE_BIT_LENGTH);
    datatypeMapping.put(ValueType.SHORT, SignalConfigurationFacility.MAX_SHORT_BIT_LENGTH);
    datatypeMapping.put(ValueType.FLOAT, SignalConfigurationFacility.MAX_FLOAT_BIT_LENGTH);
    datatypeMapping.put(ValueType.INT, SignalConfigurationFacility.MAX_INT_BIT_LENGTH);
    datatypeMapping.put(ValueType.DOUBLE, SignalConfigurationFacility.MAX_DOUBLE_BIT_LENGTH);
    datatypeMapping.put(ValueType.LONG, SignalConfigurationFacility.MAX_LONG_BIT_LENGTH);

    return datatypeMapping;
  }

  /**
   * Validate byte array.
   *
   * @param signalConfiguration
   *          the signal configuration
   * @throws BitLengthException
   *           the bit length exception
   */
  public static void validateByteArray(final SignalConfigurationBase signalConfiguration)
      throws BitLengthException {

    final boolean isDestinationTypeByteArray =
        signalConfiguration.getDestinationType() == ValueType.BYTEARRAY;

    if (isDestinationTypeByteArray && (signalConfiguration.getBitLength()
        % SignalConfigurationFacility.MAX_BYTE_BIT_LENGTH != 0)) {
      throw new BitLengthException(String.format(
          "Signal Code %s that ValueType is ByteArray but BitLength %s isn't divisible by 8",
          signalConfiguration.getSignalCode(), signalConfiguration.getBitLength()));
    }

  }

  /**
   * Validate bit off set.
   *
   * @param signalConfiguration
   *          the signal configuration
   * @throws BitOffSetException
   *           the bit off set exception
   */
  public static void validateBitOffSet(final SignalConfigurationBase signalConfiguration)
      throws BitOffSetException {
    // validate min bit offset
    // OR not define bit off set
    if (signalConfiguration.getBitOffset() < 0) {
      throw new BitOffSetException(String.format("signal code %s has bit off set < 0",
          signalConfiguration.getSignalCode()));
    }

    // validate max bit offset
    if (signalConfiguration.getBitOffset() > SignalConfigurationFacility.MAX_BIT_OFFSET) {
      throw new BitOffSetException(String.format("signal code %s has bit off set > %s",
          signalConfiguration.getSignalCode(), SignalConfigurationFacility.MAX_BIT_OFFSET));
    }
  }

  /**
   * Check define min max field.
   *
   * @param signalConfiguration
   *          the signal configuration
   * @throws ParserException
   *           the parser exception
   */
  public static void checkDefineMinMaxField(final SignalConfigurationBase signalConfiguration)
      throws ParserException {
    // invalid define MinValue
    try {
      if (Double.compare(signalConfiguration.getMinValue(), Double.NaN) != 0) {
        throw new ApplyingMinValueException(String.format(
            "signal code %s - shouldn't define min value %s for destination type %s",
            signalConfiguration.getSignalCode(), signalConfiguration.getMinValue(),
            signalConfiguration.getDestinationType()));
      }
      // invalid define MaxValue
      if (Double.compare(signalConfiguration.getMaxValue(), Double.NaN) != 0) {
        throw new ApplyingMaxValueException(String.format(
            "signal code %s - shouldn't define max value %s for destination type %s",
            signalConfiguration.getSignalCode(), signalConfiguration.getMaxValue(),
            signalConfiguration.getDestinationType()));
      }
    } catch (ApplyingMinValueException | ApplyingMaxValueException e) {
      throw new ParserException(e.getMessage(), e);
    }

  }

  /**
   * Check scaling factor.
   *
   * @param signalConfiguration
   *          the signal configuration
   * @throws ApplyingScalingFactorException
   *           the applying scaling factor exception
   */
  public static void checkScalingFactor(final SignalConfigurationBase signalConfiguration)
      throws ApplyingScalingFactorException {
    // invalid define scaling
    if (Double.compare(signalConfiguration.getScalingFactor(), Double.NaN) != 0) {
      throw new ApplyingScalingFactorException(String.format(
          "signal code %s - shouldn't define ScalingFactor %s for destination type %s",
          signalConfiguration.getSignalCode(), signalConfiguration.getScalingFactor(),
          signalConfiguration.getDestinationType()));
    }
  }

  /**
   * Check plus value.
   *
   * @param signalConfiguration
   *          the signal configuration
   * @throws ApplyingPlusValueException
   *           the applying plus value exception
   */
  public static void checkPlusValue(final SignalConfigurationBase signalConfiguration)
      throws ApplyingPlusValueException {
    // invalid define plus value
    if (Double.compare(signalConfiguration.getPlusValue(), Double.NaN) != 0) {
      throw new ApplyingPlusValueException(String.format(
          "signal code %s - shouldn't define plus value %s for destination type %s",
          signalConfiguration.getSignalCode(), signalConfiguration.getPlusValue(),
          signalConfiguration.getDestinationType()));
    }
  }

  /**
   * Validate combine digits.
   *
   * @param signalConfiguration
   *          the signal configuration
   * @throws BitLengthException
   *           the bit length exception
   */
  public static void validateCombineDigits(final SignalConfigurationBase signalConfiguration)
      throws BitLengthException {
    int bitLengthForCombine = signalConfiguration.getBitLengthforCombineType();
    if (signalConfiguration.getBitLength() % bitLengthForCombine != 0
        && signalConfiguration.getDestinationType().equals(ValueType.COMBINE)) {
      throw new BitLengthException(String.format(
          "Signal Code %s that ValueType is COMBINE but BitLength %s isn't divisible by %s",
          signalConfiguration.getSignalCode(), signalConfiguration.getBitLength(),
          bitLengthForCombine));
    }
  }

  /**
   * Validate custom destination type.
   *
   * @param signalConfiguration
   *          the signal configuration
   * @throws BitLengthException
   *           the bit length exception
   */
  public static void validateCustomDestinationType(
      final SignalConfigurationBase signalConfiguration) throws BitLengthException {
    // Validate Byte Array
    validateByteArray(signalConfiguration);
    // Validate Combine_Digits
    validateCombineDigits(signalConfiguration);
  }
}
