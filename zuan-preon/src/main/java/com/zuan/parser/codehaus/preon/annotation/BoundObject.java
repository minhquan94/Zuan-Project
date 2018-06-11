/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for marking fields to be bound to the BitBuffer. Used when the
 * default decoding approach (which is to examine the type of field, and guess a
 * decoder from that) doesn't fit your needs.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BoundObject {

  /**
   * The type of object to be decoded. (Use this when you want to override the
   * type detected at runtime.)
   *
   * @return The type of object to be decoded. (Use this when you want to
   *         override the type detected at runtime.)
   */
  Class< ? > type() default Void.class;

  /**
   * The types of objects to be decoded. Use this if you want the framework to
   * select a certain class based on a couple of leading bits prefixing the
   * actual data. Note that it expects every type in the array to have the
   * {@link TypePrefix} annotation.
   *
   * @return The types of object to be decoded.
   */
  Class< ? >[] types() default {};

  /**
   * Indicates that the type prefix must be ignored. Note that this is fairly
   * experimental. Use this with cause.
   *
   * @return true, if successful
   */
  boolean ommitTypePrefix() default false;

  /**
   * The choices to select from, based on a prefix of a certain size.
   *
   * @return The choices to select from, based on a prefix of a certain size.
   */
  Choices selectFrom() default @Choices(alternatives = {});

}
