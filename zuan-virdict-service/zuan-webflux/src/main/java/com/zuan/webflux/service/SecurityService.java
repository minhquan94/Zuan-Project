package com.zuan.webflux.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
    return authentication == null;
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
