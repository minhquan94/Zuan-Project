/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;

/**
 * A {@link Reference} to support references to objects referenced from outer
 * references. Whenever a new reference is constructed from a
 * {@link OuterReference}, then that new reference needs to preserve some
 * properties:
 * <p/>
 * <ul>
 * <li>{@link #getReferenceContext()} will need to return a reference to the
 * original {@link ResolverContext};</li>
 * <li>{@link #resolve(Resolver)} will first need to Resolve the "outer"
 * resolver, using the {@link #outerName};</li>
 * <li>... and these properties need to be preserved for all next references
 * constructed from this reference.</li>
 * </ul>
 */
public class OuterResolvingReference implements Reference<Resolver> {

  /**
   * The name to use when resolving the <em>actual</em> {@link Resolver} we need
   * to use, from the {@link Resolver} passed to {@link #resolve(Resolver)}.
   */
  private String outerName;

  /**
   * The original {@link ResolverContext} to return by this reference. (To make
   * sure that any other references are always contructed from this context.)
   */
  private final ResolverContext originalContext;

  /** The {@link Reference} wrapped. */
  private Reference<Resolver> wrapped;

  /**
   * The {@link ResolverContext} to which this is reference is essentially
   * pointing.
   */
  private final ResolverContext outerContext;

  /**
   * Constructs a new instance.
   *
   * @param outerName
   *          The name to use when resolving the outer
   *          {@link org.codehaus.preon.Resolver} from the
   *          {@link org.codehaus.preon.Resolver} passed to
   *          {@link #resolve(org.codehaus.preon.Resolver)}.
   * @param originalContext
   *          The context to be returned.
   * @param wrapped
   *          the wrapped
   * @param outerContext
   *          the outer context
   */
  public OuterResolvingReference(String outerName, ResolverContext originalContext,
      Reference<Resolver> wrapped, ResolverContext outerContext) {
    this.outerName = outerName;
    this.originalContext = originalContext;
    this.wrapped = wrapped;
    this.outerContext = outerContext;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#getReferenceContext()
   */

  @Override
  public ReferenceContext<Resolver> getReferenceContext() {
    return originalContext;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#getType()
   */

  @Override
  public Class< ? > getType() {
    return wrapped.getType();
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#isAssignableTo(java.lang.Class)
   */

  @Override
  public boolean isAssignableTo(Class< ? > type) {
    return wrapped.isAssignableTo(type);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */

  @Override
  public Object resolve(Resolver resolver) {
    Object outerResolver = resolver.get(outerName);
    if (outerResolver != null && outerResolver instanceof Resolver) {
      Resolver originalResolver = resolver.getOriginalResolver();
      return wrapped
          .resolve(new OriginalReplacingResolver(originalResolver, (Resolver) outerResolver));
    } else {
      throw new BindingException("Failed to resolve " + outerName
          + " to a value of the proper type; got " + (outerResolver == null ? outerResolver
              : outerResolver.getClass().getSimpleName()));
    }
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.ReferenceContext#selectAttribute(java.lang.String)
   */

  @Override
  public Reference<Resolver> selectAttribute(String name) throws BindingException {
    Reference<Resolver> actual = wrapped.selectAttribute(name);
    return new OuterResolvingReference(outerName, originalContext, actual, null);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ReferenceContext#selectItem(java.lang.String)
   */

  @Override
  public Reference<Resolver> selectItem(String index) throws BindingException {
    Reference<Resolver> actual = wrapped.selectItem(index);
    return new OuterResolvingReference(outerName, originalContext, actual, null);
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
    Reference<Resolver> actual = wrapped.selectItem(index);
    return new OuterResolvingReference(outerName, originalContext, actual, null);
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.Document)
   */

  @Override
  public void document(Document target) {
    wrapped.document(target);
  }

  /**
   * The Class OriginalReplacingResolver.
   */
  private static class OriginalReplacingResolver implements Resolver {

    /** The original resolver. */
    private Resolver originalResolver;

    /** The current resolver. */
    private Resolver currentResolver;

    /**
     * Instantiates a new original replacing resolver.
     *
     * @param originalResolver
     *          the original resolver
     * @param currentResolver
     *          the current resolver
     */
    public OriginalReplacingResolver(Resolver originalResolver, Resolver currentResolver) {
      this.currentResolver = currentResolver;
      this.originalResolver = originalResolver;
    }

    /**
     * Gets the.
     *
     * @param name
     *          the name
     * @return the object
     * @throws BindingException
     *           the binding exception
     */
    @Override
    public Object get(String name) throws BindingException {
      return currentResolver.get(name);
    }

    /**
     * Gets the original resolver.
     *
     * @return the original resolver
     */
    @Override
    public Resolver getOriginalResolver() {
      return originalResolver;
    }

  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<Resolver> narrow(Class< ? > type) {
    Reference<Resolver> narrowed = wrapped.narrow(type);
    if (narrowed == null) {
      return this;
    } else {
      return new OuterResolvingReference(outerName, originalContext, narrowed, null);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isBasedOn(com.virdict.
   * tool.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isBasedOn(ReferenceContext<Resolver> context) {
    return this.wrapped.isBasedOn(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Reference<Resolver> rescope(ReferenceContext<Resolver> context) {
    if (outerContext.equals(context)) {
      return wrapped;
    } else {
      return wrapped.rescope(context);
    }
  }

}
