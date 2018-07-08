/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class NavbarItemAdmin.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Entity
@Table(name = "navbar_item_admin")
public class NavbarItemAdmin {

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "item_id")
  private int id;

  /** The title. */
  @Column(name = "title")
  private String title;

  /** The group. */
  @Column(name = "item_group")
  private short group;

  /** The navbar admin details. */
  @OneToMany(mappedBy = "navbarItemAdmin", fetch = FetchType.LAZY)
  private List<NavbarAdminDetail> navbarAdminDetails;

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

  /**
   * Gets the navbar admin details.
   *
   * @return the navbar admin details
   */
  public List<NavbarAdminDetail> getNavbarAdminDetails() {
    return navbarAdminDetails;
  }

  /**
   * Sets the navbar admin details.
   *
   * @param navbarAdminDetails
   *          the new navbar admin details
   */
  public void setNavbarAdminDetails(List<NavbarAdminDetail> navbarAdminDetails) {
    this.navbarAdminDetails = navbarAdminDetails;
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
    result = prime * result + id;
    return result;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final NavbarItemAdmin other = (NavbarItemAdmin) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

}
