/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * The Class UnauthorizedAuthenticationEntryPoint.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Component
public class UnauthorizedAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.web.server.ServerAuthenticationEntryPoint#commence(org.springframework.web.server.ServerWebExchange,
   *      org.springframework.security.core.AuthenticationException)
   */
  @Override
  public Mono<Void> commence(final ServerWebExchange exchange,
      final AuthenticationException e) {
    return Mono
        .fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.ACCEPTED));
  }
}
