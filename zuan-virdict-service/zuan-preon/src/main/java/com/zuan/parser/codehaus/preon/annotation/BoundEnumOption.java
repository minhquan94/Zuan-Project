/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zuan.parser.codehaus.preon.buffer.BitBuffer;

/**
 * An annotation used to annotate enumeration values, in order to make sure they
 * can be mapped to long values read from the {@link BitBuffer}.
 *
 * @see org.codehaus.preon.codec.EnumCodec.EnumCodecFactory
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BoundEnumOption {

  /**
   * The long value read from the {@link BitBuffer}.
   *
   * @return the long
   */
  long value();

}
