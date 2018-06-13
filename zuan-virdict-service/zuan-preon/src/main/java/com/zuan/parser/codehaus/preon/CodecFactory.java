/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import java.lang.reflect.AnnotatedElement;

/**
 * A factory for {@link Codec Codecs}. A {@link CodecFactory} is not tightly
 * coupled to a certain type of {@link Codec}.
 */
@FunctionalInterface
public interface CodecFactory {

  /**
   * Constructs a new {@link Codec}. The <code>metadata</code> argument is used
   * for passing data on the expectations of the clients of the factory.
   * <p>
   * The client might choose not to pass any metadata at all, basically leaving
   * it up to the factory to make its own decisions based on type information
   * only. The client might also pass an empty {@link AnnotatedElement}.
   * </p>
   * <p>
   * Note the subtle difference here. Passing <code>null</code> means: if you
   * can create a {@link Codec} for this type, then please do so. Passing an
   * empty {@link AnnotatedElement} means: please give me a {@link Codec} for
   * the given type, but only if you find an annotation that explicitly tells
   * you to do so.
   * </p>
   *
   * @param <T>
   *          The type of the objects to be returned by the {@link Codec}.
   * @param metadata
   *          A bucket of metadata, used by the the factory to determine
   *          <em>if</em> it should be creating a {@link Codec} for a certain
   *          type, but also <em>how</em> the {@link Codec} should be created.
   * @param type
   *          The type of the objects to be returned by the {@link Codec}. (Not
   *          null).
   * @param context
   *          The context for creating references.
   * @return A new {@link Codec} for the given type.
   */
  <T> Codec<T> create(AnnotatedElement metadata, Class<T> type, ResolverContext context);

}
