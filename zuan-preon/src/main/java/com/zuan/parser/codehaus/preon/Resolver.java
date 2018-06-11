/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import com.zuan.parser.codehaus.preon.el.BindingException;

/**
 * A simple interface for resolving variable values. The interface is introduced
 * to have a flexible bridge between Limbo and Preon. With this interface, we
 * can still have different ways of retrieving values.
 */
public interface Resolver {

  /**
   * Returns the value for a named variable.
   *
   * @param name
   *          The name.
   * @return The value.
   * @throws BindingException
   *           If the name does not happen to be bound to a variable at runtime.
   */
  Object get(String name) throws BindingException;

  /**
   * Returns a reference to the original Resolver for an expression.
   *
   * @return The original {@link Resolver}.
   */
  Resolver getOriginalResolver();

}
