/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.reflect;

/**
 * The Class RuntimeInstantiationException.
 */
@SuppressWarnings("serial")
public class RuntimeInstantiationException extends ReflectionException {

  /**
   * Instantiates a new runtime instantiation exception.
   *
   * @param ie
   *          the ie
   */
  public RuntimeInstantiationException(InstantiationException ie) {
    super(ie);
  }

}
