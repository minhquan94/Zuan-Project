/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecFactory;

/**
 * An annotation marking a certain field to use a *specific* type of Codec to
 * bind it to the BitBuffer. This annotation takes highest precedence.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BoundExplicitly {

  /**
   * The {@link CodecFactory} to use to construct the {@link Codec}.
   *
   * @return The {@link CodecFactory} to use to construct the {@link Codec}.
   */
  Class< ? extends CodecFactory> factory();

}
