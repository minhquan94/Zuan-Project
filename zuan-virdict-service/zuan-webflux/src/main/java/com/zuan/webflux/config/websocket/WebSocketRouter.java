/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import com.zuan.webflux.handler.DefaultWebSocketHandler;

/**
 * The Class WebSocketRouter.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
/**
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Configuration
public class WebSocketRouter {

  /**
   * Handler mapping.
   *
   * @return the handler mapping
   */
  @Bean
  public HandlerMapping handlerMapping() {
    final Map<String, WebSocketHandler> map = new HashMap<>();
    map.put("/api/ws/echotest", new DefaultWebSocketHandler());

    final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.initApplicationContext();
    mapping.setOrder(10);
    mapping.setUrlMap(map);
    return mapping;
  }

  /**
   * Handler adapter.
   *
   * @return the web socket handler adapter
   */
  @Bean
  public WebSocketHandlerAdapter handlerAdapter() {
    return new WebSocketHandlerAdapter(webSocketService());
  }

  /**
   * Web socket service.
   *
   * @return the web socket service
   */
  @Bean
  public WebSocketService webSocketService() {
    return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
  }
}
