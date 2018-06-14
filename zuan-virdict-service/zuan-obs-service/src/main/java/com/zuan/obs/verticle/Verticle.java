/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.obs.verticle;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * The Interface Verticle.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Verticle {

  /**
   * Value.
   *
   * @return the string
   */
  String value() default "";
}
