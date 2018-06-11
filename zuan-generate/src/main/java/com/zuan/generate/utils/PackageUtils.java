/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.generate.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PackageUtils.
 *
 * @author zuan_
 */
public final class PackageUtils {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(PackageUtils.class);

  /** The max depth. */
  private static final int MAX_DEPTH = 100;

  /**
   * Instantiates a new package utils.
   */
  private PackageUtils() {
    super();
  }

  /**
   * Rename package.
   *
   * @param path
   *          the path
   * @param oldPackage
   *          the old package
   * @param newPackage
   *          the new package
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  public static void renamePackage(final String path, final String oldPackage,
      final String newPackage) throws IOException {
    final Stream<Path> files = Files.find(Paths.get(path), MAX_DEPTH,
        (filePath, fileAttr) -> fileAttr.isRegularFile());
    files.forEach(file -> {
      try {
        replaceFileContent(file, oldPackage, newPackage);
      } catch (IOException e) {
        LOG.error("", e);
      }
    });
    files.close();
  }

  /**
   * Replace file content.
   *
   * @param path
   *          the path
   * @param oldPackage
   *          the old package
   * @param newPackage
   *          the new package
   * @return true, if successful
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  public static boolean replaceFileContent(final Path path, final String oldPackage,
      final String newPackage) throws IOException {
    final Stream<String> lines = Files.lines(path);
    final List<String> replaced =
        lines.map(line -> StringUtils.replaceAll(line, oldPackage, newPackage))
            .collect(Collectors.toList());
    Files.write(path, replaced);
    lines.close();
    return false;
  }
}
