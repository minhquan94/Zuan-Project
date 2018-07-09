/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security.jwt;

import javax.security.auth.Subject;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * The Class JwtPreAuthenticationToken.
 */
public class JwtPreAuthenticationToken extends AbstractAuthenticationToken {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3508858120893154488L;

  /** The auth token. */
  private final String authToken;

  /** The bearer request header. */
  private final String bearerRequestHeader;

  /** The username. */
  private final String username;

  /**
   * Instantiates a new jwt pre authentication token.
   *
   * @param authToken
   *          the auth token
   * @param bearerRequestHeader
   *          the bearer request header
   * @param username
   *          the username
   */
  public JwtPreAuthenticationToken(final String authToken, final String bearerRequestHeader,
      final String username) {
    super(null);
    this.authToken = authToken;
    this.bearerRequestHeader = bearerRequestHeader;
    this.username = username;
    setAuthenticated(false);
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.Authentication#getCredentials()
   */
  @Override
  public Object getCredentials() {
    return null;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.Authentication#getPrincipal()
   */
  @Override
  public Object getPrincipal() {
    return username;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.security.Principal#implies(javax.security.auth.Subject)
   */
  @Override
  public boolean implies(Subject subject) {
    return false;
  }

  /**
   * Gets the auth token.
   *
   * @return the auth token
   */
  public String getAuthToken() {
    return authToken;
  }

  /**
   * Gets the bearer request header.
   *
   * @return the bearer request header
   */
  public String getBearerRequestHeader() {
    return bearerRequestHeader;
  }

  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }
}
