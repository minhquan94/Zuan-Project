/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import java.util.Collections;
import java.util.Set;

import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;
import com.zuan.parser.codehaus.preon.el.ctx.ConvertingReference;
import com.zuan.parser.codehaus.preon.el.util.ClassUtils;

/**
 * The Class ReferenceNode.
 *
 * @author zuan_
 * @param <T>
 *          the generic type
 * @param <E>
 *          the element type
 */
public class ReferenceNode<T, E> implements Node<T, E> {

  /** The reference. */
  private Reference<E> reference;

  /**
   * Instantiates a new reference node.
   *
   * @param reference
   *          the reference
   */
  public ReferenceNode(Reference<E> reference) {
    Class< ? > type = reference.getType();
    if (Byte.class == type || Short.class == type || Long.class == type || byte.class == type
        || long.class == type || short.class == type) {
      // Let's convert to Integer, to make our life a little easier.
      this.reference = ConvertingReference.create(Integer.class, reference);
    } else {
      this.reference = reference;
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#compareTo(java.lang.
   * Object, com.zuan.parser.codehaus.preon.el.ast.Node)
   */
  @SuppressWarnings({"unchecked", "rawtypes" })
  @Override
  public int compareTo(E context, Node<T, E> other) {
    Class< ? > thisType = getType();
    Class< ? > otherType = other.getType();
    Class< ? > common = ClassUtils.calculateCommonSuperType(thisType, otherType);
    if (Comparable.class.isAssignableFrom(common)) {
      return ((Comparable) eval(context)).compareTo(other.eval(context));
    } else {
      throw new ClassCastException("Incomparable types.");
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#eval(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public T eval(E context) {
    Object result = reference.resolve(context);
    return (T) result;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#gather(java.util.Set)
   */
  @Override
  public void gather(Set<Reference<E>> references) {
    references.add(reference);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#getType()
   */
  @SuppressWarnings("unchecked")
  @Override
  public Class<T> getType() {
    return (Class<T>) reference.getType();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#simplify()
   */
  @Override
  public Node<T, E> simplify() {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Node#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Node<T, E> rescope(ReferenceContext<E> context) {
    return new ReferenceNode<>(reference.rescope(context));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Expression#isConstantFor(com.
   * zuan.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isConstantFor(ReferenceContext<E> context) {
    return reference.isBasedOn(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Expression#getReferences()
   */
  @Override
  public Set<Reference<E>> getReferences() {
    return Collections.singleton(reference);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Expression#isParameterized()
   */
  @Override
  public boolean isParameterized() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    reference.document(target);
  }

  /**
   * Narrows the underlying reference to a reference of the given type, and
   * returns a new ReferenceNode pointing to this new reference.
   *
   * @param <V>
   *          the value type
   * @param type
   *          The type to narrow to.
   * @return A new ReferenceNode, pointing to a narrowed version of the
   *         reference held by this object, or the original ReferenceNode, if
   *         the Reference cannot be narrowed.
   */
  public <V> Node< ? , E> narrow(Class<V> type) {
    Reference<E> narrowed = reference.narrow(type);
    if (narrowed == null) {
      return this;
    } else {
      return new ReferenceNode<V, E>(narrowed);
    }
  }

}
