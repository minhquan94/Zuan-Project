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
 * A representation of an arithmetic node in the tree representing an
 * expression, holding a left-hand value, an operator and a right-hand value.
 *
 * @param <E>
 *          the element type
 */
public class ArithmeticNode<E> extends AbstractNode<Integer, E> {

  /**
   * The left-hand side of the expression.
   */
  private Node<Integer, E> lhs;

  /**
   * The right-hand side of the expression.
   */
  private Node<Integer, E> rhs;

  /**
   * The operator.
   */
  private Operator operator;

  /**
   * An enumeration of all operators allowed. (Note that every value also
   * implements the strategy for computing and describing the expression with
   * the infix operator.)
   */
  public enum Operator {

    /** The pow. */
    pow {
      @Override
      <E> int eval(E context, Node<Integer, E> a, Node<Integer, E> b) {
        return (int) Math.pow(a.eval(context).intValue(), b.eval(context).intValue());
      }

      @Override
      <E> void document(Node<Integer, E> a, Node<Integer, E> b, Document target) {
        a.document(target);
        target.text(" to the power of ");
        b.document(target);
      }
    },

    /** The div. */
    div {
      @Override
      <E> int eval(E context, Node<Integer, E> a, Node<Integer, E> b) {
        return a.eval(context).intValue() / b.eval(context).intValue();
      }

      @Override
      <E> void document(Node<Integer, E> a, Node<Integer, E> b, Document target) {
        a.document(target);
        target.text(" divided by ");
        b.document(target);
      }
    },

    /** The plus. */
    plus {
      @Override
      <E> int eval(E context, Node<Integer, E> a, Node<Integer, E> b) {
        return a.eval(context).intValue() + b.eval(context).intValue();
      }

      @Override
      <E> void document(Node<Integer, E> a, Node<Integer, E> b, Document target) {
        target.text("the sum of ");
        a.document(target);
        target.text(" and ");
        b.document(target);
      }
    },

    /** The minus. */
    minus {
      @Override
      <E> int eval(E context, Node<Integer, E> a, Node<Integer, E> b) {
        return a.eval(context).intValue() - b.eval(context).intValue();
      }

      @Override
      <E> void document(Node<Integer, E> a, Node<Integer, E> b, Document target) {
        target.text("the difference between ");
        a.document(target);
        target.text(" and ");
        b.document(target);
      }
    },

    /** The mult. */
    mult {
      @Override
      <E> int eval(E context, Node<Integer, E> a, Node<Integer, E> b) {
        return a.eval(context).intValue() * b.eval(context).intValue();
      }

      @Override
      <E> void document(Node<Integer, E> a, Node<Integer, E> b, Document target) {
        a.document(target);
        target.text(" times ");
        b.document(target);
      }
    };

    /**
     * Evaluates application of the infix operator on the two terms passed in.
     *
     * @param <E>
     *          the element type
     * @param context
     *          the context
     * @param lhs
     *          The left-hand side of the expression.
     * @param rhs
     *          The right-hand side of the expression.
     * @return An integer value.
     */
    abstract <E> int eval(E context, Node<Integer, E> lhs, Node<Integer, E> rhs);

    /**
     * writes the expression.
     *
     * @param <E>
     *          the element type
     * @param lhs
     *          The left-hand side of the expression.
     * @param rhs
     *          The right-hand side of the expression.
     * @param target
     *          The object receiving the description.
     */
    abstract <E> void document(Node<Integer, E> lhs, Node<Integer, E> rhs, Document target);
  }

  /**
   * Constructs a new ArithmeticNode, accepting the operator, the left-hand side
   * and the right-hand side.
   *
   * @param operator
   *          the operator
   * @param lhs
   *          the lhs
   * @param rhs
   *          the rhs
   */
  public ArithmeticNode(Operator operator, Node<Integer, E> lhs, Node<Integer, E> rhs) {
    this.operator = operator;
    this.lhs = lhs;
    this.rhs = rhs;
  }

  /**
   * Attempts to construct a new {@link ArithmeticNode} from the operator and
   * other nodes passed in.
   *
   * @param <E>
   *          The type of context for evaluation of references.
   * @param operator
   *          The operator to apply.
   * @param lhs
   *          The left hand side of the operation.
   * @param rhs
   *          The right hand side of the operation.
   * @return The newly constructed {@link ArithmeticNode}.
   * @throws BindingException
   *           the binding exception
   */
  public static <E> ArithmeticNode<E> create(Operator operator, Node< ? , E> lhs,
      Node< ? , E> rhs) throws BindingException {
    Node<Integer, E> lhsInteger = createIntegerNode(operator, lhs);
    Node<Integer, E> rhsInteger = createIntegerNode(operator, rhs);
    return new ArithmeticNode<>(operator, lhsInteger, rhsInteger);
  }

  /**
   * Constructs a new.
   *
   * @param <E>
   *          the element type
   * @param operator
   *          the operator
   * @param node
   *          the node
   * @return the node
   * @throws BindingException
   *           the binding exception
   */
  @SuppressWarnings("unchecked")
  private static <E> Node<Integer, E> createIntegerNode(Operator operator, Node< ? , E> node)
      throws BindingException {
    if (!Integer.class.isAssignableFrom(node.getType())
        && !int.class.isAssignableFrom(node.getType())) {
      StringBuilder builder = new StringBuilder();
      node.document(new StringBuilderDocument(builder));
      throw new BindingException(
          "Reference " + builder.toString() + " does not resolve to integer.");
    } else {
      return (Node<Integer, E>) node;
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#getType()
   */
  // JavaDoc inherited
  @Override
  public Class<Integer> getType() {
    return Integer.class;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#simplify()
   */
  // JavaDoc inherited
  @Override
  public Node<Integer, E> simplify() {
    Node<Integer, E> simplifiedLhs = lhs.simplify();
    Node<Integer, E> simplifiedRhs = rhs.simplify();
    if (simplifiedLhs instanceof IntegerNode && simplifiedRhs instanceof IntegerNode) {
      return new IntegerNode<>(this.eval(null));
    } else if (simplifiedLhs instanceof IntegerNode) {
      return new ArithmeticNode<>(operator, simplifiedLhs, rhs);
    } else if (simplifiedRhs instanceof IntegerNode) {
      return new ArithmeticNode<>(operator, lhs, simplifiedRhs);
    }
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Node<Integer, E> rescope(ReferenceContext<E> context) {
    return new ArithmeticNode<>(operator, lhs.rescope(context), rhs.rescope(context));
  }

  /**
   * Returns the left-hand side of the expression.
   *
   * @return The left-hand side of the expression.
   */
  public Node<Integer, E> getLhs() {
    return lhs;
  }

  /**
   * Returns the right-hand side of the expression.
   *
   * @return The right-hand side of the expression.
   */
  public Node<Integer, E> getRhs() {
    return rhs;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  @Override
  public Integer eval(E context) {
    return operator.eval(context, lhs, rhs);
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    operator.document(lhs, rhs, target);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    lhs.gather(references);
    rhs.gather(references);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return lhs.isParameterized() || rhs.isParameterized();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.AbstractNode#isConstantFor(com.
   * zuan.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isConstantFor(ReferenceContext<E> context) {
    return lhs.isConstantFor(context) && rhs.isConstantFor(context);
  }
}
