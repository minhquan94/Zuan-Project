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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.zuan.webflux.config.security.jwt.JwtAuthenticationToken;
import com.zuan.webflux.config.security.jwt.JwtPreAuthenticationToken;
import com.zuan.webflux.model.GuestUser;
import com.zuan.webflux.service.JwtTokenService;

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
  private static final Logger LOG =
      LoggerFactory.getLogger(CustomReactiveAuthenticationManager.class);

  /** The user details service. */
  private final ReactiveUserDetailsService userDetailsService;

  /** The jwt token service. */
  private final JwtTokenService jwtTokenService;

  /** The password encoder. */
  private PasswordEncoder passwordEncoder = new CustomPasswordEncoder();

  /**
   * Instantiates a new custom reactive authentication manager.
   *
   * @param userDetailsService
   *          the user details service
   * @param jwtTokenService
   *          the jwt token service
   */
  public CustomReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
      JwtTokenService jwtTokenService) {
    this.userDetailsService = userDetailsService;
    this.jwtTokenService = jwtTokenService;
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
  public <T> Mono<T> raiseBadCredentials() {
    return Mono.error(new BadCredentialsException("Invalid Credentials"));
  }

  /**
   * Authenticate token.
   *
   * @param jwtPreAuthenticationToken
   *          the jwt pre authentication token
   * @return the mono
   */
  public Mono<UserDetails> authenticateToken(
      final JwtPreAuthenticationToken jwtPreAuthenticationToken) {
    try {
      final String authToken = jwtPreAuthenticationToken.getAuthToken();
      final String username = jwtPreAuthenticationToken.getUsername();

      LOG.info("checking authentication for user {}", username);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
          && jwtTokenService.validateToken(authToken)) {
        LOG.info("authenticated user {}, setting security context", username);
        return this.userDetailsService.findByUsername(username)
            .defaultIfEmpty(guestUser(username));
      }
    } catch (final Exception e) {
      throw new BadCredentialsException("Invalid token: " + e);
    }

    return null;
  }

  /**
   * Guest user.
   *
   * @param username
   *          the username
   * @return the user details
   */
  private UserDetails guestUser(String username) {
    return new GuestUser(username);
  }

  /**
   * Sets the password encoder.
   *
   * @param passwordEncoder
   *          the new password encoder
   */
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
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
