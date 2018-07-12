/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.rest;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuan.webflux.config.security.jwt.JwtAuthenticationRequest;
import com.zuan.webflux.config.security.jwt.JwtAuthenticationResponse;
import com.zuan.webflux.config.security.jwt.JwtAuthenticationToken;
import com.zuan.webflux.model.GuestUser;
import com.zuan.webflux.model.UserRoleEnum;
import com.zuan.webflux.service.CustomReactiveUserDetailsService;
import com.zuan.webflux.service.JwtTokenService;
import com.zuan.webflux.service.SecurityService;
import com.zuan.webflux.util.AuthenConstantUtil;

import reactor.core.publisher.Mono;

/**
 * The Class LoginResource.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@RestController
@RequestMapping(path = "/rest/login", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
public class LoginResource {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

  /** The jwt token service. */
  @Autowired
  private JwtTokenService jwtTokenService;

  /** The security service. */
  @Autowired
  private SecurityService securityService;

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
  @PostMapping("/admin")
  @CrossOrigin("http://localhost:4200")
  public Mono<ResponseEntity<JwtAuthenticationResponse>> login(
      @RequestBody JwtAuthenticationRequest authenticationRequest) {
    final String username = authenticationRequest.getUsername();
    final String password = authenticationRequest.getPassword();
    LOG.info("Require login with user: {}", username);
    return reactiveUserDetailsService.findByUsername(username)
        .filter(user -> user.getPassword().equals(password) && user.getAuthorities().stream()
            .anyMatch(authority -> StringUtils.equalsIgnoreCase(authority.getAuthority(),
                UserRoleEnum.ADMIN.name())))
        .map(user -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(
            new JwtAuthenticationResponse(jwtTokenService.generateToken(user), username)))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Logout.
   *
   * @return the mono
   */
  @PostMapping("/logout")
  @CrossOrigin("http://localhost:4200")
  public Mono<ResponseEntity<JwtAuthenticationResponse>> logout() {
    final GuestUser guestUser =
        new GuestUser(AuthenConstantUtil.PREFIX_GUEST + UUID.randomUUID().toString());
    final JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(
        guestUser.getUsername(), guestUser.getPassword(), guestUser.getAuthorities(),
        jwtTokenService.generateToken(guestUser.getUsername()));
    securityService.setAuthentication(authenticationToken);
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(new JwtAuthenticationResponse(authenticationToken.getToken(),
            authenticationToken.getPrincipal())));
  }
}
