/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.HashSet;
import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * A base class for {@link Node} implementations, implementing the
 * {@link #getReferences()} operation.
 *
 * @param <T>
 *          The type of value to which this AST node resolves in
 *          {@link #eval(Object)}
 * @param <E>
 *          The type of context passed in to resolve references.
 */
public abstract class AbstractNode<T extends Comparable<T>, E> implements Node<T, E> {

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#compareTo(java.lang.
   * Object, com.zuan.parser.codehaus.preon.el.ast.Node)
   */
  @Override
  public int compareTo(E context, Node<T, E> other) {
    return eval(context).compareTo(other.eval(context));
  }

  /**
   * Gets the references.
   *
   * @return the references
   */
  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Expression#getReferences()
   */
  @Override
  public Set<Reference<E>> getReferences() {
    Set<Reference<E>> references = new HashSet<>();
    gather(references);
    return references;
  }

  /**
   * Checks if is constant for.
   *
   * @param eReferenceContext
   *          the e reference context
   * @return true, if is constant for
   */
  @Override
  public boolean isConstantFor(ReferenceContext<E> eReferenceContext) {
    return true;
  }

}
