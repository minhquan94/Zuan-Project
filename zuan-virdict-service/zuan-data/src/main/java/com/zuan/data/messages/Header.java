/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.data.messages;

import com.zuan.parser.codehaus.preon.annotation.BoundList;
import com.zuan.parser.codehaus.preon.annotation.BoundNumber;
import com.zuan.parser.codehaus.preon.annotation.BoundString;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;

/**
 * The Class HeaderInformationMess.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class Header {

  /** The header. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private short header = 255;

  /** The command type. */
  @BoundNumber(size = "32", byteOrder = ByteOrder.BIG_ENDIAN)
  private long commandType;

  /** The sd period. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte sdPeriod;

  /** The sd id. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte sdId;

  /** The store sd period. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte storeSdPeriod;

  /** The filter pattern number. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private short filterPatternNumber;

  /** The length. */
  @BoundNumber(size = "16", byteOrder = ByteOrder.LITTLE_ENDIAN)
  private int length;

  /** The data type. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte dataType;

  /** The row num of matrix. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte rowNumOfMatrix;

  /** The column num of matrix. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte columnNumOfMatrix;

  /** The size of matrix. */
  @BoundNumber(size = "16", byteOrder = ByteOrder.LITTLE_ENDIAN)
  private int sizeOfMatrix;

  /** The vehicle id. */
  @BoundString(size = "8")
  private String vehicleId;

  /** The target vehicle id. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte targetVehicleId;

  /** The spare. */
  @BoundList(size = "7")
  private byte[] spare;

  /**
   * Initialize the spare.
   */
  public void setSpare() {
    spare = new byte[7];
  }

  /**
   * Gets the header.
   *
   * @return the header
   */
  public short getHeader() {
    return this.header;
  }

  /**
   * Gets the raw command type.
   *
   * @return the raw command type
   */
  public long getRawCommandType() {
    return this.commandType;
  }

  /**
   * Gets the command type.
   *
   * @return the command type
   */
  public long getCommandType() {
    return this.commandType;
  }

  /**
   * Gets the raw sd period.
   *
   * @return the raw sd period
   */
  public byte getRawSdPeriod() {
    return this.sdPeriod;
  }

  /**
   * Gets the sd period.
   *
   * @return the sd period
   */
  public byte getSdPeriod() {
    return this.sdPeriod;
  }

  /**
   * Gets the raw sd id.
   *
   * @return the raw sd id
   */
  public byte getRawSdId() {
    return this.sdId;
  }

  /**
   * Gets the sd id.
   *
   * @return the sd id
   */
  public byte getSdId() {
    return this.sdId;
  }

  /**
   * Gets the raw store sd period.
   *
   * @return the raw store sd period
   */
  public byte getRawStoreSdPeriod() {
    return this.storeSdPeriod;
  }

  /**
   * Gets the store sd period.
   *
   * @return the store sd period
   */
  public byte getStoreSdPeriod() {
    return this.storeSdPeriod;
  }

  /**
   * Gets the raw filter pattern number.
   *
   * @return the raw filter pattern number
   */
  public short getRawFilterPatternNumber() {
    return this.filterPatternNumber;
  }

  /**
   * Gets the filter pattern number.
   *
   * @return the filter pattern number
   */
  public short getFilterPatternNumber() {
    return this.filterPatternNumber;
  }

  /**
   * Gets the raw length.
   *
   * @return the raw length
   */
  public int getRawLength() {
    return this.length;
  }

  /**
   * Gets the length.
   *
   * @return the length
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Gets the raw data type.
   *
   * @return the raw data type
   */
  public byte getRawDataType() {
    return this.dataType;
  }

  /**
   * Gets the data type.
   *
   * @return the data type
   */
  public byte getDataType() {
    return this.dataType;
  }

  /**
   * Gets the raw row num of matrix.
   *
   * @return the raw row num of matrix
   */
  public byte getRawRowNumOfMatrix() {
    return this.rowNumOfMatrix;
  }

  /**
   * Gets the row num of matrix.
   *
   * @return the row num of matrix
   */
  public byte getRowNumOfMatrix() {
    return this.rowNumOfMatrix;
  }

  /**
   * Gets the raw column num of matrix.
   *
   * @return the raw column num of matrix
   */
  public byte getRawColumnNumOfMatrix() {
    return this.columnNumOfMatrix;
  }

  /**
   * Gets the column num of matrix.
   *
   * @return the column num of matrix
   */
  public byte getColumnNumOfMatrix() {
    return this.columnNumOfMatrix;
  }

  /**
   * Gets the raw size of matrix.
   *
   * @return the raw size of matrix
   */
  public int getRawSizeOfMatrix() {
    return this.sizeOfMatrix;
  }

  /**
   * Gets the size of matrix.
   *
   * @return the size of matrix
   */
  public int getSizeOfMatrix() {
    return this.sizeOfMatrix;
  }

  /**
   * Gets the raw vehicle id.
   *
   * @return the raw vehicle id
   */
  public String getRawVehicleId() {
    return this.vehicleId;
  }

  /**
   * Gets the vehicle id.
   *
   * @return the vehicle id
   */
  public String getVehicleId() {
    return this.vehicleId;
  }

  /**
   * Gets the raw target vehicle id.
   *
   * @return the raw target vehicle id
   */
  public byte getRawTargetVehicleId() {
    return this.targetVehicleId;
  }

  /**
   * Gets the target vehicle id.
   *
   * @return the target vehicle id
   */
  public byte getTargetVehicleId() {
    return this.targetVehicleId;
  }

  /**
   * Gets the spare.
   *
   * @return the spare
   */
  public byte[] getSpare() {
    return this.spare;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder jsonBuilder = new StringBuilder();
    jsonBuilder.append("{");
    jsonBuilder.append("\"header\"" + ": ");
    jsonBuilder.append(this.header + ",");
    jsonBuilder.append("\"commandType\"" + ": ");
    jsonBuilder.append(this.commandType + ",");
    jsonBuilder.append("\"sdPeriod\"" + ": ");
    jsonBuilder.append(this.sdPeriod + ",");
    jsonBuilder.append("\"sdId\"" + ": ");
    jsonBuilder.append(this.sdId + ",");
    jsonBuilder.append("\"storeSdPeriod\"" + ": ");
    jsonBuilder.append(this.storeSdPeriod + ",");
    jsonBuilder.append("\"filterPatternNumber\"" + ": ");
    jsonBuilder.append(this.filterPatternNumber + ",");
    jsonBuilder.append("\"length\"" + ": ");
    jsonBuilder.append(this.length + ",");
    jsonBuilder.append("\"dataType\"" + ": ");
    jsonBuilder.append(this.dataType + ",");
    jsonBuilder.append("\"rowNumOfMatrix\"" + ": ");
    jsonBuilder.append(this.rowNumOfMatrix + ",");
    jsonBuilder.append("\"columnNumOfMatrix\"" + ": ");
    jsonBuilder.append(this.columnNumOfMatrix + ",");
    jsonBuilder.append("\"sizeOfMatrix\"" + ": ");
    jsonBuilder.append(this.sizeOfMatrix + ",");
    jsonBuilder.append("\"vehicleId\"" + ": ");
    jsonBuilder.append(this.vehicleId + ",");
    jsonBuilder.append("\"targetVehicleId\"" + ": ");
    jsonBuilder.append(this.targetVehicleId + ",");
    jsonBuilder.append("\"spare\"" + ": ");
    jsonBuilder.append(this.spare);
    jsonBuilder.append("}");
    return jsonBuilder.toString();
  }

  /**
   * Sets the command type.
   *
   * @param commandType
   *          the commandType to set
   */
  public void setCommandType(long commandType) {
    this.commandType = commandType;
  }

  /**
   * Sets the vehicle id.
   *
   * @param vehicleId
   *          the vehicleId to set
   */
  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  /**
   * Sets the target vehicle id.
   *
   * @param targetVehicleId
   *          the targetVehicleId to set
   */
  public void setTargetVehicleId(byte targetVehicleId) {
    this.targetVehicleId = targetVehicleId;
  }

  /**
   * Sets the length. /**
   *
   * @param sdPeriod
   *          the sdPeriod to set
   */
  public void setSdPeriod(byte sdPeriod) {
    this.sdPeriod = sdPeriod;
  }

  /**
   * Sets the store sd period.
   *
   * @param storeSdPeriod
   *          the storeSdPeriod to set
   */
  public void setStoreSdPeriod(byte storeSdPeriod) {
    this.storeSdPeriod = storeSdPeriod;
  }

  /**
   * Sets the filter pattern number.
   *
   * @param filterPatternNumber
   *          the filterPatternNumber to set
   */
  public void setFilterPatternNumber(short filterPatternNumber) {
    this.filterPatternNumber = filterPatternNumber;
  }

  /**
   * Sets the row num of matrix.
   *
   * @param rowNumOfMatrix
   *          the rowNumOfMatrix to set
   */
  public void setRowNumOfMatrix(byte rowNumOfMatrix) {
    this.rowNumOfMatrix = rowNumOfMatrix;
  }

  /**
   * Sets the column num of matrix.
   *
   * @param columnNumOfMatrix
   *          the columnNumOfMatrix to set
   */
  public void setColumnNumOfMatrix(byte columnNumOfMatrix) {
    this.columnNumOfMatrix = columnNumOfMatrix;
  }

  /**
   * Sets the size of matrix.
   *
   * @param sizeOfMatrix
   *          the sizeOfMatrix to set
   */
  public void setSizeOfMatrix(int sizeOfMatrix) {
    this.sizeOfMatrix = sizeOfMatrix;
  }

  /**
   * Sets the sd id.
   *
   * @param sdId
   *          the sdId to set
   */
  public void setSdId(byte sdId) {
    this.sdId = sdId;
  }

  /**
   * Sets the length.
   *
   * @param length
   *          the length to set
   */
  public void setLength(int length) {
    this.length = length;
  }

  /**
   * Sets the data type.
   *
   * @param dataType
   *          the new data type
   */
  public void setDataType(byte dataType) {
    this.dataType = dataType;
  }
}
