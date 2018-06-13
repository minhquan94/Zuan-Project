/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.reflect;

/**
 * The Class RuntimeIllegalAccessException.
 *
 * @author zuan_
 */
@SuppressWarnings("serial")
public class RuntimeIllegalAccessException extends ReflectionException {

  /**
   * Instantiates a new runtime illegal access exception.
   *
   * @param iae
   *          the iae
   */
  public RuntimeIllegalAccessException(IllegalAccessException iae) {
    super(iae);
  }

}
