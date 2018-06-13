/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDecorator;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.Slice;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;

/**
 * A {@link CodecFactory} creating {@link Codec Codecs} slicing the
 * {@link BitBuffer} to limit the visibility of the remainder of the buffer (and
 * easily skip forward, if the data itself is not required). Triggered by the
 * {@link Slice} annotation.
 */
public class SlicingCodecDecorator implements CodecDecorator {

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDecorator#decorate(com.virdict.
   * tool.parser.codehaus.preon.Codec, java.lang.reflect.AnnotatedElement,
   * java.lang.Class, com.zuan.parser.codehaus.preon.ResolverContext)
   */
  @Override
  public <T> Codec<T> decorate(Codec<T> decorated, AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    Slice slice = getAnnotation(metadata, type, Slice.class);
    if (slice != null) {
      return createCodecFromSlice(decorated, slice, context);
    }
    return decorated;
  }

  /**
   * Gets the annotation.
   *
   * @param <T>
   *          the generic type
   * @param <V>
   *          the value type
   * @param metadata
   *          the metadata
   * @param type
   *          the type
   * @param annotation
   *          the annotation
   * @return the annotation
   */
  private <T, V extends Annotation> V getAnnotation(AnnotatedElement metadata, Class<T> type,
      Class<V> annotation) {
    if (type.isAnnotationPresent(annotation)) {
      return type.getAnnotation(annotation);
    }
    if (metadata != null && metadata.isAnnotationPresent(annotation)) {
      return metadata.getAnnotation(annotation);
    }
    return null;
  }

  /**
   * Creates the codec from slice.
   *
   * @param <T>
   *          the generic type
   * @param decorated
   *          the decorated
   * @param slice
   *          the slice
   * @param context
   *          the context
   * @return the codec
   */
  private <T> Codec<T> createCodecFromSlice(Codec<T> decorated, Slice slice,
      ResolverContext context) {
    Expression<Integer, Resolver> sizeExpr;
    sizeExpr = Expressions.createInteger(context, slice.size());
    return new SlicingCodec<>(decorated, sizeExpr);
  }

}
