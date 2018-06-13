/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@SpringBootApplication
public class ShellApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    final SpringApplication application = new SpringApplication(ShellApplication.class);
    application.run(args);
  }
}
