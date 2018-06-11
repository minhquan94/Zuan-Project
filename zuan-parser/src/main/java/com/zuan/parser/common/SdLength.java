/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.common;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class SdLength.
 */
public class SdLength {

  /** The train project. */
  private String trainProject;

  /** The mapping. */
  private Map<String, Integer> mapping = new HashMap<>();

  /**
   * Gets the train project.
   *
   * @return the train project
   */
  public String getTrainProject() {
    return trainProject;
  }

  /**
   * Sets the train project.
   *
   * @param trainProject
   *          the new train project
   */
  public void setTrainProject(String trainProject) {
    this.trainProject = trainProject;
  }

  /**
   * Gets the mapping.
   *
   * @return the mapping
   */
  public Map<String, Integer> getMapping() {
    return mapping;
  }

  /**
   * Sets the mapping.
   *
   * @param mapping
   *          the mapping
   */
  public void setMapping(Map<String, Integer> mapping) {
    this.mapping = mapping;
  }
}
