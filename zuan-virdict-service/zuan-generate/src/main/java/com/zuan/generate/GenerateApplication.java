/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.generate;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zuan.generate.utils.PackageUtils;

/**
 * The Class ZuanGenerateApplication.
 *
 * @author zuan_
 */
@SpringBootApplication
public class GenerateApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    SpringApplication.run(GenerateApplication.class, args);
    PackageUtils.renamePackage(
        "C:\\Project\\VirdictTool\\virdict-tool-stock-service\\src\\main", "virdict.tool",
        "zuan");
  }
}
