/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.rest.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuan.webflux.dto.admin.NavbarItemAdminDto;
import com.zuan.webflux.service.admin.AdminService;

import reactor.core.publisher.Flux;

/**
 * The Class AdminResource.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@RestController
@RequestMapping("/rest/admin")
public class AdminResource {

  /** The navbar admin detail repository. */
  @Autowired
  private AdminService adminService;

  /**
   * Gets the navbar items.
   *
   * @return the navbar items
   */
  @GetMapping("/navbar-items")
  @CrossOrigin("http://localhost:4200")
  public Flux<ResponseEntity<List<NavbarItemAdminDto>>> getNavbarItems() {
    final List<NavbarItemAdminDto> itemsReturn = adminService.getNarbarItems();
    return Flux.just(ResponseEntity.ok().body(itemsReturn));
  }

}
