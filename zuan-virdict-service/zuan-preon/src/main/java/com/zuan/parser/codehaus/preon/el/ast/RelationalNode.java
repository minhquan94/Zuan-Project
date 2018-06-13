/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Set;

import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;
import com.zuan.parser.codehaus.preon.el.util.ClassUtils;
import com.zuan.parser.codehaus.preon.el.util.StringBuilderDocument;

/**
 * The node representing (part of) an expression that translates to a boolean
 * value, based on two integer-type of nodes passed in.
 *
 * @param <T>
 *          the generic type
 * @param <E>
 *          the element type
 */
public class RelationalNode<T extends Comparable<T>, E> extends AbstractNode<Boolean, E> {

  /**
   * The Enum Relation.
   */
  public enum Relation {

    /** The gt. */
    GT {
      @Override
      <T, E> boolean holds(E context, Node<T, E> a, Node<T, E> b) {
        return a.compareTo(context, b) > 0;
      }

      @Override
      <T, E> void document(Node<T, E> a, Node<T, E> b, Document target) {
        a.document(target);
        target.text(" is greater than ");
        b.document(target);
      }
    },

    /** The gte. */
    GTE {
      @Override
      <T, E> boolean holds(E context, Node<T, E> a, Node<T, E> b) {
        return a.compareTo(context, b) >= 0;
      }

      @Override
      <T, E> void document(Node<T, E> a, Node<T, E> b, Document target) {
        a.document(target);
        target.text(" is greater than or equal to ");
        b.document(target);
      }
    },

    /** The eq. */
    EQ {
      @Override
      <T, E> boolean holds(E context, Node<T, E> a, Node<T, E> b) {
        return a.compareTo(context, b) == 0;
      }

      @Override
      <T, E> void document(Node<T, E> a, Node<T, E> b, Document target) {
        a.document(target);
        target.text(" equals ");
        b.document(target);
      }
    },

    /** The lt. */
    LT {
      @Override
      <T, E> boolean holds(E context, Node<T, E> a, Node<T, E> b) {
        return a.compareTo(context, b) < 0;
      }

      @Override
      <T, E> void document(Node<T, E> a, Node<T, E> b, Document target) {
        a.document(target);
        target.text(" is less than ");
        b.document(target);
      }
    },

    /** The lte. */
    LTE {
      @Override
      <T, E> boolean holds(E context, Node<T, E> a, Node<T, E> b) {
        return a.compareTo(context, b) <= 0;
      }

      @Override
      <T, E> void document(Node<T, E> a, Node<T, E> b, Document target) {
        a.document(target);
        target.text(" is less than or equal to ");
        b.document(target);
      }
    };

    /**
     * Holds.
     *
     * @param <T>
     *          the generic type
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
    abstract <T, E> boolean holds(E context, Node<T, E> lhs, Node<T, E> rhs);

    /**
     * Document.
     *
     * @param <T>
     *          the generic type
     * @param <E>
     *          the element type
     * @param lhs
     *          the lhs
     * @param rhs
     *          the rhs
     * @param target
     *          the target
     */
    abstract <T, E> void document(Node<T, E> lhs, Node<T, E> rhs, Document target);
  }

  /**
   * The relationship that needs to be evaluated.
   */
  private Relation relation;

  /**
   * The left-hand side of the expression.
   */
  private Node<T, E> lhs;

  /**
   * The right-hand side of the expression.
   */
  private Node<T, E> rhs;

  /**
   * Constructs a new instance.
   *
   * @param relation
   *          The relationship that needs to be evaluated.
   * @param lhs
   *          The left-hand side of the expression.
   * @param rhs
   *          The right-hand side of the expression.
   */
  public RelationalNode(Relation relation, Node<T, E> lhs, Node<T, E> rhs) {
    this.relation = relation;
    this.lhs = lhs;
    this.rhs = rhs;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  // JavaDoc inherited
  @Override
  public Boolean eval(E context) {
    return relation.holds(context, lhs, rhs);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#getType()
   */
  @Override
  public Class<Boolean> getType() {
    return Boolean.class;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.ast.Node#simplify()
   */
  @Override
  public Node<Boolean, E> simplify() {
    return this;
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
   * @see
   * org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    relation.document(lhs, rhs, target);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return lhs.isParameterized() || rhs.isParameterized();
  }

  /**
   * Creates the.
   *
   * @param <E>
   *          the element type
   * @param <T>
   *          the generic type
   * @param operator
   *          the operator
   * @param lhs
   *          the lhs
   * @param rhs
   *          the rhs
   * @return the relational node
   */
  @SuppressWarnings({"unchecked", "rawtypes" })
  public static <E, T extends Comparable<T>> RelationalNode<T, E> create(Relation operator,
      Node< ? , E> lhs, Node< ? , E> rhs) {

    // Warning, highly experimental piece of code following.
    // This is to 'reduce' mult references to their corresponding types
    Class< ? > rhsType = rhs.getType();
    Class< ? > lhsType = lhs.getType();
    if (rhsType != lhsType) {
      if (rhs instanceof ReferenceNode && rhsType.isAssignableFrom(lhsType)) {
        rhs = ((ReferenceNode) rhs).narrow(lhsType);
      } else if (lhs instanceof ReferenceNode && lhsType.isAssignableFrom(rhsType)) {
        lhs = ((ReferenceNode) lhs).narrow(rhsType);
      }
    }
    Class< ? > common = ClassUtils.calculateCommonSuperType(lhs.getType(), rhs.getType());
    if (Comparable.class.isAssignableFrom(common)) {
      Node<T, E> comparableLhs = createComparableNode(lhs);
      Node<T, E> comparableRhs = createComparableNode(rhs);
      return new RelationalNode<>(operator, comparableLhs, comparableRhs);
    } else {
      StringBuilder builder = new StringBuilder();
      lhs.document(new StringBuilderDocument(builder));
      builder.append(" and ");
      rhs.document(new StringBuilderDocument(builder));
      builder.append(" are incompatible.");
      throw new BindingException(builder.toString());
    }
  }

  /**
   * Creates the comparable node.
   *
   * @param <T>
   *          the generic type
   * @param <E>
   *          the element type
   * @param node
   *          the node
   * @return the node
   */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>, E> Node<T, E> createComparableNode(
      Node< ? , E> node) {
    if (!Comparable.class
        .isAssignableFrom(ClassUtils.getGuaranteedBoxedVersion(node.getType()))) {
      StringBuilder builder = new StringBuilder();
      node.document(new StringBuilderDocument(builder));
      throw new BindingException(
          "Reference " + builder.toString() + " does not resolve to Comparable.");
    } else {
      return (Node<T, E>) node;
    }
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

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Node<Boolean, E> rescope(ReferenceContext<E> context) {
    return new RelationalNode<>(relation, lhs.rescope(context), rhs.rescope(context));
  }
}
