/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * A node representing an integer literal.
 *
 * @param <E>
 *          the element type
 */
public class IntegerNode<E> extends AbstractNode<Integer, E> {

  /**
   * The {@link Integer} value.
   */
  private Integer value;

  /**
   * Instantiates a new integer node.
   *
   * @param value
   *          the value
   */
  public IntegerNode(Integer value) {
    this.value = value;
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

  /**
   * From bin.
   *
   * @param bin
   *          the bin
   * @return the integer node
   */
  @SuppressWarnings("rawtypes")
  public static IntegerNode fromBin(String bin) {
    return new IntegerNode(Integer.parseInt(bin.substring(2), 2));
  }

  /**
   * From hex.
   *
   * @param hex
   *          the hex
   * @return the integer node
   */
  @SuppressWarnings("rawtypes")
  public static IntegerNode fromHex(String hex) {
    return new IntegerNode(Integer.parseInt(hex.substring(2), 16));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  @Override
  public Integer eval(E context) {
    return value;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    // Nothing to add
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    target.text(value.toString());
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
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Node<Integer, E> rescope(ReferenceContext<E> context) {
    return this;
  }

}
