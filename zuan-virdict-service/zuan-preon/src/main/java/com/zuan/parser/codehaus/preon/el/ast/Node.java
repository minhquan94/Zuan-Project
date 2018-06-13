/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * A node in the AST representing the expression.
 *
 * @param <T>
 *          The type of value represented by the node. (Or more precisely, the
 *          type of value to which this node will evaluate when invoking
 *          {@link #eval(Object)}.
 * @param <E>
 *          the element type
 */
public interface Node<T, E> extends Expression<T, E> {

  /**
   * Evaluates this (part of an) expression and returns the result.
   *
   * @param context
   *          The object capable of resolving references.
   * @return The result of evaluating the expression.
   */
  @Override
  T eval(E context);

  /**
   * Returns the type of value to which a node will evaluate.
   *
   * @return The type of value to which a node will evaluate.
   */
  @Override
  Class<T> getType();

  /**
   * Attempts to simplify the expression. It will return a (potentially) new
   * node, containing the simplified substitute.
   *
   * @return The simplified expression.
   */
  @Override
  Node<T, E> simplify();

  /**
   * Rescope.
   *
   * @param context
   *          the context
   * @return the node
   */
  @Override
  Node<T, E> rescope(ReferenceContext<E> context);

  /**
   * Append references used.
   *
   * @param references
   *          The references gathered so far.
   */
  void gather(Set<Reference<E>> references);

  /**
   * Think {@link Comparable#compareTo(Object)}, but then requiring a context
   * for resolving variables.
   *
   * @param context
   *          The context allowing you to resolve references.
   * @param other
   *          The object to compare to.
   * @return See {@link Comparable#compareTo(Object)}.
   */
  int compareTo(E context, Node<T, E> other);

}
