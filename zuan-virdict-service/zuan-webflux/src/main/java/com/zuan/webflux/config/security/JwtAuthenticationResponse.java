/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security;

import java.io.Serializable;

/**
 * The Class JwtAuthenticationResponse.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
public class JwtAuthenticationResponse implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The token. */
  private String token;

  /** The username. */
  private String username;

  /**
   * Instantiates a new jwt authentication response.
   *
   * @param token
   *          the token
   * @param username
   *          the username
   */
  public JwtAuthenticationResponse(String token, String username) {
    this.token = token;
    this.username = username;
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
   * Sets the token.
   *
   * @param token
   *          the new token
   */
  public void setToken(String token) {
    this.token = token;
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
   * Sets the username.
   *
   * @param username
   *          the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }
}
