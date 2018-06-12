/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.util;

import org.apache.commons.lang.StringUtils;

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
   * @param hostName
   *          the host name
   * @param rest
   *          the rest
   * @return the string
   */
  public static String buildRest(final String hostName, final String rest) {
    String suffix;
    if (StringUtils.startsWith(rest, "/")) {
      suffix = rest;
    } else {
      suffix = "/" + rest;
    }
    StringBuilder builder = new StringBuilder();
    builder.append(HTTP_SCHEME).append(hostName).append(suffix);
    return builder.toString();
  }

  /**
   * Builds the security rest.
   *
   * @param hostName
   *          the host name
   * @param rest
   *          the rest
   * @return the string
   */
  public static String buildSecurityRest(final String hostName, final String rest) {
    String suffix;
    if (StringUtils.startsWith(rest, "/")) {
      suffix = rest;
    } else {
      suffix = "/" + rest;
    }
    StringBuilder builder = new StringBuilder();
    builder.append(HTTPS_SCHEME).append(hostName).append(suffix);
    return builder.toString();
  }
}
