/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell.utils;

/**
 * The Class MathUtils.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class MathUtils {

  /**
   * Instantiates a new math utils.
   */
  private MathUtils() {
    super();
  }

  /**
   * Log2nlz.
   *
   * @param bits
   *          the bits
   * @return the int
   */
  public static int log2n(int bits) {
    if (bits == 0)
      return 0;
    return 31 - Integer.numberOfLeadingZeros(bits);
  }
}
