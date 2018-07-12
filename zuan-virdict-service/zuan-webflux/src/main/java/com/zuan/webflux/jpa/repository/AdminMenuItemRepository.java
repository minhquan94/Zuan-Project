/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.zuan.webflux.jpa.entity.AdminMenuItemEntity;

/**
 * The Interface AdminMenuItemRepository.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Transactional
public interface AdminMenuItemRepository extends BaseJpaRepository<AdminMenuItemEntity, Long> {

  /**
   * Find by item title.
   *
   * @param type
   *          the type
   * @return the list
   */
  @Query("SELECT a FROM AdminMenuItem a WHERE a.type = :type")
  List<AdminMenuItemEntity> findByItemType(@Param(value = "type") short type);
}
