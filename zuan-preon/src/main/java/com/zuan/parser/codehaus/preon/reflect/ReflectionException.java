/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.reflect;

/**
 * The Class ReflectionException.
 *
 * @author zuan_
 */
public abstract class ReflectionException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7190171626819011165L;

  /**
   * Instantiates a new reflection exception.
   *
   * @param cause
   *          the cause
   */
  public ReflectionException(Throwable cause) {
    super(cause);
  }

}
