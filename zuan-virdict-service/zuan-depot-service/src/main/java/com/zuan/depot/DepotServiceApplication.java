/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.depot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class DepotServiceApplication.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@SpringBootApplication
public class DepotServiceApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    final SpringApplication application = new SpringApplication(DepotServiceApplication.class);
    application.run(args);
  }
}
