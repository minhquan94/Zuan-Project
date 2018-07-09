/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import reactor.core.publisher.Mono;

/**
 * The Class ResponseEntityException.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@ControllerAdvice
public class ResponseEntityException {

  /**
   * Servlet exception.
   *
   * @param e
   *          the e
   * @return the mono
   */
  @ExceptionHandler(value = {ExpiredJwtException.class })
  public Mono<ResponseEntity<Object>> servletException(ExpiredJwtException e) {
    return Mono.just(ResponseEntity.created(URI.create("/login")).build());
  }
}
