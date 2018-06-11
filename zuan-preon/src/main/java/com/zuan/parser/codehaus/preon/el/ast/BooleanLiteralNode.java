/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * The Class BooleanLiteralNode.
 *
 * @author zuan_
 * @param <E>
 *          the element type
 */
public class BooleanLiteralNode<E> extends AbstractNode<Boolean, E> {

  /** The value. */
  private boolean value;

  /**
   * Instantiates a new boolean literal node.
   *
   * @param value
   *          the value
   */
  public BooleanLiteralNode(boolean value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  @Override
  public Boolean eval(E context) {
    return value;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#getType()
   */
  @Override
  public Class<Boolean> getType() {
    return Boolean.class;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#simplify()
   */
  @Override
  public Node<Boolean, E> simplify() {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Node<Boolean, E> rescope(ReferenceContext<E> eReferenceContext) {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    // Nothing to do
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    target.text(Boolean.toString(value));
  }
}
