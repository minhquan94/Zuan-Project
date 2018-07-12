/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.dto.admin;

import javax.persistence.Column;

import com.zuan.webflux.jpa.entity.AdminMenuItemEntity;

/**
 * The Class AdminMenuItemDto.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
public class AdminMenuItemDto {

  /** The name. */
  private String name;

  /** The link. */
  @Column(name = "Link")
  private String link;

  /** The type. */
  private short type;

  /** The created user. */
  private AdminMenuItemEntity parent;

  /**
   * Instantiates a new admin menu item dto.
   */
  public AdminMenuItemDto() {
    super();
  }

  /**
   * Instantiates a new admin menu item dto.
   *
   * @param name
   *          the name
   * @param link
   *          the link
   * @param type
   *          the type
   * @param parent
   *          the parent
   */
  public AdminMenuItemDto(String name, String link, short type, AdminMenuItemEntity parent) {
    this.name = name;
    this.link = link;
    this.type = type;
    this.parent = parent;
  }

  /**
   * Instantiates a new admin menu item dto.
   *
   * @param entity
   *          the entity
   */
  public AdminMenuItemDto(AdminMenuItemEntity entity) {
    this.name = entity.getName();
    this.link = entity.getLink();
    this.type = entity.getType();
    this.parent = entity.getParent();
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name
   *          the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the link.
   *
   * @return the link
   */
  public String getLink() {
    return link;
  }

  /**
   * Sets the link.
   *
   * @param link
   *          the new link
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public short getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type
   *          the new type
   */
  public void setType(short type) {
    this.type = type;
  }

  /**
   * Gets the parent.
   *
   * @return the parent
   */
  public AdminMenuItemEntity getParent() {
    return parent;
  }

  /**
   * Sets the parent.
   *
   * @param parent
   *          the new parent
   */
  public void setParent(AdminMenuItemEntity parent) {
    this.parent = parent;
  }

}
