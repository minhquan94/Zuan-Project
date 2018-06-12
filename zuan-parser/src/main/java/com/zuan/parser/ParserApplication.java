/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.zuan.data.repositories.BaseJpaRepositoryImpl;

/**
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {
    "com.zuan.data.repositories" }, repositoryBaseClass = BaseJpaRepositoryImpl.class)
@EntityScan(basePackages = "com.zuan.data.entities")
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
