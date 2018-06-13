/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ctx;

/**
 * The Interface VariableResolver.
 *
 * @author zuan_
 */
@FunctionalInterface
public interface VariableResolver {

  /**
   * Gets the.
   *
   * @param name
   *          the name
   * @return the object
   */
  Object get(String name);

}
