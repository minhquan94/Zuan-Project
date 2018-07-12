package com.zuan.webflux.config.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class JwtServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.security.web.server.ServerAuthenticationEntryPoint#commence(org.springframework.web.server.ServerWebExchange,
   *      org.springframework.security.core.AuthenticationException)
   */
  @Override
  public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
    return Mono.fromRunnable(() -> {
      final ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
    });
  }

}
