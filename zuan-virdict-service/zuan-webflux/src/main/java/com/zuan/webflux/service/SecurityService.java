/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zuan.webflux.config.security.jwt.JwtAuthenticationConverter;

/**
 * The Class SecurityService.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Service
public class SecurityService {

  /** The authentication. */
  private Authentication authentication;

  /**
   * Checks if is anonymous.
   *
   * @param authentication
   *          the authentication
   * @return true, if is anonymous
   */
  public boolean isAnonymous(Authentication authentication) {
    return authentication == null || authentication.getPrincipal() == null
        || StringUtils.startsWithIgnoreCase(authentication.getPrincipal().toString(),
            JwtAuthenticationConverter.PREFIX_GUEST);
  }

  /**
   * Checks if is anonymous.
   *
   * @return true, if is anonymous
   */
  public boolean isAnonymous() {
    return isAnonymous(authentication);
  }

  /**
   * Gets the authentication.
   *
   * @return the authentication
   */
  public Authentication getAuthentication() {
    return authentication;
  }

  /**
   * Sets the authentication.
   *
   * @param authentication
   *          the new authentication
   */
  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

}
