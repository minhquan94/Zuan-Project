/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.common;

/**
 * The Enum WidthTable.
 */
public enum WidthTable {

  /** The small. */
  SMALL(1 << 4),

  /** The medium. */
  MEDIUM(1 << 5),

  /** The large. */
  LARGE(1 << 6);

  /** The size. */
  private final int size;

  /**
   * Instantiates a new width table.
   *
   * @param size
   *          the size
   */
  private WidthTable(int size) {
    this.size = size;
  }

  /**
   * From value.
   *
   * @param size
   *          the size
   * @return the width table
   */
  public static WidthTable fromValue(final int size) {
    for (final WidthTable value : WidthTable.values()) {
      if (value.size == size) {
        return value;
      }
    }
    return null;
  }
}
