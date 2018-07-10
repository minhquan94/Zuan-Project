/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zuan.webflux.dto.admin.NavbarItemAdminDto;
import com.zuan.webflux.dto.admin.NavbarItemDetailDto;
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
  @Cacheable(value = "navbarItemsCache")
  public List<NavbarItemAdminDto> getNarbarItems() {
    final List<NavbarItemAdmin> items = navbarAdminRepository.findAll();

    final List<NavbarItemAdminDto> itemAdmins = new ArrayList<>();
    items.forEach(item -> {
      final NavbarItemAdminDto adminDto = new NavbarItemAdminDto(item);
      if (!itemAdmins.contains(adminDto)) {
        itemAdmins.add(adminDto);
      }
      if (CollectionUtils.isEmpty(adminDto.getItemDetails())) {
        adminDto.setItemDetails(new ArrayList<>());
        return;
      }
      item.getNavbarAdminDetails().forEach(detail -> {
        final NavbarItemDetailDto detailDto = new NavbarItemDetailDto(detail);
        if (!adminDto.getItemDetails().contains(detailDto)) {
          adminDto.getItemDetails().add(detailDto);
        }
      });
    });
    return itemAdmins;
  }
}
