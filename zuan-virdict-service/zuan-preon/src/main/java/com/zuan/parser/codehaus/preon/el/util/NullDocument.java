/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.util;

import com.zuan.parser.codehaus.preon.el.Document;

/**
 * A null implementation of {@link Document}.
 */
public class NullDocument implements Document {

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Document#detail(java.lang.String)
   */
  @Override
  public Document detail(String text) {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Document#link(java.lang.Object,
   * java.lang.String)
   */
  @Override
  public void link(Object object, String text) {
    // do nothing
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.el.Document#text(java.lang.String)
   */
  @Override
  public void text(String text) {
    // do nothing
  }

}
