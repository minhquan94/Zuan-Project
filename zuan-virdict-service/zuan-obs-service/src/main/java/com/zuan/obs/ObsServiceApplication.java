/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class ObsServiceApplication.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@SpringBootApplication
public class ObsServiceApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    final SpringApplication application = new SpringApplication(ObsServiceApplication.class);
    application.run(args);
  }
}
