package com.zuan.parser.signal;

import java.io.Serializable;

import com.zuan.parser.SourceType;
import com.zuan.parser.ValueType;
import com.zuan.parser.imp.exception.ParserException;

/**
 * The Interface SignalConfiguration.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public interface SignalInfomation {

  /**
   * Initialize.
   *
   * @throws ParserException
   *           the parser exception
   */
  void initialize() throws ParserException;

  /**
   * Sets the signal name.
   *
   * @param name
   *          the new signal name
   */
  void setSignalName(final String name);

  /**
   * Gets the signal name.
   *
   * @return the signal name
   */
  String getSignalName();

  /**
   * Sets the signal code.
   *
   * @param code
   *          the new signal code
   */
  void setSignalCode(final String code);

  /**
   * Gets the signal code.
   *
   * @return the signal code
   */
  String getSignalCode();

  /**
   * Sets the byte offset.
   *
   * @param offset
   *          the new byte offset
   */
  void setByteOffset(final int offset);

  /**
   * Gets the byte offset.
   *
   * @return the byte offset
   */
  int getByteOffset();

  /**
   * Sets the bit offset.
   *
   * @param offset
   *          the new bit offset
   */
  void setBitOffset(final int offset);

  /**
   * Gets the bit offset.
   *
   * @return the bit offset
   */
  int getBitOffset();

  /**
   * Sets the bit length.
   *
   * @param length
   *          the new bit length
   */
  void setBitLength(final int length);

  /**
   * Gets the bit length.
   *
   * @return the bit length
   */
  int getBitLength();

  /**
   * Gets the bit lengthfor combine type.
   *
   * @return the bit lengthfor combine type
   */
  int getBitLengthforCombineType();

  /**
   * Sets the bit lengthfor combine type.
   *
   * @param bitLength
   *          the new bit lengthfor combine type
   */
  void setBitLengthforCombineType(int bitLength);

  /**
   * Sets the source type.
   *
   * @param type
   *          the new source type
   */
  void setSourceType(final SourceType type);

  /**
   * Gets the source type.
   *
   * @return the source type
   */
  SourceType getSourceType();

  /**
   * Sets the destination type.
   *
   * @param type
   *          the new destination type
   */
  void setDestinationType(final ValueType type);

  /**
   * Sets the destination type.
   *
   * @param type
   *          the new destination type
   */
  void setDestinationType(final String type);

  /**
   * Gets the destination type.
   *
   * @return the destination type
   */
  ValueType getDestinationType();

  /**
   * Sets the scaling factor.
   *
   * @param factor
   *          the new scaling factor
   */
  void setScalingFactor(final Double factor);

  /**
   * Gets the scaling factor.
   *
   * @return the scaling factor
   */
  double getScalingFactor();

  /**
   * Sets the min value.
   *
   * @param minValue
   *          the new min value
   */
  void setMinValue(final Double minValue);

  /**
   * Gets the min value.
   *
   * @return the min value
   */
  double getMinValue();

  /**
   * Sets the max value.
   *
   * @param maxValue
   *          the new max value
   */
  void setMaxValue(final Double maxValue);

  /**
   * Gets the max value.
   *
   * @return the max value
   */
  double getMaxValue();

  /**
   * Sets the plus value.
   *
   * @param plusValue
   *          the new plus value
   */
  void setPlusValue(final Double plusValue);

  /**
   * Gets the plus value.
   *
   * @return the plus value
   */
  double getPlusValue();

  /**
   * Gets the expresstion.
   *
   * @return the serial expresstion
   */
  Serializable getExpresstion();
}
