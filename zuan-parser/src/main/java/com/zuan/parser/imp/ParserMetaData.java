/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import com.zuan.parser.jpa.entities.SignalConfigurationEntity;

/**
 * The Class ParserMetaData.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class ParserMetaData extends SignalConfiguration {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 6758899203845335933L;

  /**
   * the SD id.
   */
  private int originSd;

  /** The file type. */
  private String fileType;

  /**
   * the originatingFile.
   */
  private String originatingFile;

  /**
   * Instantiates a new parser meta data.
   */
  public ParserMetaData() {
    super();
  }

  /**
   * Instantiates a new parser meta data.
   *
   * @param entity
   *          the entity
   */
  public ParserMetaData(final SignalConfigurationEntity entity) {
    setParserMetaData(entity);
  }

  /**
   * Sets the data.
   *
   * @param entity
   *          the new data
   */
  private void setParserMetaData(final SignalConfigurationEntity entity) {
    setSignalCode(entity.getSignalCode());
    setSignalName(entity.getSignalName());
    setByteOffset(entity.getByteOffset());
    setBitOffset(entity.getBitOffset());
    setBitLength(entity.getBitLength());

    setDestinationType(toValueType(entity.getDestinationType()));
    setScalingFactor(getDouble(entity.getScalingFactor()));
    setMinValue(getDouble(entity.getMinValue()));
    setMaxValue(getDouble(entity.getMaxValue()));
    setPlusValue(getDouble(entity.getPlusValue()));
    setOriginatingFile(entity.getOriginatingFile());
    setFileType(entity.getFileType());
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.gcs.virdict.parser.SignalConfiguration#getOriginatingFile()
   */
  @Override
  public String getOriginatingFile() {
    return originatingFile;
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.gcs.virdict.parser.SignalConfiguration#setOriginatingFile(java.lang.String)
   */
  @Override
  public void setOriginatingFile(final String originatingFile) {
    this.originatingFile = originatingFile;
  }

  /**
   * Gets the origin sd.
   *
   * @return the originSd
   */
  public int getOriginSd() {
    return originSd;
  }

  /**
   * Sets the origin sd.
   *
   * @param originSd
   *          the originSd to set
   */
  public void setOriginSd(final int originSd) {
    this.originSd = originSd;
  }

  /**
   * Gets the destination type.
   *
   * @param valueType
   *          the value type
   * @return the destination type
   */
  private static ValueType toValueType(final String valueType) {
    ValueType value;
    switch (valueType.toUpperCase()) {
    case "BOOL":
    case "BOOLEAN":
      value = ValueType.BOOL;
      break;
    case "BYTE":
      value = ValueType.BYTE;
      break;
    case "SHORT":
      value = ValueType.SHORT;
      break;
    case "INT":
      value = ValueType.INT;
      break;
    case "STRING":
      value = ValueType.STRING;
      break;
    case "FLOAT":
      value = ValueType.FLOAT;
      break;
    case "DOUBLE":
      value = ValueType.DOUBLE;
      break;
    case "BYTEARRAY":
      value = ValueType.BYTEARRAY;
      break;
    case "LONG":
      value = ValueType.LONG;
      break;
    case "COMBINE4":
      value = ValueType.COMBINE;
      break;
    default:
      value = ValueType.NONE;
      break;
    }
    return value;
  }

  /**
   * Gets the double.
   *
   * @param origin
   *          the origin
   * @return the double
   */
  private static double getDouble(final Object origin) {
    if (origin == null) {
      return Double.NaN;
    }
    return (double) origin;
  }

  /**
   * Gets the file type.
   *
   * @return the file type
   */
  public String getFileType() {
    return fileType;
  }

  /**
   * Sets the file type.
   *
   * @param fileType
   *          the new file type
   */
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

}
