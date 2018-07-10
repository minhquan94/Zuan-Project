/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security.jwt;

import java.util.Collection;

import javax.security.auth.Subject;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

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
   * Instantiates a new jwt pre authentication token.
   *
   * @param authToken
   *          the auth token
   * @param bearerRequestHeader
   *          the bearer request header
   * @param username
   *          the username
   * @param authorities
   *          the authorities
   */
  public JwtPreAuthenticationToken(final String authToken, final String bearerRequestHeader,
      final String username, Collection< ? extends GrantedAuthority> authorities) {
    super(authorities);
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

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  /**
   * Equals.
   *
   * @param obj
   *          the obj
   * @return true, if successful
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    JwtPreAuthenticationToken other = (JwtPreAuthenticationToken) obj;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }

}
