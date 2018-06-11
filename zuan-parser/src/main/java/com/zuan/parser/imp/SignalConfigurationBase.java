/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.util.LinkedHashSet;
import java.util.Map;

import com.zuan.parser.imp.exception.BitLengthException;
import com.zuan.parser.imp.exception.ByteOffSetException;
import com.zuan.parser.imp.exception.CapacityOfDestinationTypeException;
import com.zuan.parser.imp.exception.DestinationTypeException;
import com.zuan.parser.imp.exception.ParserException;
import com.zuan.parser.imp.exception.SwappingMinMaxException;

/**
 * The Class SignalConfigurationBase.
 *
 * @author <a href="mailto:hoangpv@gcs-vn.com">hoangpv</a>
 */
public abstract class SignalConfigurationBase {

  /** The maximum bit length of type. */
  private static Map<ValueType, Integer> maximumBitLengthOfType =
      SignalConfigurationFacility.getMaximumBitLengthOfValueType();

  /** The exception of applying scale value. */
  private static LinkedHashSet<ValueType> exceptionOfApplyingScaleValue =
      (LinkedHashSet<ValueType>) SignalConfigurationFacility
          .getExceptionListOfApplyingScaleValue();

  /** The exception of applying min max value. */
  private static LinkedHashSet<ValueType> exceptionOfApplyingMinMaxValue =
      (LinkedHashSet<ValueType>) SignalConfigurationFacility
          .getExceptionListOfApplyingMinMaxValue();

  /**
   * Sets the signal name.
   *
   * @param name
   *          the new signal name
   */
  public abstract void setSignalName(final String name);

  /**
   * Gets the signal name.
   *
   * @return the signal name
   */
  public abstract String getSignalName();

  /**
   * Sets the signal code.
   *
   * @param code
   *          the new signal code
   */
  public abstract void setSignalCode(final String code);

  /**
   * Gets the signal code.
   *
   * @return the signal code
   */
  public abstract String getSignalCode();

  /**
   * Sets the byte offset.
   *
   * @param offset
   *          the new byte offset
   */
  public abstract void setByteOffset(final int offset);

  /**
   * Gets the byte offset.
   *
   * @return the byte offset
   */
  public abstract int getByteOffset();

  /**
   * Sets the bit offset.
   *
   * @param offset
   *          the new bit offset
   */
  public abstract void setBitOffset(final int offset);

  /**
   * Gets the bit offset.
   *
   * @return the bit offset
   */
  public abstract int getBitOffset();

  /**
   * Sets the bit length.
   *
   * @param length
   *          the new bit length
   */
  public abstract void setBitLength(final int length);

  /**
   * Gets the bit length.
   *
   * @return the bit length
   */
  public abstract int getBitLength();

  /**
   * Gets the bit lengthfor combine type.
   *
   * @return the bit lengthfor combine type
   */
  public abstract int getBitLengthforCombineType();

  /**
   * Sets the bit lengthfor combine type.
   *
   * @param bitLength
   *          the new bit lengthfor combine type
   */
  public abstract void setBitLengthforCombineType(int bitLength);

  /**
   * Sets the source type.
   *
   * @param type
   *          the new source type
   */
  public abstract void setSourceType(final SourceType type);

  /**
   * Gets the source type.
   *
   * @return the source type
   */
  public abstract SourceType getSourceType();

  /**
   * Sets the destination type.
   *
   * @param type
   *          the new destination type
   */
  public abstract void setDestinationType(final ValueType type);

  /**
   * Sets the destination type.
   *
   * @param type
   *          the new destination type
   */
  public abstract void setDestinationType(final String type);

  /**
   * Gets the destination type.
   *
   * @return the destination type
   */
  public abstract ValueType getDestinationType();

  /**
   * Sets the scaling factor.
   *
   * @param factor
   *          the new scaling factor
   */
  public abstract void setScalingFactor(final Double factor);

  /**
   * Gets the scaling factor.
   *
   * @return the scaling factor
   */
  public abstract double getScalingFactor();

  /**
   * Sets the min value.
   *
   * @param minValue
   *          the new min value
   */
  public abstract void setMinValue(final Double minValue);

  /**
   * Gets the min value.
   *
   * @return the min value
   */
  public abstract double getMinValue();

  /**
   * Sets the max value.
   *
   * @param maxValue
   *          the new max value
   */
  public abstract void setMaxValue(final Double maxValue);

  /**
   * Gets the max value.
   *
   * @return the max value
   */
  public abstract double getMaxValue();

