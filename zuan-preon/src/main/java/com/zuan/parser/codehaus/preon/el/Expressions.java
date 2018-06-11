/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import com.zuan.parser.codehaus.preon.el.ast.ArithmeticNode;
import com.zuan.parser.codehaus.preon.el.ast.ArithmeticNode.Operator;
import com.zuan.parser.codehaus.preon.el.ast.ExpressionNode;
import com.zuan.parser.codehaus.preon.el.ast.IntegerNode;
import com.zuan.parser.codehaus.preon.el.ast.Node;

/**
 * A convenience class for creating Expressions.
 */
public class Expressions {

  /**
   * Creates an {@link Expression} from the Limbo expression passed in. (Will
   * fail if the expression passed in does not return a boolean value.)
   *
   * @param <E>
   *          The type of environment that will be passed in when evaluating the
   *          expression.
   * @param context
   *          The context for this expression.
   * @param expr
   *          The Limbo expression.
   * @return An {@link Expression} object that can be evaluated against
   *         instances of <code>E</code>.
   * @throws InvalidExpressionException
   *           If the expression cannot be created. (Either because references
   *           can not be bound to the context, or because the parser fails to
   *           parse the Limbo expression.)
   */
  public static <E> Expression<Boolean, E> createBoolean(ReferenceContext<E> context,
      String expr) throws InvalidExpressionException {
    return condition(context, expr);
  }

  /**
   * Creates the.
   *
   * @param <E>
   *          the element type
   * @param context
   *          the context
   * @param expr
   *          the expr
   * @return the expression
   */
  public static <E> Expression<Object, E> create(ReferenceContext<E> context, String expr) {
    return any(context, expr);
  }

  /**
   * Creates an {@link Expression} from the Limbo expression passed in. (Will
   * fail if the expression passed in does not return a integer value.)
   *
   * @param <E>
   *          The type of environment that will be passed in when evaluating the
   *          expression.
   * @param context
   *          The context for this expression.
   * @param expr
   *          The Limbo expression.
   * @return An {@link Expression} object that can be evaluated against
   *         instances of <code>E</code>.
   * @throws InvalidExpressionException
   *           If the expression cannot be created. (Either because references
   *           can not be bound to the context, or because the parser fails to
   *           parse the Limbo expression.)
   */
  public static <E> Expression<Integer, E> createInteger(ReferenceContext<E> context,
      String expr) throws InvalidExpressionException {
    return arithmetic(context, expr);
  }

  /**
   * Creates an {@link Expression} wrapping around a number.
   *
   * @param <E>
   *          The type of context to which this expression should be applied.
   * @param value
   *          The value to be wrapped.
   * @param cl
   *          The type of context expected by the Expression.
   * @return An {@link Expression} evaluating to <code>value</code>.
   */
  public static <E> Expression<Integer, E> createInteger(int value, Class<E> cl) {
    return new IntegerNode<>(value);
  }

