/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.util;

/**
 * The Class RestUtils.
 *
 * @author zuan_
 */
public final class RestUtils {

  /** The http scheme. */
  public static final String HTTP_SCHEME = "http://";

  /** The HTT ps_ scheme. */
  public static final String HTTPS_SCHEME = "https://";

  /**
   * Instantiates a new rest utils.
   */
  private RestUtils() {
    super();
  }

  /**
   * Builds the rest.
   *
   * @param host
   *          the host
   * @param port
   *          the port
   * @param rest
   *          the rest
   * @return the string
   */
  public static String buildRest(final String host, final int port, final String rest) {
    StringBuilder builder = new StringBuilder();
    builder.append(HTTP_SCHEME).append(host).append(':').append(port).append(rest);
    return builder.toString();
  }
}
