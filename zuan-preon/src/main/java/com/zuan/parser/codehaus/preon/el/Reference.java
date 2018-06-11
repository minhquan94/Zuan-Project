/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import com.zuan.parser.codehaus.preon.el.ctx.MultiReference;

/**
 * A reference to an Object, based on an environment of type <code>E</type>.
 *
 * @param <E>
 *          The type of environment on which this reference is based.
 */
public interface Reference<E> extends ReferenceContext<E> {

  /**
   * Resolves the reference in a certain context.
   *
   * @param context
   *          The context in which references need to be resolved.
   * @return The object referenced by the Reference.
   */
  Object resolve(E context);

  /**
   * Returns the context in which this {@link Reference} is applicable.
   *
   * @return The {@link ReferenceContext} in which this reference is applicable.
   */
  ReferenceContext<E> getReferenceContext();

  /**
   * Returns a boolean indicating if the value referenced by this reference can
   * be assigned to a variable of the given type.
   *
   * @param type
   *          The type of variable that needs to receive the value.
   * @return A boolean indicating if the value reference by this reference can
   *         be assigned to a variable of the given type.
   */
  boolean isAssignableTo(Class< ? > type);

  /**
   * Returns the type of value referenced by this reference.
   *
   * @return The type of value reference by this reference.
   */
  Class< ? > getType();

  /**
   * Narrow the reference to a reference of the given type. The resulting
   * reference is expected to <em>always</em> evaluate to an instance of
   * <code>type</code>.
   * <p/>
   * <p/>
   * In order to understand the effect of this operation, it is important to
   * understand that there may sometimes be some ambiguity in a reference. Take
   * for instance the {@link MultiReference}. Calling
   * {@link MultiReference#selectAttribute(String)} will create another
   * {@link MultiReference}, pointing to different properties of different types
   * on different types of objects. Calling {@link #narrow(Class)} will
   * essentially drop all references not pointing to an instance of
   * <code>type</code>.
   *
   * @param type
   *          Narrows the reference to a reference that is guaranteed to point
   *          to a subclass of <code>type</code>.
   * @return A reference that is guaranteed to point to a subclass of
   *         <code>type</code>, or <code>null</code> if it cannot be narrowed.
   */
  Reference<E> narrow(Class< ? > type);

  /**
   * Returns a boolean, indicating if the reference is based on the given
   * context.
   *
   * @param context
   *          the context
   * @return <code>true</code> if the outcome of the reference is based on the
   *         given context; <code>false</code> otherwise.
   */
  boolean isBasedOn(ReferenceContext<E> context);

  /**
   * Rescope.
   *
   * @param context
   *          the context
   * @return the reference
   */
  Reference<E> rescope(ReferenceContext<E> context);

}
