/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * The Class JWTAuthenticationToken.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -2177512967009338056L;

  /** The token. */
  private String token;

  /**
   * Instantiates a new JWT authentication token.
   *
   * @param principal
   *          the principal
   * @param credentials
   *          the credentials
   * @param authorities
   *          the authorities
   */
  public JwtAuthenticationToken(Object principal, Object credentials,
      Collection< ? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }

  /**
   * Instantiates a new JWT authentication token.
   *
   * @param principal
   *          the principal
   * @param credentials
   *          the credentials
   */
  public JwtAuthenticationToken(Object principal, Object credentials) {
    super(principal, credentials);
  }

  /**
   * Instantiates a new JWT authentication token.
   *
   * @param principal
   *          the principal
   * @param credentials
   *          the credentials
   * @param authorities
   *          the authorities
   * @param token
   *          the token
   */
  public JwtAuthenticationToken(Object principal, Object credentials,
      Collection< ? extends GrantedAuthority> authorities, String token) {
    super(principal, credentials, authorities);
    this.token = token;
  }

  /**
   * Gets the token.
   *
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken#getCredentials()
   */
  @Override
  public Object getCredentials() {
    return null;
  }

}
