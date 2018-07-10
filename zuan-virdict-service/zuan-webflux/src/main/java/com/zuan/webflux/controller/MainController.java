/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.controller;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zuan.webflux.model.UserRoleEnum;
import com.zuan.webflux.service.SecurityService;

import reactor.core.publisher.Mono;

/**
 * The Class MainController.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Controller
public class MainController {

  /** The Constant INDEX. */
  private static final String INDEX = "index";

  /** The security service. */
  @Autowired
  private SecurityService securityService;

  /**
   * Index.
   *
   * @return the mono
   */
  @GetMapping(value = {"/", "/home" })
  public Mono<String> index() {
    return Mono.just(INDEX);
  }

  /**
   * Login.
   *
   * @return the mono
   */
  @GetMapping(value = "/login/admin")
  public Mono<String> login() {
    return Mono.just(INDEX);
  }

  /**
   * Shop.
   *
   * @return the mono
   */
  @GetMapping(value = "/shop")
  public Mono<String> shop() {
    return Mono.just(INDEX);
  }

  /**
   * Admin.
   *
   * @return the mono
   */
  @GetMapping("/admin")
  public Mono<String> admin() {
    if (securityService.isAnonymous()
        || !isAdminRole(securityService.getAuthentication().getAuthorities())) {
      return Mono.just("redirect:/login/admin");
    }
    return Mono.just(INDEX);
  }

  /**
   * Checks if is admin role.
   *
   * @param authorities
   *          the authorities
   * @return true, if is admin role
   */
  private boolean isAdminRole(Collection< ? extends GrantedAuthority> authorities) {
    return authorities.stream().anyMatch(filter -> StringUtils
        .equalsIgnoreCase(filter.getAuthority(), UserRoleEnum.ADMIN.name()));
  }
}
