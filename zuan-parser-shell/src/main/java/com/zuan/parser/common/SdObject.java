/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.common;

import java.io.Serializable;

/**
 * The Class SdObject.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SdObject implements Serializable, Comparable<SdObject> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -1637654427319655508L;

  /** The sd number. */
  private int sdNumber;

  /** The signal code. */
  private String signalCode;

  /** The description. */
  private String description;

  /** The byte offset. */
  private int byteOffset;

  /** The bit offset. */
  private int bitOffset;

  /** The bit length. */
  private int bitLength;

  /** The value. */
  private Object value;

  /**
   * Gets the sd number.
   *
   * @return the sd number
   */
  public int getSdNumber() {
    return sdNumber;
  }

  /**
   * Sets the sd number.
   *
   * @param sdNumber
   *          the new sd number
   */
  public void setSdNumber(int sdNumber) {
    this.sdNumber = sdNumber;
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
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description
   *          the new description
   */
  public void setDescription(String description) {
    this.description = description;
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
   *          the new bite length
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
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((signalCode == null) ? 0 : signalCode.hashCode());
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
    SdObject other = (SdObject) obj;
    if (signalCode == null) {
      if (other.signalCode != null)
        return false;
    } else if (!signalCode.equals(other.signalCode))
      return false;
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(SdObject o) {
    return this.sdNumber > o.getSdNumber() ? 1 : -1;
  }

}
