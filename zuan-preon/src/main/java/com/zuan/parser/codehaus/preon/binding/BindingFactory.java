/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.binding;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;

/**
 * A factory for {@link Binding} instances.
 */
@FunctionalInterface
public interface BindingFactory {

  /**
   * Constructs a new {@link Binding}.
   *
   * @param metadata
   *          The annotations of the field.
   * @param field
   *          The field to bound to.
   * @param codec
   *          The {@link Codec} to be used to decode instances of the type of
   *          object to be injected in the {@link Field field}.
   * @param context
   *          The {@link ReferenceContext context} for creating references.
   * @param containerReference
   *          the container reference
   * @return A new {@link Binding} instance, capable of loading data from a
   *         {@link BitBuffer} into an object's field.
   */
  Binding create(AnnotatedElement metadata, Field field, Codec< ? > codec,
      ResolverContext context, Documenter<ParaContents< ? >> containerReference);

}
