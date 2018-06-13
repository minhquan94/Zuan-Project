/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zuan.shell.SimpleBannerProvider;
import com.zuan.shell.provider.FileInputProvider;

/**
 * The Class Configutaion.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(value = {ParserConfigurationProperties.class })
public class ParserConfig {

  /**
   * Simple banner provider.
   *
   * @return the simple banner provider
   */
  @Bean
  public SimpleBannerProvider simpleBannerProvider() {
    return new SimpleBannerProvider();
  }

  /**
   * File input provider.
   *
   * @return the file input provider
   */
  @Bean
  public FileInputProvider fileInputProvider() {
    return new FileInputProvider();
  }
}
