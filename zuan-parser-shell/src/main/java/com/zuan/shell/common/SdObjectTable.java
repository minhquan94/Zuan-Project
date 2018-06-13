/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell.common;

import java.io.Serializable;

/**
 * The Class SdObject.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SdObjectTable implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -1637654427319655508L;

  /** The id. */
  private int id;

  /** The signal code. */
  private String signalCode;

  /** The description. */
  private String description;

  /** The byte offset. */
  private String byteOffset;

  /** The bit offset. */
  private String bitOffset;

  /** The bit length. */
  private String bitLength;

  /** The value. */
  private String value;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id
   *          the new id
   */
  public void setId(int id) {
    this.id = id;
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
  public String getByteOffset() {
    return byteOffset;
  }

  /**
   * Sets the byte offset.
   *
   * @param byteOffset
   *          the new byte offset
   */
  public void setByteOffset(String byteOffset) {
    this.byteOffset = byteOffset;
  }

  /**
   * Gets the bit offset.
   *
   * @return the bit offset
   */
  public String getBitOffset() {
    return bitOffset;
  }

  /**
   * Sets the bit offset.
   *
   * @param bitOffset
   *          the new bit offset
   */
  public void setBitOffset(String bitOffset) {
    this.bitOffset = bitOffset;
  }

  /**
   * Gets the bit length.
   *
   * @return the bit length
   */
  public String getBitLength() {
    return bitLength;
  }

  /**
   * Sets the bit length.
   *
   * @param bitLength
   *          the new bit length
   */
  public void setBitLength(String bitLength) {
    this.bitLength = bitLength;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value
   *          the new value
   */
  public void setValue(String value) {
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
    SdObjectTable other = (SdObjectTable) obj;
    if (signalCode == null) {
      if (other.signalCode != null)
        return false;
    } else if (!signalCode.equals(other.signalCode))
      return false;
    return true;
  }
}
