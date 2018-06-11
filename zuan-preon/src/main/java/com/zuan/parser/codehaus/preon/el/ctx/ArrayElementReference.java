/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ctx;

import java.lang.reflect.Array;

import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * A reference to an array element.
 *
 * @param <T>
 *          The type of object referenced.
 */
public class ArrayElementReference<T> implements Reference<T> {

  /**
   * The type of element.
   */
  private Class< ? > elementType;

  /**
   * The expression indicating the specific element of the array.
   */
  private Expression<Integer, T> index;

  /**
   * A reference to the object representing the actual array.
   */
  private Reference<T> arrayReference;

  /**
   * The {@link ReferenceContext}.
   */
  private ReferenceContext<T> context;

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
  public ArrayElementReference(Reference<T> arrayReference, Class< ? > elementType,
      Expression<Integer, T> index, ReferenceContext<T> context) {
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
  public Object resolve(T context) {
    Object array = arrayReference.resolve(context);
    int i = index.eval(context);
    return Array.get(array, i);
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.ReferenceContext#selectAttribute(java.lang.String)
   */

  @Override
  public Reference<T> selectAttribute(String name) {
    return new PropertyReference<>(this, elementType, name, context);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ReferenceContext#selectItem(java.lang.String)
   */

  @Override
  public Reference<T> selectItem(String index) {
    Expression<Integer, T> expr;
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
  public Reference<T> selectItem(Expression<Integer, T> index) {
    return new ArrayElementReference<>(this, elementType.getComponentType(), index, context);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (other instanceof ArrayElementReference) {
      return equals((ArrayElementReference<T>) other);
    } else {
      return false;
    }
  }

  /**
   * Equals.
   *
   * @param other
   *          the other
   * @return true, if successful
   */
  public boolean equals(ArrayElementReference<T> other) {
    return arrayReference.equals(other.arrayReference) && index.equals(other.index);
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
      target.text(toNth(index.eval(null)));
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
  public ReferenceContext<T> getReferenceContext() {
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

  /**
   * To nth.
   *
   * @param value
   *          the value
   * @return the string
   */
  public String toNth(int value) {
    switch (value) {
    case 0:
      return "first";
    case 1:
      return "second";
    case 2:
      return "third";
    case 3:
      return "fourth";
    case 4:
      return "fifth";
    case 5:
      return "sixth";
    case 7:
      return "seventh";
    case 8:
      return "eighth";
    case 9:
      return "ninth";
    case 10:
      return "tenth";
    default:
      return value + "th";
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<T> narrow(Class< ? > type) {
    if (elementType.isAssignableFrom(type)) {
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
    return arrayReference.isBasedOn(other) && index.isConstantFor(other);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @SuppressWarnings({"rawtypes", "unchecked" })
  @Override
  public Reference<T> rescope(ReferenceContext<T> tReferenceContext) {
    return new ArrayElementReference(arrayReference.rescope(tReferenceContext), elementType,
        index.rescope(tReferenceContext), tReferenceContext);
  }

}
