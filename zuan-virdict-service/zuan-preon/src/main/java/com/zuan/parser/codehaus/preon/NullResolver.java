/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import com.zuan.parser.codehaus.preon.el.BindingException;

/**
 * A <code>null</code> implementation of the {@link Resolver} interface.
 */
public class NullResolver implements Resolver {

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Resolver#get(java.lang.String)
   */

  @Override
  public Object get(String name) throws BindingException {
    throw new BindingException("Failed to resolve reference called " + name);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Resolver#getOriginalResolver()
   */

  @Override
  public Resolver getOriginalResolver() {
    return this;
  }

}
