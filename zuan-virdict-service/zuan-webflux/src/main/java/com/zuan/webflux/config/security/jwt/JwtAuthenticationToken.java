/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security.jwt;

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
  public JwtAuthenticationToken(String principal, String credentials,
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
  public JwtAuthenticationToken(String principal, String credentials) {
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
  public JwtAuthenticationToken(String principal, String credentials,
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
   * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken#getPrincipal()
   */
  @Override
  public String getPrincipal() {
    return (String) super.getPrincipal();
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.authentication.AbstractAuthenticationToken#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((token == null) ? 0 : token.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.authentication.AbstractAuthenticationToken#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final JwtAuthenticationToken other = (JwtAuthenticationToken) obj;
    if (token == null) {
      if (other.token != null) {
        return false;
      }
    } else if (!token.equals(other.token)) {
      return false;
    }
    return true;
  }

}
