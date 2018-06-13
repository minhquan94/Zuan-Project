/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.buffer;

/**
 * An enumeration, representing byte order. (Big endian, vs. little endian.)
 *
 * @since Jun 11, 2018
 */
public enum ByteOrder {

  /** The Little endian. */
  LITTLE_ENDIAN("little endian"),

  /** The Big endian. */
  BIG_ENDIAN("big endian");

  /** The Constant Native. */
  public static final ByteOrder Native =
      java.nio.ByteOrder.nativeOrder() == java.nio.ByteOrder.BIG_ENDIAN ? BIG_ENDIAN
          : LITTLE_ENDIAN;

  /** The text. */
  private final String text;

  /**
   * Instantiates a new byte order.
   *
   * @param text
   *          the text
   */
  ByteOrder(String text) {
    this.text = text;
  }

  /**
   * As text.
   *
   * @return the string
   */
  public String asText() {
    return text;
  }

}
