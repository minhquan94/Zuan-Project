/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

/**
 * The default implementation of the {@link Builder} interface, simply invoking
 * {@link Class#newInstance()} on the class passed in.
 */
public class DefaultBuilder implements Builder {

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Builder#create(java.lang.Class)
   */

  @Override
  public <T> T create(Class<T> type) throws InstantiationException, IllegalAccessException {
    return type.newInstance();
  }

}
