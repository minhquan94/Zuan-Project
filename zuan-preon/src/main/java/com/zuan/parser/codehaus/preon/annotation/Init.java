/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zuan.parser.codehaus.preon.codec.InitCodecDecorator;

/**
 * An annotation for marking an operation to be invoked when an object has been
 * decoded entirely. (Allows for post-processing.) Only when
 * {@link InitCodecDecorator} is used.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Init {

}