  /**
   * Arithmetic.
   *
   * @param <E>
   *          the element type
   * @param context
   *          the context
   * @param expr
   *          the expr
   * @return the node
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  @SuppressWarnings("unchecked")
  private static <E> Node<Integer, E> arithmetic(ReferenceContext<E> context, String expr)
      throws InvalidExpressionException {
    try {
      return buildWalker(context, expr).vexpr();
    } catch (RecognitionException re) {
      throw new InvalidExpressionException(re);
    }
  }

  /**
   * Condition.
   *
   * @param <E>
   *          the element type
   * @param context
   *          the context
   * @param expr
   *          the expr
   * @return the node
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  @SuppressWarnings("unchecked")
  private static <E> Node<Boolean, E> condition(ReferenceContext<E> context, String expr)
      throws InvalidExpressionException {
    try {
      return buildWalker(context, expr).zexpr();
    } catch (RecognitionException re) {
      throw new InvalidExpressionException(re);
    }
  }

  /**
   * Any.
   *
   * @param <E>
   *          the element type
   * @param context
   *          the context
   * @param expr
   *          the expr
   * @return the node
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  @SuppressWarnings("unchecked")
  private static <E> Node<Object, E> any(ReferenceContext<E> context, String expr)
      throws InvalidExpressionException {
    try {
      return buildWalker(context, expr).fexpr();
    } catch (RecognitionException re) {
      throw new InvalidExpressionException(re);
    }
  }

  /**
   * Builds the walker.
   *
   * @param <E>
   *          the element type
   * @param context
   *          the context
   * @param expr
   *          the expr
   * @return the limbo walker
   * @throws RecognitionException
   *           the recognition exception
   */
  @SuppressWarnings({"unchecked", "rawtypes" })
  private static <E> LimboWalker buildWalker(ReferenceContext<E> context, String expr)
      throws RecognitionException {
    ANTLRStringStream in = new ANTLRStringStream(expr);
    LimboLexer lexer = new LimboLexer(in);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LimboParser parser = new LimboParser(tokens);
    CommonTree tree = (CommonTree) parser.condExpression().getTree();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
    nodes.setTokenStream(tokens);
    LimboWalker walker = new LimboWalker(nodes, new ImplicitsContext(context));
    return walker;
  }

  /**
   * From.
   *
   * @param <C>
   *          the generic type
   * @param contextType
   *          the context type
   * @return the contextualized expression builder
   */
  public static <C> ContextualizedExpressionBuilder<C> from(Class<C> contextType) {
    return new ContextualizedExpressionBuilderImpl<>(contextType);
  }

  /**
   * From.
   *
   * @param <C>
   *          the generic type
   * @param contextType
   *          the context type
   * @return the bound expression builder
   */
  public static <C> BoundExpressionBuilder<C> from(ReferenceContext<C> contextType) {
    return new BoundExpressionBuilderImpl<>(contextType);
  }

  /**
   * The Interface ContextualizedExpressionBuilder.
   *
   * @param <C>
   *          the generic type
   */
  public interface ContextualizedExpressionBuilder<C> extends BoundExpressionBuilder<C> {

    /**
     * Using.
     *
     * @param binding
     *          the binding
     * @return the bound expression builder
     */
    BoundExpressionBuilder<C> using(Binding binding);

  }

  /**
   * An expression builder that has already been tied to a
   * {@link ReferenceContext}.
   *
   * @author Wilfred Springer
   * @param <C>
   *          The type of context to which the expressions will be bound.
   */
  public interface BoundExpressionBuilder<C> {

    /**
     * Constructs an expression that is supposed to evaluate to a boolean.
     *
     * @param expr
     *          The Limbo expression, as text.
     * @return An {@link Expression} object, encapsulating the expression passed
     *         in as a String.
     * @throws BindingException
     *           If the expression contains references that cannot be bound to
     *           the {@link ReferenceContext}.
     * @throws InvalidExpressionException
     *           If the expression is not a valid Limbo expression.
     */
    Expression<Boolean, C> toBoolean(String expr)
        throws BindingException, InvalidExpressionException;

    /**
     * Constructs an expression that is supposed to evaluate to a integer.
     *
     * @param expr
     *          The Limbo expression, as text.
     * @return An {@link Expression} object, encapsulating the expression passed
     *         in as a String.
     * @throws BindingException
     *           If the expression contains references that cannot be bound to
     *           the {@link ReferenceContext}.
     * @throws InvalidExpressionException
     *           If the expression is not a valid Limbo expression.
     */
    Expression<Integer, C> toInteger(String expr)
        throws BindingException, InvalidExpressionException;

  }

  /**
   * The Class BoundExpressionBuilderImpl.
   *
   * @param <C>
   *          the generic type
   */
  private static class BoundExpressionBuilderImpl<C> implements BoundExpressionBuilder<C> {

    /** The context. */
    private ReferenceContext<C> context;

