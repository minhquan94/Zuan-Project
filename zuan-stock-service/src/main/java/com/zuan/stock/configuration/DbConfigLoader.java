/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.configuration;

/**
 * The Class ConfigLoader.
 *
 * @author zuan_
 */
public final class DbConfigLoader {

  /** The Constant INSTANCE. */
  private static final DbConfigLoader INSTANCE = new DbConfigLoader();

  /** The host. */
  private String host;

  /** The port. */
  private int port;

  /** The rest signal data. */
  private String restSignalData;

  /**
   * Instantiates a new config loader.
   */
  private DbConfigLoader() {
    super();
  }

  /**
   * Gets the single instance of DbConfigLoader.
   *
   * @return single instance of DbConfigLoader
   */
  public static DbConfigLoader getInstance() {
    return INSTANCE;
  }

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
   * Gets the rest signal data.
   *
   * @return the rest signal data
   */
  public String getRestSignalData() {
    return restSignalData;
  }

  /**
   * Sets the rest signal data.
   *
   * @param restSignalData
   *          the new rest signal data
   */
  public void setRestSignalData(String restSignalData) {
    this.restSignalData = restSignalData;
  }

}
