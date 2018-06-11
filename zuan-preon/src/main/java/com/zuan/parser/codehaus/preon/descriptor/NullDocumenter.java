/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.descriptor;

import nl.flotsam.pecia.Documenter;

/**
 * The Class NullDocumenter.
 *
 * @author zuan_
 * @param <C>
 *          the generic type
 */
public class NullDocumenter<C> implements Documenter<C> {

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.Documenter#document(java.lang.Object)
   */
  @Override
  public void document(C target) {
    // Do nothing
  }

}
