/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for marking a byte array to be bound to be bound. Contrary to
 * the general purpose {@link BoundList}, this annotation allows you to specify
 * a matching sequence.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BoundBuffer {

  /**
   * The bytes that need to be matched.
   *
   * @return The bytes that need to be matched.
   */
  public byte[] match() default {};

}
