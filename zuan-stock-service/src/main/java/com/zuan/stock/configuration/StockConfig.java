/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * The Class StockConfig.
 *
 * @author zuan_
 */
@Configuration
public class StockConfig {

  /**
   * Rest template.
   *
   * @return the rest template
   */
  @Primary
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    return restTemplate;
  }
}
