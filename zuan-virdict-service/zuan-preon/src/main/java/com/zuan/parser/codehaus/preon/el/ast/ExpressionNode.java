/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * A {@link Node} wrapping around an {@link Expression}. Relevant in cases in
 * which we don't have access to the AST representation of the expression.
 *
 * @param <T>
 *          The type of value to which this node evaluates.
 * @param <E>
 *          The type of context required to evaluate this node.
 */
public class ExpressionNode<T extends Comparable<T>, E> extends AbstractNode<T, E> {

  /**
   * The expression held by this node.
   */
  private Expression<T, E> expression;

  /**
   * Constructs a new instance.
   *
   * @param expression
   *          The expression to be represented by this node.
   */
  public ExpressionNode(Expression<T, E> expression) {
    this.expression = expression;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  @Override
  public T eval(E context) {
    return expression.eval(context);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    references.addAll(expression.getReferences());
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#getType()
   */
  @Override
  public Class<T> getType() {
    return expression.getType();
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#simplify()
   */
  @Override
  public Node<T, E> simplify() {
    return new ExpressionNode<>(expression.simplify());
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @SuppressWarnings({"unchecked", "rawtypes" })
  @Override
  public Node<T, E> rescope(ReferenceContext<E> context) {
    return new ExpressionNode(expression.rescope(context));
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return expression.isParameterized();
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    expression.document(target);
  }

}
