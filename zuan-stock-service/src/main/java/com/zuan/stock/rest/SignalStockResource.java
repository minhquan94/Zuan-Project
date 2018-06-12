/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zuan.data.model.SdSignalData;
import com.zuan.stock.util.RestUtils;

/**
 * The Class SignalStockresource.
 *
 * @author zuan_
 */
@RestController
@RequestMapping("/rest/stock/signal-data")
public class SignalStockResource {

  /** The rest template. */
  @Autowired
  private RestTemplate restTemplate;

  /** The db servicename. */
  @Value("${db-service.name}")
  private String dbServicename;

  /** The signal data. */
  @Value("${rest.db-service.signal-data}")
  private String signalData;

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
    final ResponseEntity<List<SdSignalData>> exchange = restTemplate.exchange(
        RestUtils.buildRest(dbServicename, signalData + '/' + trainProject), HttpMethod.GET,
        null, new ParameterizedTypeReference<List<SdSignalData>>() {
        });
    return exchange.getBody();
  }

}
