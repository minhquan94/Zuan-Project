/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;

/**
 * The annotation used to indicate the discriminator used to recognize an
 * instance of this class.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TypePrefix {

  /**
   * The type of endianness: either {@link ByteOrder#LITTLE_ENDIAN} or
   * {@link ByteOrder#BIG_ENDIAN}.
   *
   * @return The type of endianness. Defaults to {@link ByteOrder#LITTLE_ENDIAN}.
   */
  ByteOrder byteOrder() default ByteOrder.LITTLE_ENDIAN;

  /**
   * The number of bits used to represent the numeric value. Defaults to 0,
   * allowing the {@link Codec} to make its own decision on the number of bits
   * to be used; however, they are expected to respect the following defaults:
   * <p/>
   * <table>
   * <tr>
   * <th>Type</th>
   * <th>Number of bits</th>
   * </tr>
   * <tr>
   * <td>Long, long</td>
   * <td>64</td>
   * </tr>
   * <tr>
   * <td>Integer, int</td>
   * <td>32</td>
   * </tr>
   * <tr>
   * <td>Short, short</td>
   * <td>16</td>
   * </tr>
   * <tr>
   * <td>Byte, byte</td>
   * <td>8</td>
   * </tr>
   * </table>
   * <p/>
   * <p>
   * The type of this annotation might be turned into a String in the future, to
   * allow the size to be based on an expression instead of a fixed value.
   * </p>
   *
   * @return The number of bits used to represent the numeric value.
   */
  int size() default 0;

  /**
   * The value that will be used to match this particular record.
   *
   * @return The value, as a Limbo expression. (This is done to allow future
   *         versions of Limbo to be extended with a convenience notation for
   *         binary notation.)
   */
  String value() default "0";

}
