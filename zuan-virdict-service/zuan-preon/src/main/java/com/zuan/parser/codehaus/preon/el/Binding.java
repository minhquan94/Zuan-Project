/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

/**
 * The interface implemented by objects capable of creating a
 * {@link ReferenceContext} from a class passed in. The {@link ReferenceContext}
 * built will allow clients to access <em>attributes</em> and <em>items</em>
 * from instances of that class.
 * <p>
 * <em>Note:</em> this does not necessarily mean accessing bean properties or
 * fields. In fact, the {@link ReferenceContext} actually allows you to abstract
 * from <em>any</em> state associated to the instance.
 * </p>
 */
@FunctionalInterface
public interface Binding {

  /**
   * Returns a {@link ReferenceContext} exposing data of the class.
   *
   * @param <C>
   *          The type parameter of the {@link ReferenceContext} returned.
   * @param type
   *          The type of context to which the {@link ReferenceContext} applies.
   * @return A {@link ReferenceContext} providing a structured interface to data
   *         associated to instances of that class.
   */
  <C> ReferenceContext<C> create(Class<C> type);

}
