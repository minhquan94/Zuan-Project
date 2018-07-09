/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zuan.webflux.model.NavbarItemAdmin;

/**
 * The Interface NavbarAdminRepository.
 */
@Repository
public interface NavbarAdminRepository extends JpaRepository<NavbarItemAdmin, Integer> {

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
   */
  @Override
  @Query("SELECT n FROM NavbarItemAdmin n JOIN fetch n.navbarAdminDetails nd")
  List<NavbarItemAdmin> findAll();

}
