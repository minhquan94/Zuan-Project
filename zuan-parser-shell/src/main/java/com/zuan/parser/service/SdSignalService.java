/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zuan.data.model.SdSignalData;

/**
 * The Interface SdSignalService.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Service
public class SdSignalService {

  /**
   * Gets the signal configuration.
   *
   * @param trainProject
   *          the train project
   * @return the signal configuration
   */
  @Cacheable(value = "SignalConfiguration", key = "#trainProject")
  public List<SdSignalData> getSignalConfiguration(final String trainProject) {
    return null;
  }

  /**
   * Gets the signal configuration.
   *
   * @param trainProject
   *          the train project
   * @param signalCode
   *          the signal code
   * @return the signal configuration
   */
  public List<SdSignalData> getSignalConfiguration(final String trainProject,
      final String signalCode) {
    final List<SdSignalData> signalConfiguration = getSignalConfiguration(trainProject);
    return signalConfiguration;
  }
}
