/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.dto.admin;

import com.zuan.webflux.model.NavbarAdminDetail;

/**
 * The Class NavbarItemDetail.
 */
public class NavbarItemDetailDto {

  /** The name. */
  private String name;

  /** The link. */
  private String link;

  /** The count. */
  private Integer count;

  /** The group. */
  private short group;

  /**
   * Instantiates a new navbar item detail.
   */
  public NavbarItemDetailDto() {
    super();
  }

  /**
   * Instantiates a new navbar item detail.
   *
   * @param name
   *          the name
   * @param link
   *          the link
   * @param count
   *          the count
   */
  public NavbarItemDetailDto(String name, String link, Integer count) {
    this.name = name;
    this.link = link;
    this.count = count;
  }

  /**
   * Instantiates a new navbar item detail dto.
   *
   * @param adminDetail
   *          the admin detail
   */
  public NavbarItemDetailDto(NavbarAdminDetail adminDetail) {
    this.name = adminDetail.getName();
    this.link = adminDetail.getLink();
    this.count = adminDetail.getCount();
    this.group = adminDetail.getNavbarItemAdmin().getGroup();
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
   * Gets the count.
   *
   * @return the count
   */
  public Integer getCount() {
    return count;
  }

  /**
   * Sets the count.
   *
   * @param count
   *          the new count
   */
  public void setCount(Integer count) {
    this.count = count;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + group;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    NavbarItemDetailDto other = (NavbarItemDetailDto) obj;
    if (group != other.group)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "NavbarItemDetailDto [name=" + name + ", link=" + link + ", count=" + count + "]";
  }

}
