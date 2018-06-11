/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.zuan.parser.common.SdLength;

/**
 * The Class BinaryPropertiesConfiguration.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@ConfigurationProperties("parser")
public class ParserConfigurationProperties {

  /** The train projects. */
  private List<String> trainProjects;

  /** The sd number position. */
  private int sdNumberPosition;

  /** The sd length mapper. */
  private List<SdLength> sdLengthMapper = new ArrayList<>();

  /**
   * Gets the train projects.
   *
   * @return the train projects
   */
  public List<String> getTrainProjects() {
    return trainProjects;
  }

  /**
   * Sets the train projects.
   *
   * @param trainProjects
   *          the new train projects
   */
  public void setTrainProjects(List<String> trainProjects) {
    this.trainProjects = trainProjects;
  }

  /**
   * Gets the sd number position.
   *
   * @return the sd number position
   */
  public int getSdNumberPosition() {
    return sdNumberPosition;
  }

  /**
   * Sets the sd number position.
   *
   * @param sdNumberPosition
   *          the new sd number position
   */
  public void setSdNumberPosition(int sdNumberPosition) {
    this.sdNumberPosition = sdNumberPosition;
  }

  /**
   * Gets the sd length mapper.
   *
   * @return the sd length mapper
   */
  public List<SdLength> getSdLengthMapper() {
    return sdLengthMapper;
  }

  /**
   * Sets the sd length mapper.
   *
   * @param sdLengthMapper
   *          the new sd length mapper
   */
  public void setSdLengthMapper(List<SdLength> sdLengthMapper) {
    this.sdLengthMapper = sdLengthMapper;
  }
}
