/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

/**
 * A context for building references to elements in an environment of E. The
 * {@link ReferenceContext} interface allows us to build references relying on
 * different types of environments.
 * <p>
 * The main reason for having the {@link ReferenceContext} interface is to have
 * a better abstraction from the environment in which a Limbo expression is
 * executed. With these abstractions, the component <em>using</em> Limbo in a
 * certain context is still able to define the meaning of the selectors for that
 * particular context. This allows Quarks to build meaningful descriptions of
 * references by taking Codec information into account.
 * </p>
 *
 * @param <E>
 *          The type of environment that needs to be passed in in order to
 *          resolve a {@link Reference} created from this
 *          {@link ReferenceContext}.
 */
public interface ReferenceContext<E> extends Descriptive {

  /**
   * Creates a new {@link Reference}; the new {@link Reference} will be resolved
   * by first resolving the current reference, and then use the name to navigate
   * to another object associated to that object.
   *
   * @param name
   *          The name to be used to resolve the Reference.
   * @return A new Reference, pointing to the object that is related to the
   *         original object by the given name.
   * @throws BindingException
   *           the binding exception
   */
  Reference<E> selectAttribute(String name) throws BindingException;

  /**
   * Creates a new {@link Reference}; the new {@link Reference} will be resolved
   * by first resolving the current reference, and then use the index to
   * navigate to the associated object.
   *
   * @param index
   *          The index to be used. (A Limbo expression.)
   * @return A new Reference, pointing to the object that is related to the
   *         original object by the given index.
   * @throws BindingException
   *           the binding exception
   */
  Reference<E> selectItem(String index) throws BindingException;

  /**
   * Creates a new {@link Reference}; the new {@link Reference} will be resolved
   * by first resolving the current reference, and then use the index to
   * navigate to the associated object.
   *
   * @param index
   *          The index to be used. (An object representation of an Expression
   *          on the same context.)
   * @return A new Reference, pointing to the object that is related to the
   *         original object by the given index.
   * @throws BindingException
   *           the binding exception
   */
  Reference<E> selectItem(Expression<Integer, E> index) throws BindingException;

}
