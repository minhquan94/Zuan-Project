/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.mvel2.MVEL;

/**
 * The class SignalConfiguration.
 *
 * @author <a href="mailto:hoangpv@gcs-vn.com">hoangpv</a>
 */
public class SignalConfiguration extends SignalConfigurationBase implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The signal name. */
  private String signalName;

  /** The signal code. */
  private String signalCode;

  /** The offset. */
  private int byteOffset = Integer.MIN_VALUE;

  /** The bit offset. */
  private int bitOffset = Integer.MIN_VALUE;

  /** The bit length. */
  private int bitLength = Integer.MIN_VALUE;

  /** The bit lengthfor combine type. */
  private int bitLengthforCombineType = 4;

  /** The source type. */
  private SourceType sourceType = SourceType.NONE;

  /** The destination type. */
  private ValueType destinationType = ValueType.NONE;

  /** The scaling factor. */
  private double scalingFactor = Double.NaN;

  /** The min. */
  private double minValue = Double.NaN;

  /** The max. */
  private double maxValue = Double.NaN;

  /** The plus. */
  private double plusValue = Double.NaN;

  /** The train project. */
  private String trainProject;

  /** The is include in analytics. */
  private boolean isIncludeInAnalytics;

  /** The is include in real time. */
  private boolean isIncludeInRealTime;

  /** The is include in time series. */
  private boolean isIncludeInTimeSeries;

  /** The originating file. */
  private String originatingFile;

  /** The mvel expression. */
  private String mvelExpression;

  /** The serial expresstion. */
  private Serializable serialExpresstion;

  /**
   * Sets the signal name.
   *
   * @param name
   *          the name of the signal
   */
  @Override
  public void setSignalName(final String name) {
    this.signalName = name;
  }

  /**
   * Gets the signal name.
   *
   * @return the signal name
   */
  @Override
  public String getSignalName() {
    return this.signalName;
  }

  /**
   * Sets the signal code.
   *
   * @param code
   *          the code of the signal
   */
  @Override
  public void setSignalCode(final String code) {
    this.signalCode = code;
  }

  /**
   * Gets the signal code.
   *
   * @return the signal code
   */
  @Override
  public String getSignalCode() {
    return this.signalCode;
  }

  /**
   * Sets the byte offset.
   *
   * @param offset
   *          the byte offset of the signal
   */
  @Override
  public void setByteOffset(final int offset) {
    this.byteOffset = offset;
  }

  /**
   * Gets the byte offset.
   *
   * @return the byte offset
   */
  @Override
  public int getByteOffset() {
    return this.byteOffset;
  }

  /**
   * Sets the bit offset.
   *
   * @param offset
   *          the bit offset of the signal
   */
  @Override
  public void setBitOffset(final int offset) {
    this.bitOffset = offset;
  }

  /**
   * Gets the bit offset.
   *
   * @return the bit offset
   */
  @Override
  public int getBitOffset() {
    // D7 .. D0 >> 0 .. 7
    return BitBufferFacility.MAXIMUM_BIT_POSITION - this.bitOffset;
  }

  /**
   * Sets the bit length.
   *
   * @param length
   *          the bit length of the signal
   */
  @Override
  public void setBitLength(final int length) {
    this.bitLength = length;
  }

  /**
   * Gets the bit length.
   *
   * @return the bit length
   */
  @Override
  public int getBitLength() {
    return this.bitLength;
  }

  /**
   * Sets the source type.
   *
   * @param type
   *          the source type of the signal
   */
  @Override
  public void setSourceType(final SourceType type) {
    this.sourceType = type;
  }

  /**
   * Gets the source type.
   *
   * @return the source type
   */
  @Override
  public SourceType getSourceType() {
    return this.sourceType;
  }

  /**
   * Sets the destination type.
   *
   * @param type
   *          the destination type of the signal
   */
  @Override
  public void setDestinationType(final ValueType type) {
    this.destinationType = type;
  }

  /**
   * Sets the destination type.
   *
   * @param strType
   *          the new destination type
   */
  @Override
  public void setDestinationType(final String type) {
    final String typeValue = type.toLowerCase(Locale.ENGLISH);
    // check value type of Parser
    switch (typeValue) {
    case "boolean":
    case "bool":
      this.destinationType = ValueType.BOOL;
      break;
    case "byte":
      this.destinationType = ValueType.BYTE;
      break;
    case "short":
      this.destinationType = ValueType.SHORT;
      break;
    case "int":
    case "integer":
      this.destinationType = ValueType.INT;
      break;
    case "double":
      this.destinationType = ValueType.DOUBLE;
      break;
    case "float":
      this.destinationType = ValueType.FLOAT;
      break;
    case "long":
      this.destinationType = ValueType.LONG;
      break;
    case "string":
      // The Parser will parse a byte array to a character array.
      this.destinationType = ValueType.STRING;
      break;
    case "bytearray":
      // The type are quite rare.
      this.destinationType = ValueType.BYTEARRAY;
      break;
    default:
      // custom type
      if (typeValue.contains("combine")) {
        this.destinationType = ValueType.COMBINE;
        int bitLengthForCombine = 0;
        try {
          bitLengthForCombine = Integer.valueOf(typeValue.replace("combine", ""));
        } catch (NumberFormatException e) {
          this.destinationType = ValueType.NONE;
        }
        this.setBitLengthforCombineType(bitLengthForCombine);
      } else {
        this.destinationType = ValueType.NONE;
      }
    }
  }

  /**
   * Gets the destination type.
   *
   * @return the destination type
   */
  @Override
  public ValueType getDestinationType() {
    return this.destinationType;
  }

  /**
   * Sets the scaling factor.
   *
   * @param factor
   *          the scaling factor of the signal
   */
  @Override
  public void setScalingFactor(final Double factor) {
    if (factor != null && !factor.equals(0.0)) {
      this.scalingFactor = factor;
    }
  }

  /**
   * Gets the scaling factor.
   *
   * @return the scaling factor
   */
  @Override
  public double getScalingFactor() {
    return this.scalingFactor;
  }

  /**
   * Gets the scaling factor.
   *
   * @param defaultValue
   *          the default value
   * @return the scaling factor
   */
  @Override
  public double getScalingFactor(double defaultValue) {
    if (Double.isNaN(this.scalingFactor)) {
      return defaultValue;
    }
    return this.scalingFactor;
  }

  /**
   * Sets the min value.
   *
   * @param minValue
   *          the new min value
   */
  @Override
  public void setMinValue(final Double minValue) {
    if (minValue != null) {
      this.minValue = minValue;
    }
  }

  /**
   * Gets the min value.
   *
   * @return the min value
   */
  @Override
  public double getMinValue() {
    return this.minValue;
  }

  /**
   * Sets the max value.
   *
   * @param maxValue
   *          the new max value
   */
  @Override
  public void setMaxValue(final Double maxValue) {
    if (maxValue != null) {
      this.maxValue = maxValue;
    }
  }

  /**
   * Gets the max value.
   *
   * @return the max value
   */
  @Override
  public double getMaxValue() {
    return this.maxValue;
  }

  /**
   * Sets the plus value.
   *
   * @param plusValue
   *          the new plus value
   */
  @Override
  public void setPlusValue(final Double plusValue) {
    if (plusValue != null) {
      this.plusValue = plusValue;
    }
  }

  /**
   * Gets the plus value.
   *
   * @return the plus value
   */
  @Override
  public double getPlusValue() {
    return this.plusValue;
  }

  /**
   * Gets the plus value.
   *
   * @param defaultValue
   *          the default value
   * @return the plus value
   */
  @Override
  public double getPlusValue(double defaultValue) {
    if (Double.isNaN(this.plusValue)) {
      return defaultValue;
    }
    return this.plusValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#setTrainProject(java.lang.String)
   */
  @Override
  public void setTrainProject(final String trainProject) {
    this.trainProject = trainProject;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#getTrainProject()
   */
  @Override
  public String getTrainProject() {
    return this.trainProject;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#SetIncludeInAnalytics(boolean)
   */
  @Override
  public void setIncludeInAnalytics(final boolean isIncludeInAnalytics) {
    this.isIncludeInAnalytics = isIncludeInAnalytics;
  }

  /**
   * Gets the include in analytics.
   *
   * @return the include in analytics
   */
  @Override
  public boolean isIncludeInAnalytics() {
    return this.isIncludeInAnalytics;
  }

  /**
   * Sets the include in real time.
   *
   * @param isIncludeInRealTime
   *          the new include in real time
   */
  @Override
  public void setIncludeInRealTime(final boolean isIncludeInRealTime) {
    this.isIncludeInRealTime = isIncludeInRealTime;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#getIncludeInRealTime()
   */
  @Override
  public boolean isIncludeInRealTime() {
    return this.isIncludeInRealTime;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#setIncludeInTimeSeries(boolean)
   */
  @Override
  public void setIncludeInTimeSeries(final boolean isIncludeInTimeSeries) {
    this.isIncludeInTimeSeries = isIncludeInTimeSeries;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#getIncludeInTimeSeries()
   */
  @Override
  public boolean isIncludeInTimeSeries() {
    return this.isIncludeInTimeSeries;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#originatingFile(java.lang.String)
   */
  @Override
  public void setOriginatingFile(final String originatingFile) {
    this.originatingFile = originatingFile;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#originatingFile()
   */
  @Override
  public String getOriginatingFile() {
    return this.originatingFile;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#getBitLengthforCombineType()
   */
  @Override
  public int getBitLengthforCombineType() {
    return bitLengthforCombineType;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.parser.SignalConfigurationBase#setBitLengthforCombineType(int)
   */
  @Override
  public void setBitLengthforCombineType(int bitLength) {
    if (bitLength > 0) {
      this.bitLengthforCombineType = bitLength;
    }
  }

  /**
   * @return the mvelExpression
   */
  public String getMvelExpression() {
    return mvelExpression;
  }

  /**
   * @param mvelExpression
   *          the mvelExpression to set
   */
  public void setMvelExpression(String mvelExpression) {
    this.mvelExpression = mvelExpression;
    if (StringUtils.isNoneBlank(mvelExpression)) {
      this.serialExpresstion = MVEL.compileExpression(mvelExpression.trim());
    }
  }

  /**
   * @return the serialExpresstion
   */
  public Serializable getSerialExpresstion() {
    return serialExpresstion;
  }
}
