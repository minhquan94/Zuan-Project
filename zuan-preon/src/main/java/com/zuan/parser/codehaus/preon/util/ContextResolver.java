/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

/**
 * The Interface ContextResolver.
 *
 * @author zuan_
 * @param <T>
 *          the generic type
 */
@FunctionalInterface
public interface ContextResolver<T> {

  /**
   * Gets the context.
   *
   * @return the context
   */
  T getContext();

}
