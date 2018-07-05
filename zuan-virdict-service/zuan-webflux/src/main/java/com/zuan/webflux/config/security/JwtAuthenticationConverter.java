/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * The Class JwtAuthenticationConverter.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Component
public class JwtAuthenticationConverter
implements Function<ServerWebExchange, Mono<Authentication>> {

  /** The logger. */
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /** The user details service. */
  private final ReactiveUserDetailsService userDetailsService;

  /** The jwt token util. */
  private final JwtTokenUtil jwtTokenUtil;

  /** The token header. */
  @Value("${jwt.header}")
  private String tokenHeader;

  /** The token param. */
  @Value("${jwt.param}")
  private String tokenParam;

  /** The bearer prefix. */
  @Value("${jwt.prefix}")
  private String bearerPrefix;

  /**
   * Instantiates a new jwt authentication converter.
   *
   * @param userDetailsService
   *          the user details service
   * @param jwtTokenUtil
   *          the jwt token util
   */
  public JwtAuthenticationConverter(ReactiveUserDetailsService userDetailsService,
      JwtTokenUtil jwtTokenUtil) {
    Assert.notNull(userDetailsService, "userDetailsService cannot be null");
    Assert.notNull(jwtTokenUtil, "jwtTokenUtil cannot be null");

    this.userDetailsService = userDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.util.function.Function#apply(java.lang.Object)
   */
  @Override
  public Mono<Authentication> apply(ServerWebExchange exchange) {
    final ServerHttpRequest request = exchange.getRequest();
    try {

      final Authentication authentication = null;
      String authToken = null;
      String username = null;

      final String bearerRequestHeader =
          exchange.getRequest().getHeaders().getFirst(tokenHeader);

      if (bearerRequestHeader != null && bearerRequestHeader.startsWith(bearerPrefix + " ")) {
        authToken = bearerRequestHeader.substring(7);
      }

      if (authToken == null && request.getQueryParams() != null
          && !request.getQueryParams().isEmpty()) {
        final String authTokenParam = request.getQueryParams().getFirst(tokenParam);
        if (authTokenParam != null) {
          authToken = authTokenParam;
        }
      }

      if (authToken != null) {
        try {
          username = jwtTokenUtil.getUsernameFromToken(authToken);
        } catch (final IllegalArgumentException e) {
          logger.error("an error occured during getting username from token", e);
        } catch (final Exception e) {
          logger.warn("the token is expired and not valid anymore", e);
        }
      } else {
        logger.warn("couldn't find bearer string, will ignore the header");
      }

      logger.info("checking authentication for user " + username);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        return Mono
            .just(new JwtPreAuthenticationToken(authToken, bearerRequestHeader, username));
      }

      return Mono.just(authentication);
    } catch (final Exception e) {
      throw new BadCredentialsException("Invalid token: " + e);
    }
  }
}
