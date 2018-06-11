/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import com.zuan.parser.codehaus.preon.el.ctx.ClassReferenceContext;

/**
 * The Enum Bindings.
 */
public enum Bindings implements Binding {

  /** The Late binding. */
  LATE_BINDING {

    @Override
    public <C> ReferenceContext<C> create(Class<C> type) {
      return new ClassReferenceContext<>(type);
    }

  },

  /** The Early binding. */
  EARLY_BINDING {
    @Override
    public <C> ReferenceContext<C> create(Class<C> type) {
      return new ClassReferenceContext<>(type);
    }

  };

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Binding#create(java.lang.Class)
   */
  @Override
  public abstract <C> ReferenceContext<C> create(Class<C> type);

}
