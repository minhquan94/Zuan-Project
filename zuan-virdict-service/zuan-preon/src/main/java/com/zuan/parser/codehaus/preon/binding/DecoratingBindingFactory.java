/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.binding;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.ResolverContext;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;

/**
 * A factory for creating DecoratingBinding objects.
 *
 * @author zuan_
 */
public class DecoratingBindingFactory implements BindingFactory {

  /** The binding factory. */
  private final BindingFactory bindingFactory;

  /** The binding decorators. */
  private final BindingDecorator[] bindingDecorators;

  /**
   * Instantiates a new decorating binding factory.
   *
   * @param bindingFactory
   *          the binding factory
   * @param bindingDecorators
   *          the binding decorators
   */
  public DecoratingBindingFactory(BindingFactory bindingFactory,
      BindingDecorator[] bindingDecorators) {
    this.bindingDecorators = bindingDecorators;
    this.bindingFactory = bindingFactory;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.binding.BindingFactory#create(java.
   * lang.reflect.AnnotatedElement, java.lang.reflect.Field,
   * com.zuan.parser.codehaus.preon.Codec,
   * com.zuan.parser.codehaus.preon.ResolverContext,
   * nl.flotsam.pecia.Documenter)
   */
  @Override
  public Binding create(AnnotatedElement metadata, Field field, Codec< ? > codec,
      ResolverContext context, Documenter<ParaContents< ? >> containerReference) {
    Binding binding =
        bindingFactory.create(metadata, field, codec, context, containerReference);
    if (binding != null) {
      for (BindingDecorator decorator : bindingDecorators) {
        binding = decorator.decorate(binding);
      }
    }
    return binding;
  }
}
