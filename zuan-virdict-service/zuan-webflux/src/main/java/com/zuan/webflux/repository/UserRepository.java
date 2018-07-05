/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zuan.webflux.model.Users;

/**
 * The Interface UserRepository.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

  /**
   * Find by user name.
   *
   * @param userName
   *          the user name
   * @return the mono
   */
  Users findByUserName(String userName);
}
