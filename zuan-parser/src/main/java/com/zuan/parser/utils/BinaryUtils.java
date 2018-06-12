/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

/**
 * The Class BinaryUtils.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class BinaryUtils {

  /**
   * Instantiates a new binary utils.
   */
  private BinaryUtils() {
    // do nothing
  }

  /**
   * Convert file to byte arr.
   *
   * @param input
   *          the input
   * @return the byte[]
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  public static byte[] convertFileToByteArr(final File input) throws IOException {
    try (final InputStream inputStream = new FileInputStream(input)) {
      return IOUtils.toByteArray(inputStream);
    }
  }

  /**
   * Gets the byte length.
   *
   * @param bitLength
   *          the bit length
   * @return the byte length
   */
  public static int getByteLength(int bitLength) {
    if (bitLength > 8) {
      return bitLength / 8 + 1;
    }
    return 1;
  }

  /**
   * Convert file to byte arr.
   *
   * @param path
   *          the path
   * @return the byte[]
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  public static byte[] convertFileToByteArr(final String path) throws IOException {
    return convertFileToByteArr(Paths.get(path).toFile());
  }

}
