/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.rest.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuan.webflux.dto.admin.NavbarItemAdminDto;
import com.zuan.webflux.model.NavbarItemAdmin;
import com.zuan.webflux.repository.NavbarAdminRepository;

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
  private NavbarAdminRepository navbarAdminRepository;

  /**
   * Gets the navbar items.
   *
   * @return the navbar items
   */
  @GetMapping("/navbar-items")
  @CrossOrigin("http://localhost:4200")
  public Flux<ResponseEntity<List<NavbarItemAdminDto>>> getNavbarItems() {
    final List<NavbarItemAdmin> items = navbarAdminRepository.findAll();
    final List<NavbarItemAdminDto> itemsReturn =
        items.stream().map(NavbarItemAdminDto::new).collect(Collectors.toList());
    return Flux.just(ResponseEntity.ok().body(itemsReturn));
  }

}