  /**
   * Sets the plus value.
   *
   * @param plusValue
   *          the new plus value
   */
  public abstract void setPlusValue(final Double plusValue);

  /**
   * Gets the plus value.
   *
   * @return the plus value
   */
  public abstract double getPlusValue();

  /**
   * Sets the train project.
   *
   * @param trainProject
   *          the new train project
   */
  public abstract void setTrainProject(final String trainProject);

  /**
   * Gets the train project.
   *
   * @return the train project
   */
  public abstract String getTrainProject();

  /**
   * Sets the include in analytics.
   *
   * @param isIncludeInAnalytics
   *          the is include in analytics
   */
  public abstract void setIncludeInAnalytics(final boolean isIncludeInAnalytics);

  /**
   * Gets the include in analytics.
   *
   * @return the include in analytics
   */
  public abstract boolean isIncludeInAnalytics();

  /**
   * Sets the include in real time.
   *
   * @param isIncludeInRealTime
   *          the new include in real time
   */
  public abstract void setIncludeInRealTime(final boolean isIncludeInRealTime);

  /**
   * Gets the include in real time.
   *
   * @return the include in real time
   */
  public abstract boolean isIncludeInRealTime();

  /**
   * Sets the include in time series.
   *
   * @param isIncludeInTimeSeries
   *          the new include in time series
   */
  public abstract void setIncludeInTimeSeries(final boolean isIncludeInTimeSeries);

  /**
   * Gets the include in time series.
   *
   * @return the include in time series
   */
  public abstract boolean isIncludeInTimeSeries();

  /**
   * Originating file.
   *
   * @param originatingFile
   *          the originating file
   */
  public abstract void setOriginatingFile(final String originatingFile);

  /**
   * Originating file.
   *
   * @return the string
   */
  public abstract String getOriginatingFile();

  /**
   * Validate configuration.
   *
   * @throws ParserException
   *           the parser exception
   */
  public void validateConfiguration() throws ParserException {
    // validate min byte off set OR
    // OR not define byte off set
    if (this.getByteOffset() < 0) {
      throw new ByteOffSetException(
          String.format("signal code %s has byte off set < 0", this.getSignalCode()));
    }

    SignalConfigurationFacility.validateBitOffSet(this);

    // validate min bit length
    // OR not define bit length
    if (this.getBitLength() < 1) {
      throw new BitLengthException(
          String.format("signal code %s bit length < 1", this.getSignalCode()));
    }

    validateDestinationType();

    // Min > Max OR Max < Min
    if (Double.compare(this.getMinValue(), this.getMaxValue()) > 0
        && Double.compare(this.getMinValue(), Double.NaN) != 0
        && Double.compare(this.getMaxValue(), Double.NaN) != 0) {
      throw new SwappingMinMaxException(
          String.format("signal code %s has min value %s bigger than max value %s",
              this.getSignalCode(), this.getMinValue(), this.getMaxValue()));
    }

    SignalConfigurationFacility.validateCustomDestinationType(this);
  }

  /**
   * Validate destination type.
   *
   * @throws ParserException
   *           the parser exception
   */
  private void validateDestinationType() throws ParserException {
    // empty destination type
    if (this.getDestinationType() == ValueType.NONE) {
      throw new DestinationTypeException(String
          .format("signal code %s doesn't define destionation type", this.getSignalCode()));
    }

    // invalid destination type
    if (maximumBitLengthOfType.containsKey(this.getDestinationType())
        && this.getBitLength() > maximumBitLengthOfType.get(this.getDestinationType())) {
      throw new CapacityOfDestinationTypeException(
          String.format("signal code %s has destination type %s is not enough with %s bit",
              this.getSignalCode(), this.getDestinationType(), this.getBitLength()));
    }

    if (exceptionOfApplyingScaleValue.contains(this.getDestinationType())) {
      SignalConfigurationFacility.checkScalingFactor(this);

      SignalConfigurationFacility.checkDefineMinMaxField(this);

      SignalConfigurationFacility.checkPlusValue(this);
    }

    // dataTypeNotMinMaxCollection
    if (exceptionOfApplyingMinMaxValue.contains(this.getDestinationType())) {
      SignalConfigurationFacility.checkDefineMinMaxField(this);
    }
  }

  /**
   * Gets the plus value.
   *
   * @param defaultValue
   *          the default value
   * @return the plus value
   */
  public abstract double getPlusValue(double defaultValue);

  /**
   * Gets the scaling factor.
   *
   * @param defaultValue
   *          the default value
   * @return the scaling factor
   */
  public abstract double getScalingFactor(double defaultValue);
}
