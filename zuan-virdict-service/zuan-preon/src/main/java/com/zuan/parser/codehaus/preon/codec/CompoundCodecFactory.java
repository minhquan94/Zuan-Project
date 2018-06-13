/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.ResolverContext;

/**
 * A {@link CodecFactory} capable of generating {@link Codec Codecs} by
 * delegating to a sorted list of other {@link CodecFactory CodecFactories}.
 */
public class CompoundCodecFactory implements CodecFactory {

  /**
   * The sorted list of {@link CodecFactory CodecFactories} to which this
   * {@link CodecFactory} will delegate.
   */
  private final List<CodecFactory> factories = new ArrayList<>();

  /**
   * Creates the.
   *
   * @param <T>
   *          the generic type
   * @param overrides
   *          the overrides
   * @param type
   *          the type
   * @param context
   *          the context
   * @return the codec
   */
  @Override
  public <T> Codec<T> create(AnnotatedElement overrides, Class<T> type,
      ResolverContext context) {
    Codec<T> result;
    for (CodecFactory delegate : factories) {
      result = delegate.create(overrides, type, context);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  /**
   * Adds the.
   *
   * @param factory
   *          the factory
   */
  public void add(CodecFactory factory) {
    factories.add(factory);
  }

  /**
   * Removes the.
   *
   * @param factory
   *          the factory
   */
  public void remove(CodecFactory factory) {
    factories.remove(factory);
  }

}
