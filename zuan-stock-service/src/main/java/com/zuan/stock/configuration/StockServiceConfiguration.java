/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
  @Primary
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
