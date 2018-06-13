/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Reference;

/**
 * The Class IndexSelector.
 *
 * @author zuan_
 * @param <E>
 *          the element type
 */
public class IndexSelector<E> implements Selector<E> {

  /** The index. */
  private Expression<Integer, E> index;

  /**
   * Instantiates a new index selector.
   *
   * @param index
   *          the index
   */
  public IndexSelector(Expression<Integer, E> index) {
    this.index = index;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Selector#select(com.virdict.
   * tool.parser.codehaus.preon.el.Reference)
   */
  @Override
  public Reference<E> select(Reference<E> ref) {
    return ref.selectItem(index);
  }

}
