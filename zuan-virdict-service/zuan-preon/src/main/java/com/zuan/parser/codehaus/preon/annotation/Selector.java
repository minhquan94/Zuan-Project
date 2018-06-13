/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.zuan.parser.codehaus.preon.CodecSelector;

/**
 * The annotation used to set a specific Selector when having to choose between
 * multiple {@link Codec Codecs}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Selector {

  /**
   * The type of {@link CodecSelector} to be used when instantiating a policy
   * for deciding between different types of {@link Codec Codecs}.
   *
   * @return The type of {@link CodecSelector} to be used when instantiating a
   *         policy for deciding between different types of {@link Codec
   *         Codecs}.
   */
  Class< ? extends CodecSelector> value();

}
