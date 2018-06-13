/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import com.zuan.parser.codehaus.preon.buffer.ByteOrder;

/**
 * The annotation allowing you to define a number of choices, based a prefix of
 * a certain {@link #prefixSize() size}.
 */
public @interface Choices {

  /**
   * The number of bits to be read for determining the prefix.
   *
   * @return The number of bits to be read for determining the prefix.
   */
  int prefixSize() default 0;

  /**
   * The byte order to take into account when returning a representation of the
   * first {@link #prefixSize() size} bits read as a prefix.
   *
   * @return The byte order to take into account when returning a representation
   *         of the first {@link #prefixSize() size} bits read as a prefix.
   */
  ByteOrder byteOrder() default ByteOrder.BIG_ENDIAN;

  /**
   * The choices to select from.
   *
   * @return The choices to select from.
   */
  Choice[] alternatives() default {};

  /**
   * The default type, if any.
   *
   * @return the class
   */
  Class< ? > defaultType() default Void.class;

  /** The annotation holding a single choice. */
  public @interface Choice {

    /**
     * The condition that needs to hold, if an instance of {@link #type() type}
     * is to be decoded. A Limbo expression exposing at least one variable: the
     * variable prefix, representing the value read using the
     * {@link Choices#prefixSize()} and {@link Choices#byteOrder()}
     * specifications.
     *
     * @return The condition that needs to hold, if an instance of
     *         {@link #type() type} is to be decoded.
     */
    String condition();

    /**
     * The type to decode in case the {@link #condition()} holds.
     *
     * @return The type to decode in case the {@link #condition()} holds.
     */
    Class< ? > type();

  }

}
