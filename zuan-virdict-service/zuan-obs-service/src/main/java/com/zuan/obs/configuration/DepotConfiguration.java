/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.obs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class DepotConfiguration.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Configuration
@ConfigurationProperties("depot")
public class DepotConfiguration {

  /** The host. */
  private String host;

  /** The port. */
  private int port;

  /**
   * Gets the host.
   *
   * @return the host
   */
  public String getHost() {
    return host;
  }

  /**
   * Sets the host.
   *
   * @param host
   *          the new host
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Gets the port.
   *
   * @return the port
   */
  public int getPort() {
    return port;
  }

  /**
   * Sets the port.
   *
   * @param port
   *          the new port
   */
  public void setPort(int port) {
    this.port = port;
  }

}
