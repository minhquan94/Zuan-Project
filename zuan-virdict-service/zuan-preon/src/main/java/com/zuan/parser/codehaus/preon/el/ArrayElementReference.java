/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import java.lang.reflect.Array;

import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.util.TextUtils;

/**
 * A reference to an array element.
 */
public class ArrayElementReference implements Reference<Resolver> {

  /** The type of element. */
  private Class< ? > elementType;

  /** The expression indicating the specific element of the array. */
  private Expression<Integer, Resolver> index;

  /** A reference to the object representing the actual array. */
  private Reference<Resolver> arrayReference;

  /** The {@link ReferenceContext}. */
  private ReferenceContext<Resolver> context;

  /**
   * Constructs a new {@link ArrayElementReference}.
   *
   * @param arrayReference
   *          A reference to an array.
   * @param elementType
   *          The type of element.
   * @param index
   *          The position in the array. (A Limbo expression.)
   * @param context
   *          The root context of the reference.
   */
  public ArrayElementReference(Reference<Resolver> arrayReference, Class< ? > elementType,
      Expression<Integer, Resolver> index, ReferenceContext<Resolver> context) {
    this.arrayReference = arrayReference;
    this.elementType = elementType;
    this.index = index;
    this.context = context;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */

  @Override
  public Object resolve(Resolver context) {
    Object array = arrayReference.resolve(context);
    int i = index.eval(context.getOriginalResolver());
    return Array.get(array, i);
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.ReferenceContext#selectAttribute(java.lang.String)
   */

  @Override
  public Reference<Resolver> selectAttribute(String name) {
    return new PropertyReference(this, elementType, name, context);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ReferenceContext#selectItem(java.lang.String)
   */

  @Override
  public Reference<Resolver> selectItem(String index) {
    Expression<Integer, Resolver> expr;
    expr = Expressions.createInteger(context, index);
    return selectItem(expr);
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.ReferenceContext#selectItem(org.codehaus.preon.el.
   * Expression)
   */

  @Override
  public Reference<Resolver> selectItem(Expression<Integer, Resolver> index) {
    return new ArrayElementReference(this, elementType.getComponentType(), index, context);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((arrayReference == null) ? 0 : arrayReference.hashCode());
    result = prime * result + ((index == null) ? 0 : index.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ArrayElementReference other = (ArrayElementReference) obj;
    if (arrayReference == null) {
      if (other.arrayReference != null) {
        return false;
      }
    } else if (!arrayReference.equals(other.arrayReference)) {
      return false;
    }
    if (index == null) {
      if (other.index != null) {
        return false;
      }
    } else if (!index.equals(other.index)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.Document)
   */

  @Override
  public void document(Document target) {
    if (!index.isParameterized()) {
      target.text("the ");
      target.text(TextUtils.getPositionAsText(index.eval(null)));
      target.text(" element of ");
      arrayReference.document(target);
    } else {
      target.text("the nth element of ");
      arrayReference.document(target);
      target.text(" (with n being ");
      index.document(target);
      target.text(")");
    }
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#getReferenceContext()
   */

  @Override
  public ReferenceContext<Resolver> getReferenceContext() {
    return context;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#isAssignableTo(java.lang.Class)
   */

  @Override
  public boolean isAssignableTo(Class< ? > type) {
    return elementType.isAssignableFrom(type);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Reference#getType()
   */

  @Override
  public Class< ? > getType() {
    return elementType;
  }

  @Override
  public Reference<Resolver> narrow(Class< ? > type) {
    if (type == elementType) {
      return this;
    } else {
      return null;
    }
  }

  @Override
  public boolean isBasedOn(ReferenceContext<Resolver> resolverReferenceContext) {
    return this.arrayReference.isBasedOn(resolverReferenceContext)
        && this.index.isConstantFor(resolverReferenceContext);
  }

  @Override
  public Reference<Resolver> rescope(ReferenceContext<Resolver> newScope) {
    return new ArrayElementReference(arrayReference.rescope(newScope), elementType,
        index.rescope(newScope), newScope);
  }

}
