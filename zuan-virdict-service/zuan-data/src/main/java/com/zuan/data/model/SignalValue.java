package com.zuan.data.model;

import java.io.Serializable;

/**
 * The Interface SignalValue.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SignalValue implements Comparable<SignalValue>, Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -7262272628909577623L;

  /** The signal name. */
  private String signalName;

  /** The signal code. */
  private String signalCode;

  /** The offset. */
  private int byteOffset;

  /** The bit offset. */
  private int bitOffset;

  /** The bit length. */
  private int bitLength;

  /** The value. */
  private transient Object value;

  /**
   * Gets the signal name.
   *
   * @return the signal name
   */
  public String getSignalName() {
    return signalName;
  }

  /**
   * Sets the signal name.
   *
   * @param signalName
   *          the new signal name
   */
  public void setSignalName(String signalName) {
    this.signalName = signalName;
  }

  /**
   * Gets the signal code.
   *
   * @return the signal code
   */
  public String getSignalCode() {
    return signalCode;
  }

  /**
   * Sets the signal code.
   *
   * @param signalCode
   *          the new signal code
   */
  public void setSignalCode(String signalCode) {
    this.signalCode = signalCode;
  }

  /**
   * Gets the byte offset.
   *
   * @return the byte offset
   */
  public int getByteOffset() {
    return byteOffset;
  }

  /**
   * Sets the byte offset.
   *
   * @param byteOffset
   *          the new byte offset
   */
  public void setByteOffset(int byteOffset) {
    this.byteOffset = byteOffset;
  }

  /**
   * Gets the bit offset.
   *
   * @return the bit offset
   */
  public int getBitOffset() {
    return bitOffset;
  }

  /**
   * Sets the bit offset.
   *
   * @param bitOffset
   *          the new bit offset
   */
  public void setBitOffset(int bitOffset) {
    this.bitOffset = bitOffset;
  }

  /**
   * Gets the bit length.
   *
   * @return the bit length
   */
  public int getBitLength() {
    return bitLength;
  }

  /**
   * Sets the bit length.
   *
   * @param bitLength
   *          the new bit length
   */
  public void setBitLength(int bitLength) {
    this.bitLength = bitLength;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value
   *          the new value
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(SignalValue o) {
    if (this.byteOffset > o.byteOffset) {
      return 1;
    } else if (this.byteOffset < o.byteOffset) {
      return -1;
    }

    if (this.bitOffset > o.bitOffset) {
      return 1;
    } else if (this.bitOffset < o.bitOffset) {
      return -1;
    }
    return 0;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + bitLength;
    result = prime * result + bitOffset;
    result = prime * result + byteOffset;
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SignalValue other = (SignalValue) obj;
    if (bitLength != other.bitLength)
      return false;
    if (bitOffset != other.bitOffset)
      return false;
    if (byteOffset != other.byteOffset)
      return false;
    return true;
  }

}
