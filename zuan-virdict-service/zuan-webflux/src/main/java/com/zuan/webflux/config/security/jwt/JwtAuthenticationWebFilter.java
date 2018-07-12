/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security.jwt;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * The Class JwtAuthenticationWebFilter.
 */
@Component
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

  /**
   * Instantiates a new jwt authentication web filter.
   *
   * @param authenticationManager
   *          the authentication manager
   * @param converter
   *          the converter
   * @param entryPoint
   *          the entry point
   */
  public JwtAuthenticationWebFilter(final ReactiveAuthenticationManager authenticationManager,
      final JwtAuthenticationConverter converter,
      final JwtServerAuthenticationEntryPoint entryPoint) {

    super(authenticationManager);
    setAuthenticationConverter(converter);
    setAuthenticationFailureHandler(
        new ServerAuthenticationEntryPointFailureHandler(entryPoint));
    setRequiresAuthenticationMatcher(new JwtHeadersExchangeMatcher());
  }

  /**
   * The Class JWTHeadersExchangeMatcher.
   */
  private static class JwtHeadersExchangeMatcher implements ServerWebExchangeMatcher {

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher#matches(org.springframework.web.server.ServerWebExchange)
     */
    @Override
    public Mono<MatchResult> matches(final ServerWebExchange exchange) {
      final Mono<ServerHttpRequest> request =
          Mono.just(exchange).map(ServerWebExchange::getRequest);

      /* Check for header "authorization" or parameter "token" */
      return request.map(ServerHttpRequest::getHeaders)
          .filter(h -> h.containsKey("authorization")).flatMap(x -> MatchResult.match())
          .switchIfEmpty(request.map(ServerHttpRequest::getQueryParams)
              .filter(h -> h.containsKey("token")).flatMap(x -> MatchResult.match())
              .switchIfEmpty(MatchResult.notMatch()));
    }
  }
}
