/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.dbservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.zuan.data.repositories.BaseJpaRepositoryImpl;

/**
 * The Class DbServiceApplication.
 *
 * @author zuan_
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {
    "com.zuan.data.repositories" }, repositoryBaseClass = BaseJpaRepositoryImpl.class)
@EntityScan(basePackages = "com.zuan.data.entities")
@EnableEurekaClient
public class DbServiceApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(DbServiceApplication.class, args);
  }
}
