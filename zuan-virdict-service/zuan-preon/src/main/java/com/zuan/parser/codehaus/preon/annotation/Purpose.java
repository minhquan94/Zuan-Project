/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A (probably) temporary solution for adding documentation to the fields to be
 * used while generating documentation on the file format.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE })
public @interface Purpose {

  /**
   * Value.
   *
   * @return the string
   */
  String value();

}
