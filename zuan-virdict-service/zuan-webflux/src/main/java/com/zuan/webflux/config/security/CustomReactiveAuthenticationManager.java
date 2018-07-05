/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * The Class CustomReactiveAuthenticationManager.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

  /** The logger. */
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /** The user details service. */
  private final ReactiveUserDetailsService userDetailsService;

  /** The jwt token util. */
  private final JwtTokenUtil jwtTokenUtil;

  /** The password encoder. */
  private PasswordEncoder passwordEncoder =
      PasswordEncoderFactories.createDelegatingPasswordEncoder();

  /**
   * Instantiates a new custom reactive authentication manager.
   *
   * @param userDetailsService
   *          the user details service
   * @param jwtTokenUtil
   *          the jwt token util
   */
  public CustomReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
      JwtTokenUtil jwtTokenUtil) {
    Assert.notNull(userDetailsService, "userDetailsService cannot be null");
    Assert.notNull(jwtTokenUtil, "jwtTokenUtil cannot be null");

    this.userDetailsService = userDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.authentication.ReactiveAuthenticationManager#authenticate(org.springframework.security.core.Authentication)
   */
  @Override
  public Mono<Authentication> authenticate(final Authentication authentication) {
    if (authentication instanceof JwtPreAuthenticationToken) {
      return Mono.just(authentication).switchIfEmpty(Mono.defer(this::raiseBadCredentials))
          .cast(JwtPreAuthenticationToken.class).flatMap(this::authenticateToken)
          .publishOn(Schedulers.parallel()).onErrorResume(e -> raiseBadCredentials())
          .map(u -> new JwtAuthenticationToken(u.getUsername(), u.getPassword(),
              u.getAuthorities()));
    }

    return Mono.just(authentication);
  }

  /**
   * Raise bad credentials.
   *
   * @param <T>
   *          the generic type
   * @return the mono
   */
  private <T> Mono<T> raiseBadCredentials() {
    return Mono.error(new BadCredentialsException("Invalid Credentials"));
  }

  /**
   * Authenticate token.
   *
   * @param jwtPreAuthenticationToken
   *          the jwt pre authentication token
   * @return the mono
   */
  private Mono<UserDetails> authenticateToken(
      final JwtPreAuthenticationToken jwtPreAuthenticationToken) {
    try {
      final String authToken = jwtPreAuthenticationToken.getAuthToken();
      final String username = jwtPreAuthenticationToken.getUsername();

      logger.info("checking authentication for user " + username);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        if (jwtTokenUtil.validateToken(authToken)) {
          logger.info("authenticated user " + username + ", setting security context");
          return this.userDetailsService.findByUsername(username);
        }
      }
    } catch (final Exception e) {
      throw new BadCredentialsException("Invalid token...");
    }

    return null;
  }

  /**
   * Sets the password encoder.
   *
   * @param passwordEncoder
   *          the new password encoder
   */
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Gets the password encoder.
   *
   * @return the password encoder
   */
  public PasswordEncoder getPasswordEncoder() {
    return passwordEncoder;
  }
}
