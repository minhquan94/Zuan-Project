/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.handler;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

public class DefaultWebSocketHandler extends AbstractWebSocketHandler {
  public DefaultWebSocketHandler() {
    this.authorizedRoles.addAll(Arrays.asList("ROLE_ADMIN"));
  }

  public Mono<Void> doHandle(WebSocketSession session) {
    // Use retain() for Reactor Netty
    return session.send(session.receive().doOnNext(WebSocketMessage::retain)
        .delayElements(Duration.ofSeconds(2)));
  }
}
