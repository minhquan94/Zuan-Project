/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zuan.data.model.SignalData;
import com.zuan.stock.configuration.DbConfigLoader;
import com.zuan.stock.util.RestUtils;

import yahoofinance.YahooFinance;

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

  /** The yahoo finance. */
  private final YahooFinance yahooFinance;

  /**
   * Instantiates a new signal stock resource.
   *
   * @param yahooFinance
   *          the yahoo finance
   */
  private SignalStockResource(YahooFinance yahooFinance) {
    this.yahooFinance = yahooFinance;
  }

  /**
   * Gets the signal data by train.
   *
   * @param trainProject
   *          the train project
   * @return the signal data by train
   */
  @GetMapping("/{train-project}")
  public List<SignalData> getSignalDataByTrain(
      @PathVariable("train-project") final String trainProject) {
    final DbConfigLoader dbConfigLoader = DbConfigLoader.getInstance();
    final ResponseEntity<List<SignalData>> exchange = restTemplate.exchange(
        RestUtils.buildRest(dbConfigLoader.getHost(), dbConfigLoader.getPort(),
            dbConfigLoader.getRestSignalData()),
        HttpMethod.GET, null, new ParameterizedTypeReference<List<SignalData>>() {
        });

    final List<SignalData> signalDatas = exchange.getBody();
    return new ArrayList<>();
  }
}
