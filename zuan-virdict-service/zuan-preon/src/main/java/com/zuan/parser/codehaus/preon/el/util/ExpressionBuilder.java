/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.util;

import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.InvalidExpressionException;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * A simple convenience class for building {@link Expression} instances, simply
 * encapsulating a {@link ReferenceContext}, allowing clients to be unaware of
 * the particular {@link ReferenceContext} in use.
 *
 * @param <E>
 *          The type of environment used to resolve references.
 */
public class ExpressionBuilder<E> {

  /**
   * The context for constructing references.
   */
  private ReferenceContext<E> context;

  /**
   * Constructs a new instance.
   *
   * @param context
   *          The context for constructing references.
   */
  public ExpressionBuilder(ReferenceContext<E> context) {
    this.context = context;
  }

  /**
   * Creates an {@link Expression} of type boolean.
   *
   * @param expr
   *          A character sequence conforming to the el grammar, returning a
   *          boolean.
   * @return A boolean {@link Expression}.
   * @throws InvalidExpressionException
   *           If the character sequence passed in is not a valid el expression
   *           returning a boolean, or if the references used cannot be linked
   *           to the {@link #context ReferenceContext}.
   */
  public Expression<Boolean, E> createBoolean(String expr) throws InvalidExpressionException {
    return Expressions.createBoolean(context, expr);
  }

  /**
   * Creates an {@link Expression} of type integer.
   *
   * @param expr
   *          A character sequence conforming to the el grammar, returning an
   *          integer.
   * @return A boolean {@link Expression}.
   * @throws InvalidExpressionException
   *           If the character sequence passed in is not a valid el expression
   *           returning an integer, or if the references used cannot be linked
   *           to the {@link #context ReferenceContext}.
   */
  public Expression<Integer, E> createInteger(String expr) throws InvalidExpressionException {
    return Expressions.createInteger(context, expr);
  }

}
