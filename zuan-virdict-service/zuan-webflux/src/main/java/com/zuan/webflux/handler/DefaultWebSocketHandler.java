/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.handler;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

/**
 * The Class DefaultWebSocketHandler.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
public class DefaultWebSocketHandler extends AbstractWebSocketHandler {

  /**
   * Instantiates a new default web socket handler.
   */
  public DefaultWebSocketHandler() {
    this.authorizedRoles.addAll(Arrays.asList("ADMIN"));
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.webflux.handler.AbstractWebSocketHandler#doHandle(org.springframework.web.reactive.socket.WebSocketSession)
   */
  @Override
  public Mono<Void> doHandle(WebSocketSession session) {
    // Use retain() for Reactor Netty
    return session.send(session.receive().doOnNext(WebSocketMessage::retain)
        .delayElements(Duration.ofSeconds(2)));
  }
}
