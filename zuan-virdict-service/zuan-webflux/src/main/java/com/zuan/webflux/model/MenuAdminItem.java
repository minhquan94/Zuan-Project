/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.model;

/**
 * The Enum MenuAdminItem.
 */
public enum MenuAdminItem {

  /** The top. */
  TOP((short) 1, "Menu Top"),

  /** The left. */
  LEFT((short) 2, "Menu LEFT");

  /** The type. */
  private short type;

  /** The description. */
  private String description;

  /**
   * Instantiates a new menu admin item.
   *
   * @param type
   *          the type
   * @param description
   *          the description
   */
  private MenuAdminItem(short type, String description) {
    this.type = type;
    this.description = description;
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
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }
}
