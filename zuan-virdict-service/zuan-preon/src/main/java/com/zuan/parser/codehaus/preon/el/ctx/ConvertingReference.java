/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ctx;

import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;
import com.zuan.parser.codehaus.preon.el.util.Converter;
import com.zuan.parser.codehaus.preon.el.util.Converters;

/**
 * The Class ConvertingReference.
 *
 * @author zuan_
 * @param <T>
 *          the generic type
 * @param <E>
 *          the element type
 */
public class ConvertingReference<T, E> implements Reference<E> {

  /** The reference. */
  private Reference<E> reference;

  /** The type. */
  private Class<T> type;

  /** The converter. */
  private Converter<Object, Class<T>> converter;

  /**
   * Instantiates a new converting reference.
   *
   * @param type
   *          the type
   * @param reference
   *          the reference
   */
  @SuppressWarnings("unchecked")
  public ConvertingReference(Class<T> type, Reference<E> reference) {
    this.type = type;
    this.reference = reference;
    this.converter = (Converter<Object, Class<T>>) Converters.get(reference.getType(), type);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getReferenceContext()
   */
  @Override
  public ReferenceContext<E> getReferenceContext() {
    return reference.getReferenceContext();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getType()
   */
  @Override
  public Class< ? > getType() {
    return type;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
   * Class)
   */
  @Override
  public boolean isAssignableTo(Class< ? > type) {
    return type.isAssignableFrom(this.type);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */
  @Override
  public Object resolve(E context) {
    return converter.convert(reference.resolve(context));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
   * java.lang.String)
   */
  @Override
  public Reference<E> selectAttribute(String name) throws BindingException {
    return reference.selectAttribute(name);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<E> selectItem(String index) throws BindingException {
    return reference.selectItem(index);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
   * zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<E> selectItem(Expression<Integer, E> index) throws BindingException {
    return reference.selectItem(index);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    reference.document(target);
  }

  /**
   * Creates the.
   *
   * @param <T>
   *          the generic type
   * @param <E>
   *          the element type
   * @param type
   *          the type
   * @param reference
   *          the reference
   * @return the converting reference
   */
  public static <T, E> ConvertingReference<T, E> create(Class<T> type,
      Reference<E> reference) {
    return new ConvertingReference<>(type, reference);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<E> narrow(Class< ? > type) throws BindingException {
    if (this.type.isAssignableFrom(type)) {
      return this;
    } else {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isBasedOn(com.virdict.
   * tool.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isBasedOn(ReferenceContext<E> context) {
    return reference.isBasedOn(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Reference<E> rescope(ReferenceContext<E> context) {
    return new ConvertingReference<>(type, reference.rescope(context));
  }

}
