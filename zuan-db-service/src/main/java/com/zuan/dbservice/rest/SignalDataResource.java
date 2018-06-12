/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.dbservice.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuan.data.entities.SignalConfigurationEntity;
import com.zuan.data.model.SdSignalData;
import com.zuan.dbservice.server.SignalDataServer;

/**
 * The Class SignalDataResource.
 */
@RestController
@RequestMapping("/rest/db/signal-data")
public class SignalDataResource {

  /** The signal data service. */
  @Autowired
  private SignalDataServer signalDataService;

  /**
   * Gets the signal data by train.
   *
   * @param trainProject
   *          the train project
   * @return the signal data by train
   */
  @GetMapping("/{train-project}")
  public List<SdSignalData> getSignalDataByTrain(
      @PathVariable("train-project") final String trainProject) {
    final List<SignalConfigurationEntity> signalDataByTrain =
        signalDataService.getSignalDataByTrain(trainProject);
    if (CollectionUtils.isEmpty(signalDataByTrain)) {
      return new ArrayList<>();
    }
    return signalDataByTrain.stream().map(SdSignalData::new).collect(Collectors.toList());
  }
}
