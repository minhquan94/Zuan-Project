/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * The superclass of reference nodes.
 *
 * @param <E>
 *          the element type
 */
public class IntegerReferenceNode<E> extends AbstractNode<Integer, E> {

  /** The reference. */
  private Reference<E> reference;

  /**
   * Instantiates a new integer reference node.
   *
   * @param reference
   *          the reference
   */
  public IntegerReferenceNode(Reference<E> reference) {
    this.reference = reference;
  }

  /**
   * Resolve value.
   *
   * @param context
   *          the context
   * @return the object
   */
  protected Object resolveValue(E context) {
    return reference.resolve(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    references.add(reference);
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
   * @see com.zuan.parser.codehaus.preon.el.ast.AbstractNode#isConstantFor(com.
   * zuan.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isConstantFor(ReferenceContext<E> context) {
    return reference.isBasedOn(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  @Override
  public Integer eval(E context) {
    Object value = resolveValue(context);
    Class< ? > type = value.getClass();
    if (Byte.class.equals(type)) {
      return ((Byte) value).intValue();
    } else if (Short.class.equals(type)) {
      return ((Short) value).intValue();
    } else if (Long.class.equals(type)) {
      return ((Long) value).intValue();
    } else if (Integer.class.equals(type)) {
      return (Integer) value;
    } else {
      final StringBuilder builder = new StringBuilder();
      builder.append("Type of ");
      builder.append(" as an integer value.");
      throw new ClassCastException(builder.toString());
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#getType()
   */
  @Override
  public Class<Integer> getType() {
    return Integer.class;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#simplify()
   */
  @Override
  public Node<Integer, E> simplify() {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Node<Integer, E> rescope(ReferenceContext<E> context) {
    return new IntegerReferenceNode<>(reference.rescope(context));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return true;
  }
}
