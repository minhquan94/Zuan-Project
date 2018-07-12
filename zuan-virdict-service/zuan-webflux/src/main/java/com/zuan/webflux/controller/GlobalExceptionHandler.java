/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuan.webflux.config.security.jwt.JwtAuthenticationResponse;
import com.zuan.webflux.service.JwtTokenService;
import com.zuan.webflux.util.AuthenConstantUtil;

import io.jsonwebtoken.ExpiredJwtException;
import reactor.core.publisher.Mono;

/**
 * The Class ResponseEntityException.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /** The jwt token service. */
  @Autowired
  private JwtTokenService jwtTokenService;

  /** The object mapper. */
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.web.server.WebExceptionHandler#handle(org.springframework.web.server.ServerWebExchange,
   *      java.lang.Throwable)
   */
  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    return Mono.fromRunnable(() -> handleException(exchange, ex));
  }

  /**
   * Handle exception.
   *
   * @param exchange
   *          the exchange
   * @param ex
   *          the ex
   */
  private void handleException(ServerWebExchange exchange, Throwable ex) {
    final ServerHttpResponse response = exchange.getResponse();
    if (ex instanceof BadCredentialsException) {
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      response.getHeaders().add("text", "BadCredentialsException");
      LOG.warn("{}", ex.getMessage());
    } else if (ex instanceof ExpiredJwtException) {
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      response.getHeaders().add("text", "ExpiredJwtException");
      final String username = AuthenConstantUtil.PREFIX_GUEST + UUID.randomUUID().toString();
      final JwtAuthenticationResponse authenticationResponse =
          new JwtAuthenticationResponse(jwtTokenService.generateToken(username), username);
      try {
        response.getHeaders().add("authenToken",
            objectMapper.writeValueAsString(authenticationResponse));
        LOG.warn("{}", ex.getMessage());
      } catch (JsonProcessingException e) {
        LOG.error("", e);
      }
    } else {
      LOG.error("", ex);
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
