/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.reflect;

/**
 * The Class RuntimeNoSuchFieldException.
 *
 * @author zuan_
 */
@SuppressWarnings("serial")
public class RuntimeNoSuchFieldException extends ReflectionException {

  /**
   * Instantiates a new runtime no such field exception.
   *
   * @param nse
   *          the nse
   */
  public RuntimeNoSuchFieldException(NoSuchFieldException nse) {
    super(nse);
  }

}
