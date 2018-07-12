/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.jpa.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * The Class RoleEntity.
 */
@Entity(name = "Roles")
public class RoleEntity implements BaseIdEntity {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 2199501402431779631L;

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "RoleId")
  private Long id;

  /** The role code. */
  @Column(name = "RoleCode")
  private String roleCode;

  /** The created time. */
  @Column(name = "CreatedTime")
  private Timestamp createdTime;

  /** The role description. */
  @Column(name = "RoleDescription")
  private String roleDescription;

  /** The role name. */
  @Column(name = "RoleName")
  private String roleName;

  /** The role type. */
  @Column(name = "RoleType")
  private short roleType;

  /** The updated time. */
  @Column(name = "UpdatedTime")
  private Timestamp updatedTime;

  /** The created user id. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CreatedUserId")
  private UserEntity createdUser;

  /** The updated user. */
  // bi-directional many-to-one association to UserEntity
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "UpdatedUserId")
  private UserEntity updatedUser;

  /** The users. */
  // bi-directional many-to-many association to UserEntity
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private List<UserEntity> users;

  /**
   * Gets the role code.
   *
   * @return the role code
   */
  public String getRoleCode() {
    return roleCode;
  }

  /**
   * Sets the role code.
   *
   * @param roleCode
   *          the new role code
   */
  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  /**
   * Gets the created time.
   *
   * @return the created time
   */
  public Timestamp getCreatedTime() {
    return createdTime;
  }

  /**
   * Sets the created time.
   *
   * @param createdTime
   *          the new created time
   */
  public void setCreatedTime(Timestamp createdTime) {
    this.createdTime = createdTime;
  }

  /**
   * Gets the role description.
   *
   * @return the role description
   */
  public String getRoleDescription() {
    return roleDescription;
  }

  /**
   * Sets the role description.
   *
   * @param roleDescription
   *          the new role description
   */
  public void setRoleDescription(String roleDescription) {
    this.roleDescription = roleDescription;
  }

  /**
   * Gets the role name.
   *
   * @return the role name
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * Sets the role name.
   *
   * @param roleName
   *          the new role name
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   * Gets the role type.
   *
   * @return the role type
   */
  public short getRoleType() {
    return roleType;
  }

  /**
   * Sets the role type.
   *
   * @param roleType
   *          the new role type
   */
  public void setRoleType(short roleType) {
    this.roleType = roleType;
  }

  /**
   * Gets the updated time.
   *
   * @return the updated time
   */
  public Timestamp getUpdatedTime() {
    return updatedTime;
  }

  /**
   * Sets the updated time.
   *
   * @param updatedTime
   *          the new updated time
   */
  public void setUpdatedTime(Timestamp updatedTime) {
    this.updatedTime = updatedTime;
  }

  /**
   * Gets the created user.
   *
   * @return the created user
   */
  public UserEntity getCreatedUser() {
    return createdUser;
  }

  /**
   * Sets the created user.
   *
   * @param createdUser
   *          the new created user
   */
  public void setCreatedUser(UserEntity createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * Gets the updated user.
   *
   * @return the updated user
   */
  public UserEntity getUpdatedUser() {
    return updatedUser;
  }

  /**
   * Sets the updated user.
   *
   * @param updatedUser
   *          the new updated user
   */
  public void setUpdatedUser(UserEntity updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * Gets the users.
   *
   * @return the users
   */
  public List<UserEntity> getUsers() {
    return users;
  }

  /**
   * Sets the users.
   *
   * @param users
   *          the new users
   */
  public void setUsers(List<UserEntity> users) {
    this.users = users;
  }

  /**
   * Sets the id.
   *
   * @param id
   *          the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.webflux.jpa.entity.BaseIdEntity#getId()
   */
  @Override
  public Long getId() {
    return id;
  }

}
