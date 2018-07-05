/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The Class WebfluxApplication.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@EnableJpaRepositories(basePackages = "com.zuan.webflux.repository")
@EntityScan(basePackages = "com.zuan.webflux.model")
@ComponentScan(basePackages = {
    "com.zuan.webflux.*"
})
@SpringBootApplication
public class WebfluxApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    final SpringApplication application = new SpringApplication(WebfluxApplication.class);
    application.setWebApplicationType(WebApplicationType.REACTIVE);
    application.run(args);
  }

}
