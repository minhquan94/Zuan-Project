/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.data.model;

import java.io.Serializable;

import com.zuan.data.entities.SignalConfigurationEntity;

/**
 * The Class SignalConfigurationEntity.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SignalData implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4828633886230216198L;

  /** The bit length. */
  private int bitLength;

  /** The bit offset. */
  private int bitOffset;

  /** The byte offset. */
  private int byteOffset;

  /** The destination type. */
  private String destinationType;

  /** The include in analytics. */
  private boolean includeInAnalytics;

  /** The include in real time. */
  private boolean includeInRealTime;

  /** The include in time series. */
  private boolean includeInTimeSeries;

  /** The max value. */
  private Double maxValue;

  /** The min value. */
  private Double minValue;

  /** The originating file. */
  private String originatingFile;

  /** The plus value. */
  private Double plusValue;

  /** The scaling factor. */
  private Double scalingFactor;

  /** The signal code. */
  private String signalCode;

  /** The signal name. */
  private String signalName;

  /** The source type. */
  private String sourceType;

  /** The train project. */
  private String trainProject;

  /** The file type. */
  private String fileType;

  /**
   * Instantiates a new signal data.
   */
  public SignalData() {
    super();
  }

  /**
   * Instantiates a new signal data.
   *
   * @param entity
   *          the entity
   */
  public SignalData(final SignalConfigurationEntity entity) {
    this.bitLength = entity.getBitLength();
    this.bitOffset = entity.getBitOffset();
    this.byteOffset = entity.getByteOffset();
    this.destinationType = entity.getDestinationType();
    this.fileType = entity.getFileType();
    this.includeInAnalytics = entity.isIncludeInAnalytics();
    this.includeInRealTime = entity.isIncludeInRealTime();
    this.includeInTimeSeries = entity.isIncludeInTimeSeries();
    this.maxValue = entity.getMaxValue();
    this.minValue = entity.getMinValue();
    this.originatingFile = entity.getOriginatingFile();
    this.plusValue = entity.getPlusValue();
    this.scalingFactor = entity.getScalingFactor();
    this.signalCode = entity.getSignalCode();
    this.signalName = entity.getSignalName();
    this.sourceType = entity.getSourceType();
    this.trainProject = entity.getTrainProject();
  }

  /**
   * Gets the bit length.
   *
   * @return the bitLength
   */
  public int getBitLength() {
    return bitLength;
  }

  /**
   * Sets the bit length.
   *
   * @param bitLength
   *          the bitLength to set
   */
  public void setBitLength(final int bitLength) {
    this.bitLength = bitLength;
  }

  /**
   * Gets the bit offset.
   *
   * @return the bitOffset
   */
  public int getBitOffset() {
    return bitOffset;
  }

  /**
   * Sets the bit offset.
   *
   * @param bitOffset
   *          the bitOffset to set
   */
  public void setBitOffset(final int bitOffset) {
    this.bitOffset = bitOffset;
  }

  /**
   * Gets the byte offset.
   *
   * @return the byteOffset
   */
  public int getByteOffset() {
    return byteOffset;
  }

  /**
   * Sets the byte offset.
   *
   * @param byteOffset
   *          the byteOffset to set
   */
  public void setByteOffset(final int byteOffset) {
    this.byteOffset = byteOffset;
  }

  /**
   * Gets the destination type.
   *
   * @return the destinationType
   */
  public String getDestinationType() {
    return destinationType;
  }

  /**
   * Sets the destination type.
   *
   * @param destinationType
   *          the destinationType to set
   */
  public void setDestinationType(final String destinationType) {
    this.destinationType = destinationType;
  }

  /**
   * Checks if is include in analytics.
   *
   * @return the includeInAnalytics
   */
  public boolean isIncludeInAnalytics() {
    return includeInAnalytics;
  }

  /**
   * Sets the include in analytics.
   *
   * @param includeInAnalytics
   *          the includeInAnalytics to set
   */
  public void setIncludeInAnalytics(final boolean includeInAnalytics) {
    this.includeInAnalytics = includeInAnalytics;
  }

  /**
   * Checks if is include in real time.
   *
   * @return the includeInRealTime
   */
  public boolean isIncludeInRealTime() {
    return includeInRealTime;
  }

  /**
   * Sets the include in real time.
   *
   * @param includeInRealTime
   *          the includeInRealTime to set
   */
  public void setIncludeInRealTime(final boolean includeInRealTime) {
    this.includeInRealTime = includeInRealTime;
  }

  /**
   * Checks if is include in time series.
   *
   * @return the includeInTimeSeries
   */
  public boolean isIncludeInTimeSeries() {
    return includeInTimeSeries;
  }

  /**
   * Sets the include in time series.
   *
   * @param includeInTimeSeries
   *          the includeInTimeSeries to set
   */
  public void setIncludeInTimeSeries(final boolean includeInTimeSeries) {
    this.includeInTimeSeries = includeInTimeSeries;
  }

  /**
   * Gets the max value.
   *
   * @return the maxValue
   */
  public Double getMaxValue() {
    return maxValue;
  }

  /**
   * Sets the max value.
   *
   * @param maxValue
   *          the maxValue to set
   */
  public void setMaxValue(final Double maxValue) {
    this.maxValue = maxValue;
  }

  /**
   * Gets the min value.
   *
   * @return the minValue
   */
  public Double getMinValue() {
    return minValue;
  }

  /**
   * Sets the min value.
   *
   * @param minValue
   *          the minValue to set
   */
  public void setMinValue(final Double minValue) {
    this.minValue = minValue;
  }

  /**
   * Gets the originating file.
   *
   * @return the originatingFile
   */
  public String getOriginatingFile() {
    return originatingFile;
  }

  /**
   * Sets the originating file.
   *
   * @param originatingFile
   *          the originatingFile to set
   */
  public void setOriginatingFile(final String originatingFile) {
    this.originatingFile = originatingFile;
  }

  /**
   * Gets the plus value.
   *
   * @return the plusValue
   */
  public Double getPlusValue() {
    return plusValue;
  }

  /**
   * Sets the plus value.
   *
   * @param plusValue
   *          the plusValue to set
   */
  public void setPlusValue(final Double plusValue) {
    this.plusValue = plusValue;
  }

  /**
   * Gets the scaling factor.
   *
   * @return the scalingFactor
   */
  public Double getScalingFactor() {
    return scalingFactor;
  }

  /**
   * Sets the scaling factor.
   *
   * @param scalingFactor
   *          the scalingFactor to set
   */
  public void setScalingFactor(final Double scalingFactor) {
    this.scalingFactor = scalingFactor;
  }

  /**
   * Gets the signal code.
   *
   * @return the signalCode
   */
  public String getSignalCode() {
    return signalCode;
  }

  /**
   * Sets the signal code.
   *
   * @param signalCode
   *          the signalCode to set
   */
  public void setSignalCode(final String signalCode) {
    this.signalCode = signalCode;
  }

  /**
   * Gets the signal name.
   *
   * @return the signalName
   */
  public String getSignalName() {
    return signalName;
  }

  /**
   * Sets the signal name.
   *
   * @param signalName
   *          the signalName to set
   */
  public void setSignalName(final String signalName) {
    this.signalName = signalName;
  }

  /**
   * Gets the source type.
   *
   * @return the sourceType
   */
  public String getSourceType() {
    return sourceType;
  }

  /**
   * Sets the source type.
   *
   * @param sourceType
   *          the sourceType to set
   */
  public void setSourceType(final String sourceType) {
    this.sourceType = sourceType;
  }

  /**
   * Gets the train project.
   *
   * @return the trainProject
   */
  public String getTrainProject() {
    return trainProject;
  }

  /**
   * Sets the train project.
   *
   * @param trainProject
   *          the trainProject to set
   */
  public void setTrainProject(final String trainProject) {
    this.trainProject = trainProject;
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
