/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The Class EurekaServerApplication.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@EnableEurekaServer
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaServerApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(EurekaServerApplication.class, args);
  }
}