    /**
     * Instantiates a new bound expression builder impl.
     *
     * @param context
     *          the context
     */
    public BoundExpressionBuilderImpl(ReferenceContext<C> context) {
      this.context = context;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Expressions.BoundExpressionBuilder#
     * toBoolean(java.lang.String)
     */
    @Override
    public Expression<Boolean, C> toBoolean(String expr)
        throws BindingException, InvalidExpressionException {
      return createBoolean(context, expr);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Expressions.BoundExpressionBuilder#
     * toInteger(java.lang.String)
     */
    @Override
    public Expression<Integer, C> toInteger(String expr)
        throws BindingException, InvalidExpressionException {
      return createInteger(context, expr);
    }

  }

  /**
   * The Class ContextualizedExpressionBuilderImpl.
   *
   * @param <C>
   *          the generic type
   */
  private static class ContextualizedExpressionBuilderImpl<C>
      implements ContextualizedExpressionBuilder<C> {

    /** The default. */
    private static Binding DEFAULT = Bindings.EARLY_BINDING;

    /** The type. */
    private Class< ? > type;

    /**
     * Instantiates a new contextualized expression builder impl.
     *
     * @param type
     *          the type
     */
    public ContextualizedExpressionBuilderImpl(Class< ? > type) {
      this.type = type;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Expressions.
     * ContextualizedExpressionBuilder#using(com.zuan.parser.codehaus.preon.
     * el.Binding)
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes" })
    public BoundExpressionBuilder<C> using(Binding binding) {
      return new BoundExpressionBuilderImpl(DEFAULT.create(type));
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Expressions.BoundExpressionBuilder#
     * toBoolean(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Expression<Boolean, C> toBoolean(String expr)
        throws BindingException, InvalidExpressionException {
      return (Expression<Boolean, C>) createBoolean(DEFAULT.create(type), expr);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Expressions.BoundExpressionBuilder#
     * toInteger(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Expression<Integer, C> toInteger(String expr)
        throws BindingException, InvalidExpressionException {
      return (Expression<Integer, C>) createInteger(DEFAULT.create(type), expr);
    }

  }

  /**
   * Returns a new expression, representing the multiplication of the two
   * {@link Expression}s passed in.
   *
   * @param <C>
   *          The type of context of the expressions.
   * @param first
   *          The first expression.
   * @param second
   *          The second expression.
   * @return A new {@link Expression}, representing the multiplication of the
   *         two arguments passed in. Returns <code>null</code> if either one of
   *         the arguments is <code>null</code>.
   */
  public static <C> Expression<Integer, C> multiply(Expression<Integer, C> first,
      Expression<Integer, C> second) {
    return combine(Operator.mult, first, second);
  }

  /**
   * Returns a new expression, representing the sum of the two
   * {@link Expression}s passed in.
   *
   * @param <C>
   *          The type of context of the expressions.
   * @param first
   *          The first expression.
   * @param second
   *          The second expression.
   * @return A new {@link Expression}, representing the sum of the two arguments
   *         passed in. Returns <code>null</code> if either one of the arguments
   *         is <code>null</code>.
   */
  public static <C> Expression<Integer, C> add(Expression<Integer, C> first,
      Expression<Integer, C> second) {
    return combine(Operator.plus, first, second);
  }

  /**
   * Returns a new {@link Node}.
   *
   * @param <C>
   *          The type of context to be passed in into the Node in order to
   *          calculate a result.
   * @param operator
   *          The operator to be applied.
   * @param first
   *          The first operator.
   * @param second
   *          The second operator.
   * @return A new {@link Node}.
   */
  private static <C> Node<Integer, C> combine(Operator operator, Expression<Integer, C> first,
      Expression<Integer, C> second) {
    if (first == null || second == null) {
      return null;
    }
    Node<Integer, C> firstNode;
    Node<Integer, C> secondNode;
    if (first instanceof Node) {
      firstNode = (Node<Integer, C>) first;
    } else {
      firstNode = new ExpressionNode<>(first);
    }
    if (second instanceof Node) {
      secondNode = (Node<Integer, C>) second;
    } else {
      secondNode = new ExpressionNode<>(second);
    }
    return new ArithmeticNode<>(operator, firstNode, secondNode);
  }

}
