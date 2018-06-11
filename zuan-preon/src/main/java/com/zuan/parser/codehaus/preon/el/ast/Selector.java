/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import com.zuan.parser.codehaus.preon.el.Reference;

/**
 * The Interface Selector.
 *
 * @param <E>
 *          the element type
 */
@FunctionalInterface
public interface Selector<E> {

  /**
   * Select.
   *
   * @param ref
   *          the ref
   * @return the reference
   */
  Reference<E> select(Reference<E> ref);

}
