/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The Class ZuanGatewayServerApplication.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(GatewayServerApplication.class, args);
  }
}
