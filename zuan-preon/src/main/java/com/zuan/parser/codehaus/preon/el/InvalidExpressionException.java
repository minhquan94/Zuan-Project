/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

/**
 * The exception thrown when a String cannot be interpreted as a Limbo
 * expression.
 */
@SuppressWarnings("serial")
public class InvalidExpressionException extends RuntimeException {

  /**
   * Constructs a new exception, accepting the root cause.
   *
   * @param cause
   *          The root cause of the exception.
   */
  public InvalidExpressionException(Throwable cause) {
    super(cause);
  }

}
