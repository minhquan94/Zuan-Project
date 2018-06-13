/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.HashSet;
import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;
import com.zuan.parser.codehaus.preon.el.util.Converter;
import com.zuan.parser.codehaus.preon.el.util.Converters;

/**
 * A {@link Node} with the ability to convert to other types of {@link Node}s.
 * Use the static {@link #tryConversion(Node, Class)} and
 * {@link #tryConversionToIntegerNode(Node, Class)} to convert Nodes in some
 * other type of node.
 *
 * @param <T>
 *          The target type of node.
 * @param <E>
 *          The type of context.
 * @param <S>
 *          The source type of node.
 */
public class ConvertingNode<T extends Comparable<T>, E, S> implements Node<T, E> {

  /**
   * The source {@link Node}.
   */
  private Node<S, E> source;

  /**
   * The {@link Converter} used to convert types from S to N.
   */
  private Converter<S, T> converter;

  /**
   * Constructs a new instance, accepting the {@link Converter} to be applied
   * and the source {@link Node}.
   *
   * @param converter
   *          The {@link Converter}.
   * @param source
   *          The source {@link Node}.
   */
  public ConvertingNode(Converter<S, T> converter, Node<S, E> source) {
    this.source = source;
    this.converter = converter;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#compareTo(java.lang.
   * Object, com.zuan.parser.codehaus.preon.el.ast.Node)
   */
  @Override
  public int compareTo(E context, Node<T, E> other) {
    return this.eval(context).compareTo(other.eval(context));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  @Override
  public T eval(E context) {
    return converter.convert(source.eval(context));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    source.gather(references);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#getType()
   */
  @Override
  public Class<T> getType() {
    return converter.getTargetType();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#simplify()
   */
  @Override
  public Node<T, E> simplify() {
    return new ConvertingNode<>(converter, source.simplify());
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Node<T, E> rescope(ReferenceContext<E> context) {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Expression#isConstantFor(com.
   * zuan.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isConstantFor(ReferenceContext<E> context) {
    return source.isConstantFor(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Expression#getReferences()
   */
  @Override
  public Set<Reference<E>> getReferences() {
    Set<Reference<E>> references = new HashSet<>();
    gather(references);
    return references;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return source.isParameterized();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    source.document(target);
  }

  /**
   * Try conversion.
   *
   * @param <T>
   *          the generic type
   * @param <E>
   *          the element type
   * @param <S>
   *          the generic type
   * @param source
   *          the source
   * @param targetType
   *          the target type
   * @return the node
   */
  public static <T extends Comparable<T>, E, S> Node< ? , E> tryConversion(Node<S, E> source,
      Class<T> targetType) {
    Converter<S, T> converter = Converters.get(source.getType(), targetType);
    if (converter != null) {
      return new ConvertingNode<>(converter, source);
    } else {
      return source;
    }
  }

  /**
   * Try conversion to integer node.
   *
   * @param <T>
   *          the generic type
   * @param <E>
   *          the element type
   * @param <S>
   *          the generic type
   * @param source
   *          the source
   * @return the node
   */
  public static <T, E, S> Node< ? , E> tryConversionToIntegerNode(Node<S, E> source) {
    Class< ? > type = source.getType();
    if (Byte.class == type || Short.class == type || Long.class == type) {
      return tryConversion(source, Integer.class);
    } else {
      return source;
    }
  }

}
