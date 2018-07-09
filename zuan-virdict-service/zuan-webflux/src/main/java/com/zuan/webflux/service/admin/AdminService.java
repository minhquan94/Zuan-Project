/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuan.webflux.dto.admin.NavbarItemAdminDto;
import com.zuan.webflux.model.NavbarItemAdmin;
import com.zuan.webflux.repository.NavbarAdminRepository;

/**
 * The Class AdminService.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Service
public class AdminService {

  /** The navbar admin detail repository. */
  @Autowired
  private NavbarAdminRepository navbarAdminRepository;

  /**
   * Gets the narbar items.
   *
   * @return the narbar items
   */
  public List<NavbarItemAdminDto> getNarbarItems() {
    final List<NavbarItemAdmin> items = navbarAdminRepository.findAll();
    return items.stream().map(NavbarItemAdminDto::new).collect(Collectors.toList());
  }
}
