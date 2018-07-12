/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zuan.webflux.dto.admin.AdminMenuItemDto;
import com.zuan.webflux.jpa.entity.AdminMenuItemEntity;
import com.zuan.webflux.jpa.repository.AdminMenuItemRepository;
import com.zuan.webflux.model.MenuAdminItem;

/**
 * The Class AdminService.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Service
public class AdminService {

  /** The navbar admin detail repository. */
  @Autowired
  private AdminMenuItemRepository menuItemRepository;

  /**
   * Gets the narbar items.
   *
   * @return the narbar items
   */
  @Cacheable(value = "navbarItemsTopCache")
  public List<AdminMenuItemDto> getNavbarTopItems() {
    final List<AdminMenuItemEntity> items =
        menuItemRepository.findByItemType(MenuAdminItem.TOP.getType());
    if (CollectionUtils.isEmpty(items)) {
      return new ArrayList<>();
    }
    return items.stream().map(AdminMenuItemDto::new).collect(Collectors.toList());
  }
}
