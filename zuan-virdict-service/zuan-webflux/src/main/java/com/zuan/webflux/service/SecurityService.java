/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.service;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;

import com.zuan.webflux.model.UserRoleEnum;
import com.zuan.webflux.util.AuthenConstantUtil;

/**
 * The Class SecurityService.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SecurityService implements SecurityContext {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 8458082764395614688L;

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
            AuthenConstantUtil.PREFIX_GUEST);
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
   * Checks if is admin role.
   *
   * @param authorities
   *          the authorities
   * @return true, if is admin role
   */
  public boolean isAdminRole(Collection< ? extends GrantedAuthority> authorities) {
    return authorities.stream().anyMatch(filter -> StringUtils
        .equalsIgnoreCase(filter.getAuthority(), UserRoleEnum.ADMIN.name()));
  }

  /**
   * Gets the authentication.
   *
   * @return the authentication
   */
  @Override
  public Authentication getAuthentication() {
    return authentication;
  }

  /**
   * Sets the authentication.
   *
   * @param authentication
   *          the new authentication
   */
  @Override
  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

}
