/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.signal;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import com.zuan.parser.SourceType;
import com.zuan.parser.ValueType;
import com.zuan.parser.imp.BitBufferFacility;
import com.zuan.parser.imp.exception.CapacityOfDestinationTypeException;
import com.zuan.parser.imp.exception.DestinationTypeException;
import com.zuan.parser.imp.exception.ParserException;

/**
 * The Class CommonSignalInformation.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class CommonSignalInformation implements SignalInfomation, Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 6068010711469040296L;

  /** The maximum bit length of type. */
  private static Map<ValueType, Integer> maximumBitLengthOfType =
      SignalInformationFacility.getMaximumBitLengthOfValueType();

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
   * @param type
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
    // do nothing
  }

  /**
   * Gets the scaling factor.
   *
   * @return the scaling factor
   */
  @Override
  public double getScalingFactor() {
    return 1.0;
  }

  /**
   * Sets the min value.
   *
   * @param minValue
   *          the new min value
   */
  @Override
  public void setMinValue(final Double minValue) {
    // do nothing
  }

  /**
   * Gets the min value.
   *
   * @return the min value
   */
  @Override
  public double getMinValue() {
    return 0;
  }

  /**
   * Sets the max value.
   *
   * @param maxValue
   *          the new max value
   */
  @Override
  public void setMaxValue(final Double maxValue) {
    // do nothing
  }

  /**
   * Gets the max value.
   *
   * @return the max value
   */
  @Override
  public double getMaxValue() {
    return 0;
  }

  /**
   * Sets the plus value.
   *
   * @param plusValue
   *          the new plus value
   */
  @Override
  public void setPlusValue(final Double plusValue) {
    // do nothing
  }

  /**
   * Gets the plus value.
   *
   * @return the plus value
   */
  @Override
  public double getPlusValue() {
    return 0;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.parser.AbstractSignalInformation.gcs.virdict.parser.SignalConfigurationBase#getBitLengthforCombineType()
   */
  @Override
  public int getBitLengthforCombineType() {
    return bitLengthforCombineType;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.parser.AbstractSignalInformation.gcs.virdict.parser.SignalConfigurationBase#setBitLengthforCombineType(int)
   */
  @Override
  public void setBitLengthforCombineType(int bitLength) {
    if (bitLength > 0) {
      this.bitLengthforCombineType = bitLength;
    }
  }

  /**
   * Gets the mvel expression.
   *
   * @return the mvelExpression
   */
  public String getMvelExpression() {
    return null;
  }

  /**
   * Sets the mvel expression.
   *
   * @param mvelExpression
   *          the mvelExpression to set
   */
  public void setMvelExpression(String mvelExpression) {
    // do nothing
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.signal.SignalInfomation#getExpresstion()
   */
  @Override
  public Serializable getExpresstion() {
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.signal.SignalInfomation#initialize()
   */
  @Override
  public void initialize() throws ParserException {
    SignalInformationFacility.validateBitOffSet(this);
    SignalInformationFacility.validateBitLength(this);
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
    SignalInformationFacility.validateCustomDestinationType(this);
  }
}
