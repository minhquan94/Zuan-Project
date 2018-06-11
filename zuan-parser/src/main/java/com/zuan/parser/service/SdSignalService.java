/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zuan.parser.jpa.entities.SignalConfigurationEntity;
import com.zuan.parser.jpa.repositories.SignalConfigurationRepository;

/**
 * The Interface SdSignalService.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Service
public class SdSignalService {

  /** The signal configuration repository. */
  @Autowired
  private SignalConfigurationRepository signalConfigurationRepository;

  /**
   * Gets the signal configuration.
   *
   * @param trainProject
   *          the train project
   * @return the signal configuration
   */
  @Cacheable(value = "SignalConfiguration", key = "#trainProject")
  public List<SignalConfigurationEntity> getSignalConfiguration(final String trainProject) {
    return signalConfigurationRepository.findByTrainProject(trainProject);
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
  public List<SignalConfigurationEntity> getSignalConfiguration(final String trainProject,
      final String signalCode) {
    final List<SignalConfigurationEntity> signalConfiguration =
        getSignalConfiguration(trainProject);
    return signalConfiguration;
  }
}
