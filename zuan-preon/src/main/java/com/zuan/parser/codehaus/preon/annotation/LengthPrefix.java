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
public @interface LengthPrefix {

  /**
   * The type of endianness: either {@link ByteOrder#LITTLE_ENDIAN} or
   * {@link ByteOrder#BIG_ENDIAN}.
   *
   * @return The type of endianness. Defaults to {@link ByteOrder#LITTLE_ENDIAN}.
   */
  ByteOrder endian() default ByteOrder.LITTLE_ENDIAN;

  /**
   * The number of bits used to represent the numeric value. Defaults to 0,
   * allowing the {@link Codec} to make its own decision on the number of bits
   * to be used; however, they are expected to respect the following defaults:
   */
  String size() default "";

}
