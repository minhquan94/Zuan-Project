/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security.jwt;

import java.util.UUID;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.zuan.webflux.service.JwtTokenService;
import com.zuan.webflux.util.AuthenConstantUtil;

import io.jsonwebtoken.ExpiredJwtException;
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
  private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationConverter.class);

  /** The jwt token service. */
  private final JwtTokenService jwtTokenService;

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
   * @param jwtTokenService
   *          the jwt token util
   */
  public JwtAuthenticationConverter(JwtTokenService jwtTokenService) {
    this.jwtTokenService = jwtTokenService;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.util.function.Function#apply(java.lang.Object)
   */
  @Override
  public Mono<Authentication> apply(ServerWebExchange exchange) {
    final ServerHttpRequest request = exchange.getRequest();
    String authToken = null;
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
    Authentication authentication = getAuthenticator(authToken, bearerRequestHeader);
    return Mono.just(authentication);
  }

  /**
   * Gets the user name.
   *
   * @param authToken
   *          the auth token
   * @return the user name
   */
  private Authentication getAuthenticator(final String authToken,
      final String bearerRequestHeader) {
    if (authToken != null) {
      try {
        return new JwtPreAuthenticationToken(authToken, bearerRequestHeader,
            jwtTokenService.getUsernameFromToken(authToken));
      } catch (final IllegalArgumentException e) {
        LOG.error("an error occured during getting username from token", e);
      } catch (final ExpiredJwtException e) { // NOSONAR
        LOG.warn("the token is expired and not valid anymore");
        throw e;
      }
    } else {
      LOG.warn("couldn't find bearer string, will ignore the header");
      String username = AuthenConstantUtil.PREFIX_GUEST + UUID.randomUUID().toString();
      return new JwtPreAuthenticationToken(authToken, bearerRequestHeader,
          jwtTokenService.generateToken(username));
    }
    throw new BadCredentialsException("Invalid token: " + authToken);
  }
}
