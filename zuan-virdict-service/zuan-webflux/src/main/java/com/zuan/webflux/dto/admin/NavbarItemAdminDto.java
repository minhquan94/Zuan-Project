/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.dto.admin;

import java.util.List;

import com.zuan.webflux.model.NavbarItemAdmin;

/**
 * The Class NavbarItemAdmin.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
public class NavbarItemAdminDto {

  /** The title. */
  private String title;

  /** The group. */
  private short group;

  /** The item details. */
  private List<NavbarItemDetailDto> itemDetails;

  /**
   * Instantiates a new navbar item admin.
   */
  public NavbarItemAdminDto() {
    super();
  }

  /**
   * Instantiates a new navbar item admin dto.
   *
   * @param title
   *          the title
   * @param group
   *          the group
   * @param itemDetails
   *          the item details
   */
  public NavbarItemAdminDto(String title, short group, List<NavbarItemDetailDto> itemDetails) {
    this.title = title;
    this.group = group;
    this.itemDetails = itemDetails;
  }

  /**
   * Instantiates a new navbar item admin dto.
   *
   * @param itemAdmin
   *          the item admin
   */
  public NavbarItemAdminDto(NavbarItemAdmin itemAdmin) {
    this.title = itemAdmin.getTitle();
    this.group = itemAdmin.getGroup();
    itemAdmin.getNavbarAdminDetails().forEach(NavbarItemDetailDto::new);
  }

  /**
   * Gets the title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title.
   *
   * @param title
   *          the new title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the item details.
   *
   * @return the item details
   */
  public List<NavbarItemDetailDto> getItemDetails() {
    return itemDetails;
  }

  /**
   * Sets the item details.
   *
   * @param itemDetails
   *          the new item details
   */
  public void setItemDetails(List<NavbarItemDetailDto> itemDetails) {
    this.itemDetails = itemDetails;
  }

  /**
   * Gets the group.
   *
   * @return the group
   */
  public short getGroup() {
    return group;
  }

  /**
   * Sets the group.
   *
   * @param group
   *          the new group
   */
  public void setGroup(short group) {
    this.group = group;
  }

}
