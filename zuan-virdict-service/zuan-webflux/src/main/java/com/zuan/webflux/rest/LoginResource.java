/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuan.webflux.dto.UserDto;

import reactor.core.publisher.Mono;

/**
 * The Class LoginResource.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@RestController
@RequestMapping("/rest/login")
public class LoginResource {

  /**
   * Login.
   *
   * @param dto
   *          the dto
   * @return the user dto
   */
  @PostMapping
  public Mono<UserDto> login(@RequestBody UserDto dto) {
    return null;
  }
}
