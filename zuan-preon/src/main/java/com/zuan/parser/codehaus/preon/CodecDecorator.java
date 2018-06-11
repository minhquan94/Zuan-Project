/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import java.lang.reflect.AnnotatedElement;

/**
 * A factory for {@link Codec} decorators.
 */
@FunctionalInterface
public interface CodecDecorator {

  /**
   * Attempts to wrap the {@link Codec} passed in with new decorator. Note that
   * if the factory fails to create the decorator, it is expected to return the
   * original {@link Codec}.
   *
   * @param <T>
   *          The type of {@link Codec} to be decorated.
   * @param codec
   *          The object that needs to be decorated.
   * @param metadata
   *          Metadata for the type.
   * @param type
   *          The type of {@link Codec} to be be decorated.
   * @param context
   *          the context
   * @return A decorated {@link Codec} or the original {@link Codec}.
   */
  <T> Codec<T> decorate(Codec<T> codec, AnnotatedElement metadata, Class<T> type,
      ResolverContext context);

}
