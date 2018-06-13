/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;

/**
 * A {@link Reference} to the outer context. This {@link Reference} will create
 * new references ({@link #selectAttribute(String)},
 * {@link #selectItem(Expression)}, etc.) that will be based on the
 * {@link #outerContext} passed in. In order to make sure the
 * {@link #originalContext} will be preserved by wrapping these new
 * {@link Reference}s in objects that will handle all incoming calls
 * appropriately.
 */
public class OuterReference implements Reference<Resolver> {

  /**
   * The default name of referring to the outer context.
   */
  public final static String DEFAULT_OUTER_NAME = "outer";

  /**
   * The "outer" {@link ResolverContext}.
   */
  private ResolverContext outerContext;

  /**
   * The "original" context from which this object was created. (Note that this
   * not necessarily needs to be the sub context of the outer context. It could
   * be something way more deep down in the chain.
   */
  private ResolverContext originalContext;

  /**
   * Constructs a new instance.
   *
   * @param outerContext
   *          The "outer" context.
   * @param originalContext
   *          The "original" context.
   * @see OuterReference#outerContext
   * @see OuterReference#originalContext
   */
  public OuterReference(ResolverContext outerContext, ResolverContext originalContext) {
    this.outerContext = outerContext;
    this.originalContext = originalContext;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#getType()
   */

  @Override
  public Class< ? > getType() {
    return Resolver.class;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#isAssignableTo(java.lang.Class)
   */

  @Override
  public boolean isAssignableTo(Class< ? > type) {
    return type.isAssignableFrom(getType());
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */

  @Override
  public Object resolve(Resolver resolver) {
    throw new IllegalStateException("Never expected to be called.");
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.ReferenceContext#selectAttribute(java.lang.String)
   */

  @Override
  public Reference<Resolver> selectAttribute(String name) throws BindingException {
    Reference<Resolver> actual = outerContext.selectAttribute(name);
    return new OuterResolvingReference(DEFAULT_OUTER_NAME, originalContext, actual,
        outerContext);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ReferenceContext#selectItem(java.lang.String)
   */

  @Override
  public Reference<Resolver> selectItem(String index) throws BindingException {
    Reference<Resolver> actual = outerContext.selectItem(index);
    return new OuterResolvingReference(DEFAULT_OUTER_NAME, originalContext, actual, null);
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.ReferenceContext#selectItem(org.codehaus.preon.el.
   * Expression)
   */

  @Override
  public Reference<Resolver> selectItem(Expression<Integer, Resolver> index)
      throws BindingException {
    Reference<Resolver> actual = outerContext.selectItem(index);
    return new OuterResolvingReference(DEFAULT_OUTER_NAME, originalContext, actual, null);
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.Document)
   */

  @Override
  public void document(Document target) {
    target.text("the ");
    outerContext.document(target);
    target.text(" containing the ");
    originalContext.document(target);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#getReferenceContext()
   */

  @Override
  public ReferenceContext<Resolver> getReferenceContext() {
    return outerContext;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<Resolver> narrow(Class< ? > type) {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isBasedOn(com.virdict.
   * tool.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isBasedOn(ReferenceContext<Resolver> context) {
    return outerContext.equals(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Reference<Resolver> rescope(ReferenceContext<Resolver> resolverReferenceContext) {
    // Since instance of this class are taken out of the reference path, this
    // operation is never expected to be
    // invoked on an instance of this class.
    throw new UnsupportedOperationException();
  }

}
