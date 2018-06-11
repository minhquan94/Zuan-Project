/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * The Class VirdictToolStockServiceApplication.
 *
 * @author zuan_
 */
@SpringBootApplication
@EnableEurekaClient
public class StockServiceApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(StockServiceApplication.class, args);
  }
}
