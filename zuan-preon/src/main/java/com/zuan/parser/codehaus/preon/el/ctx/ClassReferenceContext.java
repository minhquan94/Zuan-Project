/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ctx;

import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * The Class ClassReferenceContext.
 *
 * @author zuan_
 * @param <T>
 *          the generic type
 */
public class ClassReferenceContext<T> implements Reference<T> {

  /** The type. */
  private Class<T> type;

  /**
   * Instantiates a new class reference context.
   *
   * @param type
   *          the type
   */
  public ClassReferenceContext(Class<T> type) {
    this.type = type;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getReferenceContext()
   */
  @Override
  public ReferenceContext<T> getReferenceContext() {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
   * Class)
   */
  @Override
  public boolean isAssignableTo(Class< ? > type) {
    return this.type.isAssignableFrom(type);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */
  @Override
  public Object resolve(T context) {
    return context;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
   * java.lang.String)
   */
  @Override
  public Reference<T> selectAttribute(String name) throws BindingException {
    return new PropertyReference<>(this, type, name, this);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<T> selectItem(String index) throws BindingException {
    throw new BindingException("Items not supported.");
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
   * zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<T> selectItem(Expression<Integer, T> index) throws BindingException {
    throw new BindingException("Items not supported.");
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    target.text("a " + type.getSimpleName());
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
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<T> narrow(Class< ? > type) {
    if (type.isAssignableFrom(type)) {
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
  public boolean isBasedOn(ReferenceContext<T> other) {
    return this.equals(other);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Reference<T> rescope(ReferenceContext<T> context) {
    assert context == this;
    return this;
  }

}
