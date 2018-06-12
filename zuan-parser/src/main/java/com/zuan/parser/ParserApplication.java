/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@EnableCaching
@SpringBootApplication
public class ParserApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    SpringApplication application = new SpringApplication(ParserApplication.class);
    application.run(args);
  }
}
