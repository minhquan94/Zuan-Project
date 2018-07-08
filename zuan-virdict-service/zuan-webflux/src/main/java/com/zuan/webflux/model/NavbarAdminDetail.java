/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The Class NavbarAdminDetail.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Entity
@Table(name = "navbar_item_admin_detail")
public class NavbarAdminDetail {

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "item_detail_id")
  private int id;

  /** The name. */
  @Column(name = "item_detail_name")
  private String name;

  /** The link. */
  @Column(name = "item_detail_link")
  private String link;

  /** The count. */
  @Column(name = "item_detail_count")
  private Integer count;

  /** The navbar item admin. */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "navbar_item_admnin_id")
  private NavbarItemAdmin navbarItemAdmin;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id
   *          the new id
   */
  public void setId(int id) {
    this.id = id;
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
   * Gets the navbar item admin.
   *
   * @return the navbar item admin
   */
  public NavbarItemAdmin getNavbarItemAdmin() {
    return navbarItemAdmin;
  }

  /**
   * Sets the navbar item admin.
   *
   * @param navbarItemAdmin
   *          the new navbar item admin
   */
  public void setNavbarItemAdmin(NavbarItemAdmin navbarItemAdmin) {
    this.navbarItemAdmin = navbarItemAdmin;
  }

}
