/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuan.webflux.config.security.jwt.JwtAuthenticationRequest;
import com.zuan.webflux.config.security.jwt.JwtAuthenticationResponse;
import com.zuan.webflux.service.CustomReactiveUserDetailsService;
import com.zuan.webflux.service.JwtTokenService;

import reactor.core.publisher.Mono;

/**
 * The Class LoginResource.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@RestController
@RequestMapping(path = "/rest/login", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
public class LoginResource {

  /** The jwt token service. */
  @Autowired
  private JwtTokenService jwtTokenService;

  /** The reactive user details service. */
  @Autowired
  private CustomReactiveUserDetailsService reactiveUserDetailsService;

  /**
   * Login.
   *
   * @param authenticationRequest
   *          the authentication request
   * @return the mono
   */
  @PostMapping
  @CrossOrigin("http://localhost:4200")
  public Mono<ResponseEntity<JwtAuthenticationResponse>> login(
      @RequestBody JwtAuthenticationRequest authenticationRequest) {
    final String username = authenticationRequest.getUsername();
    final String password = authenticationRequest.getPassword();
    return reactiveUserDetailsService.findByUsername(username)
        .filter(user -> user.getPassword().equals(password))
        .map(user -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(
            new JwtAuthenticationResponse(jwtTokenService.generateToken(user), username)))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Test.
   *
   * @return the mono
   */
  @GetMapping("/test")
  @CrossOrigin("http://localhost:4200")
  public Mono<ResponseEntity<String>> test() {
    return Mono.just(ResponseEntity.ok().body("OK-------------"));
  }

}
