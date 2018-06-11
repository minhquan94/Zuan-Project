/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecConstructionException;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.Codecs;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.Bound;
import com.zuan.parser.codehaus.preon.annotation.BoundList;
import com.zuan.parser.codehaus.preon.annotation.BoundObject;
import com.zuan.parser.codehaus.preon.annotation.Choices;
import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.InvalidExpressionException;
import com.zuan.parser.codehaus.preon.util.AnnotationWrapper;

/**
 * A {@link CodecFactory} that will be triggered by {@link Bound} or
 * {@link BoundList} annotations on arrays. Note that {@link Codec}s created by
 * this class will always read all data eagerly. (Unlike {@link List}s, you
 * cannot implement arrays yourself.)
 */
public class ArrayCodecFactory implements CodecFactory {

  /**
   * The {@link CodecFactory} that will be used for constructing the
   * {@link Codecs} to construct elements in the List.
   */
  private CodecFactory factory;

  /**
   * Constructs a new instance, accepting the {@link CodecFactory} creating the
   * {@link Codec Codecs} that will reconstruct elements in the List.
   *
   * @param delegate
   *          The {@link CodecFactory} creating the {@link Codec Codecs} that
   *          will reconstruct elements in the List.
   */
  public ArrayCodecFactory(CodecFactory delegate) {
    this.factory = delegate;
  }

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
  @SuppressWarnings("unchecked")
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    BoundList settings = null;
    if (metadata != null && (settings = metadata.getAnnotation(BoundList.class)) != null
        && type.isArray() && settings.size() != null && settings.size().length() != 0) {
      Expression<Integer, Resolver> expr = getSizeExpression(settings, context);
      Codec<Object> elementCodec = null;
      if (type.getComponentType().isPrimitive()) {
        elementCodec = (Codec<Object>) factory.create(null, type.getComponentType(), context);
      } else {
        BoundObject objectSettings = getObjectSettings(settings);
        elementCodec = (Codec<Object>) factory.create(new AnnotationWrapper(objectSettings),
            type.getComponentType(), context);
      }
      return (Codec<T>) new ArrayCodec(expr, elementCodec, type);
    } else {
      return null;
    }

  }

  /**
   * Gets the object settings.
   *
   * @param settings
   *          the settings
   * @return the object settings
   */
  private BoundObject getObjectSettings(final BoundList settings) {
    return new BoundObject() {

      @Override
      public Class< ? > type() {
        return settings.type();
      }

      @Override
      public Class< ? >[] types() {
        return settings.types();
      }

      @Override
      public Class< ? extends Annotation> annotationType() {
        return BoundObject.class;
      }

      @Override
      public boolean ommitTypePrefix() {
        return settings.ommitTypePrefix();
      }

      @Override
      public Choices selectFrom() {
        return settings.selectFrom();
      }

    };
  }

  /**
   * Returns the {@link Expression} that will be evaluated to the array's size.
   *
   * @param listSettings
   *          The annotation, holding the expression.
   * @param context
   *          the context
   * @return An {@link Expression} instance, representing the expression.
   * @throws CodecConstructionException
   *           the codec construction exception
   */
  private Expression<Integer, Resolver> getSizeExpression(BoundList listSettings,
      ResolverContext context) throws CodecConstructionException {
    try {
      return Expressions.createInteger(context, listSettings.size());
    } catch (InvalidExpressionException ece) {
      throw new CodecConstructionException(ece);
    } catch (BindingException be) {
      throw new CodecConstructionException(be);
    }
  }
}
