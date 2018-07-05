/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
  @GetMapping(value = "/login")
  public Mono<String> login() {
    return Mono.just(INDEX);
  }

  /**
   * Admin.
   *
   * @return the mono
   */
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/admin")
  public Mono<String> admin() {
    return Mono.just(INDEX);
  }
}
