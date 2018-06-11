/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.stock.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * The Class StartupListenner.
 */
@Component
public class StartupListenner {

  @Value("${db-server.host}")
  private String dbHost;

  @Value("${db-server.port}")
  private int dbPort;

  @Value("${db-server.rest.signal-data}")
  private String dbRestSignalData;

  /**
   * Checks if is started event.
   */
  @EventListener(ContextRefreshedEvent.class)
  public void isStartedEvent() {
    DbConfigLoader configLoader = DbConfigLoader.getInstance();
    configLoader.setHost(dbHost);
    configLoader.setPort(dbPort);
    configLoader.setRestSignalData(dbRestSignalData);
  }
}
