/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Set;

import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;
import com.zuan.parser.codehaus.preon.el.util.StringBuilderDocument;

/**
 * A {@link Node} representing a combinatorial boolean operator.
 *
 * @param <E>
 *          The type of context on which this node would be applied.
 */
public class BooleanOperatorNode<E> extends AbstractNode<Boolean, E> {

  /**
   * The left-hand side of the operation.
   */
  private Node<Boolean, E> lhs;

  /**
   * The right-hand side of the operation.
   */
  private Node<Boolean, E> rhs;

  /**
   * The operator to apply.
   */
  private BooleanOperator operator;

  /**
   * The Enum BooleanOperator.
   */
  public enum BooleanOperator {

    /** The and. */
    AND {
      @Override
      <E> boolean holds(E context, Node<Boolean, E> lhs, Node<Boolean, E> rhs) {
        return lhs.eval(context) && rhs.eval(context);
      }

      @Override
      <E> void document(Node<Boolean, E> lhs, Node<Boolean, E> rhs, Document target) {
        lhs.document(target);
        target.text(" and ");
        rhs.document(target);
      }
    },

    /** The or. */
    OR {
      @Override
      <E> boolean holds(E context, Node<Boolean, E> lhs, Node<Boolean, E> rhs) {
        return lhs.eval(context) || rhs.eval(context);
      }

      @Override
      <E> void document(Node<Boolean, E> lhs, Node<Boolean, E> rhs, Document target) {
        lhs.document(target);
        target.text(" or ");
        rhs.document(target);
      }
    };

    /**
     * Holds.
     *
     * @param <E>
     *          the element type
     * @param context
     *          the context
     * @param lhs
     *          the lhs
     * @param rhs
     *          the rhs
     * @return true, if successful
     */
    abstract <E> boolean holds(E context, Node<Boolean, E> lhs, Node<Boolean, E> rhs);

    /**
     * Document.
     *
     * @param <E>
     *          the element type
     * @param lhs
     *          the lhs
     * @param rhs
     *          the rhs
     * @param target
     *          the target
     */
    abstract <E> void document(Node<Boolean, E> lhs, Node<Boolean, E> rhs, Document target);

  }

  /**
   * Constructs a new instance, accepting the operator, and the left-hand and
   * right-hand side.
   *
   * @param operator
   *          The operator to apply.
   * @param lhs
   *          The left-hand side of the operation.
   * @param rhs
   *          The right-hand side of the operation.
   */
  public BooleanOperatorNode(BooleanOperator operator, Node<Boolean, E> lhs,
      Node<Boolean, E> rhs) {
    this.operator = operator;
    this.lhs = lhs;
    this.rhs = rhs;
  }

  /*
   * (non-Javadoc)
   * @see ast.Node#eval(java.lang.Object)
   */
  @Override
  public Boolean eval(E context) {
    return operator.holds(context, lhs, rhs);
  }

  /*
   * (non-Javadoc)
   * @see ast.Node#getType()
   */
  @Override
  public Class<Boolean> getType() {
    return Boolean.class;
  }

  /*
   * (non-Javadoc)
   * @see ast.Node#simplify()
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
  public Node<Boolean, E> rescope(ReferenceContext<E> context) {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see Descriptive#document(Document)
   */
  @Override
  public void document(Document target) {
    operator.document(lhs, rhs, target);
  }

  /*
   * (non-Javadoc)
   * @see ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    lhs.gather(references);
    rhs.gather(references);
  }

  /*
   * (non-Javadoc)
   * @see Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return lhs.isParameterized() || rhs.isParameterized();
  }

  /**
   * Constructs a new {@link BooleanOperatorNode}.
   *
   * @param <E>
   *          the element type
   * @param operator
   *          the operator
   * @param lhs
   *          the lhs
   * @param rhs
   *          the rhs
   * @return the boolean operator node
   */
  public static <E> BooleanOperatorNode<E> create(BooleanOperator operator, Node< ? , E> lhs,
      Node< ? , E> rhs) {
    Node<Boolean, E> booleanLhs = createBooleanNode(lhs);
    Node<Boolean, E> booleanRhs = createBooleanNode(rhs);
    return new BooleanOperatorNode<>(operator, booleanLhs, booleanRhs);
  }

  /**
   * Creates a boolean {@link Node} from the untyped {@link Node} passed in.
   *
   * @param <E>
   *          The type of context on which the node will be applied.
   * @param node
   *          The untyped node.
   * @return A boolean node.
   */
  @SuppressWarnings("unchecked")
  public static <E> Node<Boolean, E> createBooleanNode(Node< ? , E> node) {
    if (!boolean.class.isAssignableFrom(node.getType())
        && !Boolean.class.isAssignableFrom(node.getType())) {
      StringBuilder builder = new StringBuilder();
      node.document(new StringBuilderDocument(builder));
      throw new BindingException(
          "Reference " + builder.toString() + " does not resolve to boolean.");
    } else {
      return (Node<Boolean, E>) node;
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.AbstractNode#isConstantFor(com.
   * zuan.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isConstantFor(ReferenceContext<E> eReferenceContext) {
    return lhs.isConstantFor(eReferenceContext) && rhs.isConstantFor(eReferenceContext);

  }
}
