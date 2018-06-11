/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * A convenience {@link Node} wrapper around <code>String</code>s.
 *
 * @param <E>
 *          the element type
 */
public class StringNode<E> extends AbstractNode<String, E> {

  /** The value. */
  private String value;

  /**
   * Instantiates a new string node.
   *
   * @param value
   *          the value
   */
  public StringNode(String value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  @Override
  public String eval(E context) {
    return value;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    // do nothing
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#getType()
   */
  @Override
  public Class<String> getType() {
    return String.class;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#simplify()
   */
  @Override
  public Node<String, E> simplify() {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Node<String, E> rescope(ReferenceContext<E> eReferenceContext) {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    target.text("the String \"");
    target.text(value);
    target.text("\"");
  }

}
