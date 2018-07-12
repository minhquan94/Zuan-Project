/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.rest;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuan.webflux.config.security.jwt.JwtAuthenticationResponse;
import com.zuan.webflux.config.security.jwt.JwtAuthenticationToken;
import com.zuan.webflux.model.GuestUser;
import com.zuan.webflux.service.JwtTokenService;
import com.zuan.webflux.service.SecurityService;
import com.zuan.webflux.util.AuthenConstantUtil;

import reactor.core.publisher.Mono;

/**
 * The Class CommonResource.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@RestController
@RequestMapping(path = "/rest/common", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
public class CommonResource {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(CommonResource.class);

  /** The security service. */
  @Autowired
  private SecurityService securityService;

  /** The jwt token service. */
  @Autowired
  private JwtTokenService jwtTokenService;

  /**
   * Gets the user.
   *
   * @return the user
   */
  @PostMapping("/request-authen")
  @CrossOrigin("http://localhost:4200")
  public Mono<ResponseEntity<JwtAuthenticationResponse>> getUser() {
    JwtAuthenticationToken authentication =
        (JwtAuthenticationToken) securityService.getAuthentication();
    if (authentication == null) {
      final GuestUser guestUser =
          new GuestUser(AuthenConstantUtil.PREFIX_GUEST + UUID.randomUUID().toString());
      authentication = new JwtAuthenticationToken(
          guestUser.getUsername(), guestUser.getPassword(), guestUser.getAuthorities(),
          jwtTokenService.generateToken(guestUser.getUsername()));
      securityService.setAuthentication(authentication);
    }
    LOG.info("Authorized token for {}", authentication.getPrincipal());
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(new JwtAuthenticationResponse(authentication.getToken(),
            authentication.getPrincipal())));
  }
}
