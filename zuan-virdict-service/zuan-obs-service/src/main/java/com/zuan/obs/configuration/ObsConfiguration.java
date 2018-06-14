/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.obs.configuration;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class ObsConfiguration.
 */
@Configuration
@ConfigurationProperties("obs")
public class ObsConfiguration {

  /** The host. */
  private String host;

  /** The port. */
  private int port;

  /** The verticles. */
  private Set<String> verticles;

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

  /**
   * Gets the verticles.
   *
   * @return the verticles
   */
  public Set<String> getVerticles() {
    return verticles;
  }

  /**
   * Sets the verticles.
   *
   * @param verticles
   *          the new verticles
   */
  public void setVerticles(Set<String> verticles) {
    this.verticles = verticles;
  }

}
