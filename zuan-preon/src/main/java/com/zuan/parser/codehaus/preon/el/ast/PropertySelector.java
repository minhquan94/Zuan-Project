/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ast;

import com.zuan.parser.codehaus.preon.el.Reference;

/**
 * The Class PropertySelector.
 *
 * @author zuan_
 * @param <E>
 *          the element type
 */
public class PropertySelector<E> implements Selector<E> {

  /** The name. */
  private String name;

  /**
   * Instantiates a new property selector.
   *
   * @param name
   *          the name
   */
  public PropertySelector(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ast.Selector#select(com.virdict.
   * tool.parser.codehaus.preon.el.Reference)
   */
  @Override
  public Reference<E> select(Reference<E> ref) {
    return ref.selectAttribute(name);
  }

}
