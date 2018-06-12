/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The Class StockServiceConfiguration.
 *
 * @author zuan_
 */
@Configuration
public class StockServiceConfiguration {

  /**
   * Rest template.
   *
   * @return the rest template
   */
  @LoadBalanced
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
