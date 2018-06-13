/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.dbservice.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuan.data.entities.SignalConfigurationEntity;
import com.zuan.data.repositories.SignalConfigurationRepository;

/**
 * The Class SignalDataServer.
 */
@Service
public class SignalDataServer {

  /** The signal configuration repository. */
  @Autowired
  private SignalConfigurationRepository signalConfigurationRepository;

  /**
   * Gets the signal data by train.
   *
   * @param trainProject
   *          the train project
   * @return the signal data by train
   */
  public List<SignalConfigurationEntity> getSignalDataByTrain(final String trainProject) {
    return signalConfigurationRepository.findByTrainProject(trainProject);
  }

}
