/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The annotation to be used if you want to make sure that only a slice of the
 * underlying BitBuffer is passed on while decoding a (compound) value. Useful
 * as an alternative to {@link LengthPrefix}. With {@link LengthPrefix}, the
 * size of the slice needs to be encoded in the bit stream itself. With
 * {@link Slice} you can just pass in a fixed value, or a reference to a
 * variable read somewhere else.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Slice {

  /**
   * The size of the slice <em>in number of bits</em>. (A Limbo expression.)
   *
   * @return The size of the slice, in number of bits.
   */
  String size() default "0";

}
