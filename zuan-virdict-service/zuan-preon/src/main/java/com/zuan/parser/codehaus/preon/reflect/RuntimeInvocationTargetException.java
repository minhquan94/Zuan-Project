/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * The Class RuntimeInvocationTargetException.
 *
 * @author zuan_
 */
@SuppressWarnings("serial")
public class RuntimeInvocationTargetException extends ReflectionException {

  /**
   * Instantiates a new runtime invocation target exception.
   *
   * @param ite
   *          the ite
   */
  public RuntimeInvocationTargetException(InvocationTargetException ite) {
    super(ite);
  }

}
