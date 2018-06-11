/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import com.zuan.parser.codehaus.preon.Resolver;

/**
 * The Class ContextReplacingReference.
 *
 * @author zuan_
 */
public class ContextReplacingReference implements Reference<Resolver> {

  /** The alternative context. */
  private ReferenceContext<Resolver> alternativeContext;

  /** The reference. */
  private Reference<Resolver> reference;

  /**
   * Instantiates a new context replacing reference.
   *
   * @param alternativeContext
   *          the alternative context
   * @param reference
   *          the reference
   */
  public ContextReplacingReference(ReferenceContext<Resolver> alternativeContext,
      Reference<Resolver> reference) {
    this.alternativeContext = alternativeContext;
    this.reference = reference;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getReferenceContext()
   */
  @Override
  public ReferenceContext<Resolver> getReferenceContext() {
    return alternativeContext;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getType()
   */
  @Override
  public Class< ? > getType() {
    return reference.getType();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
   * Class)
   */
  @Override
  public boolean isAssignableTo(Class< ? > type) {
    return reference.isAssignableTo(type);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */
  @Override
  public Object resolve(Resolver context) {
    return reference.resolve(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
   * java.lang.String)
   */
  @Override
  public Reference<Resolver> selectAttribute(String name) throws BindingException {
    return new ContextReplacingReference(alternativeContext, reference.selectAttribute(name));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<Resolver> selectItem(String index) throws BindingException {
    return new ContextReplacingReference(alternativeContext, reference.selectItem(index));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
   * zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<Resolver> selectItem(Expression<Integer, Resolver> index)
      throws BindingException {
    return new ContextReplacingReference(alternativeContext, reference.selectItem(index));
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

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<Resolver> narrow(Class< ? > type) {
    Reference<Resolver> narrowed = this.reference.narrow(type);
    if (narrowed == null) {
      return null;
    } else {
      return new ContextReplacingReference(alternativeContext, narrowed);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isBasedOn(com.virdict.
   * tool.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isBasedOn(ReferenceContext<Resolver> other) {
    return reference.isBasedOn(other);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Reference<Resolver> rescope(ReferenceContext<Resolver> context) {
    return reference.rescope(context);
  }

}
