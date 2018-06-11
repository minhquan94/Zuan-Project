/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

/**
 * The exception thrown when the expression cannot be bound to a context. (Note
 * that this exception might occur both at construction of the
 * {@link Expression} instance, or at the evaluation time.)
 */
@SuppressWarnings("serial")
public class BindingException extends RuntimeException {

  /**
   * Instantiates a new binding exception.
   *
   * @param message
   *          the message
   */
  public BindingException(String message) {
    super(message);
  }

  /**
   * Instantiates a new binding exception.
   *
   * @param message
   *          the message
   * @param cause
   *          the cause
   */
  public BindingException(String message, Throwable cause) {
    super(message, cause);
  }

}
