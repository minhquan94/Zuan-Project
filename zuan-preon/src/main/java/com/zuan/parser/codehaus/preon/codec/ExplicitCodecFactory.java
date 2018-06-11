/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.reflect.AnnotatedElement;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecConstructionException;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.BoundExplicitly;

/**
 * A {@link CodecFactory} allowing you to explicitly set the {@link Codec} to
 * use. (Triggered by the {@link BoundExplicitly} annotation.
 */
public class ExplicitCodecFactory implements CodecFactory {

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.CodecFactory#create(java.lang.reflect.AnnotatedElement,
   * java.lang.Class, org.codehaus.preon.ResolverContext)
   */

  /**
   * Creates the.
   *
   * @param <T>
   *          the generic type
   * @param metadata
   *          the metadata
   * @param type
   *          the type
   * @param context
   *          the context
   * @return the codec
   */
  @Override
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    if (metadata != null && metadata.isAnnotationPresent(BoundExplicitly.class)) {
      BoundExplicitly settings = metadata.getAnnotation(BoundExplicitly.class);
      try {
        CodecFactory factory = settings.factory().newInstance();
        return factory.create(metadata, type, null);
      } catch (InstantiationException e) {
        throw new CodecConstructionException(
            "Failed to construct Codec using " + settings.factory().getName());
      } catch (IllegalAccessException e) {
        throw new CodecConstructionException(
            "No permission to construct an instance of " + settings.factory().getName());
      }
    } else {
      return null;
    }
  }

}
