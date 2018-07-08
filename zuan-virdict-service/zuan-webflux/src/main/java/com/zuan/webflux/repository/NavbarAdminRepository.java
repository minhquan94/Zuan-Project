/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zuan.webflux.model.NavbarItemAdmin;

/**
 * The Interface NavbarAdminRepository.
 */
@Repository
public interface NavbarAdminRepository extends JpaRepository<NavbarItemAdmin, Integer> {

}
