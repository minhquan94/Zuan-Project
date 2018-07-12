/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The Class AdminMenuItem.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Entity(name = "AdminMenuItem")
public class AdminMenuItemEntity implements BaseIdEntity {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 2583555413933589489L;

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "AdminMenuItemId")
  private Long id;

  /** The name. */
  @Column(name = "Name", nullable = false)
  private String name;

  /** The link. */
  @Column(name = "Link")
  private String link;

  /** The type. */
  @Column(name = "Type", nullable = false)
  private short type;

  /** The created user. */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ParentId")
  private AdminMenuItemEntity parent;

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Override
  public Long getId() {
    return id;
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

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AdminMenuItemEntity [id=" + id + ", name=" + name + ", link=" + link + ", type="
        + type + ", parent=" + parent + "]";
  }

}
