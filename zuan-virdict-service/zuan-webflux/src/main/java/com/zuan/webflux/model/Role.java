/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Role.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Entity
@Table(name = "role")
public class Role {

  /** The role id. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id")
  private int roleId;

  /** The role. */
  @Column(name = "role")
  private String roleName;

  /**
   * Instantiates a new role.
   */
  public Role() {
    // do nothing
  }

  /**
   * Gets the role id.
   *
   * @return the role id
   */
  public int getRoleId() {
    return roleId;
  }

  /**
   * Sets the role id.
   *
   * @param roleId
   *          the new role id
   */
  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  /**
   * Gets the role.
   *
   * @return the role
   */
  public String getRole() {
    return roleName;
  }

  /**
   * Sets the role.
   *
   * @param role
   *          the new role
   */
  public void setRole(String role) {
    this.roleName = role;
  }
}
