/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.jpa.repository;

import org.springframework.transaction.annotation.Transactional;

import com.zuan.webflux.jpa.entity.UserEntity;

/**
 * The Interface UserRepository.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Transactional
public interface UserRepository extends BaseJpaRepository<UserEntity, Long> {

  /**
   * Find by user name.
   *
   * @param userName
   *          the user name
   * @return the mono
   */
  UserEntity findByUserName(String userName);
}
