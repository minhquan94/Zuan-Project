/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * The Class CloudConfigApplication.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@SpringBootApplication
@EnableConfigServer
public class CloudConfigApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(CloudConfigApplication.class, args);
  }
}
