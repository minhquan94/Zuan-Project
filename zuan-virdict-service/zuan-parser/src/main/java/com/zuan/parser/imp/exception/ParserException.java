/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp.exception;

/**
 * The Class ParserException.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class ParserException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4560984757275643389L;

  /**
   * Instantiates a new parser exception.
   */
  public ParserException() {
    super();
  }

  /**
   * Instantiates a new parser exception.
   *
   * @param message
   *          the message
   * @param cause
   *          the cause
   * @param enableSuppression
   *          the enable suppression
   * @param writableStackTrace
   *          the writable stack trace
   */
  public ParserException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Instantiates a new parser exception.
   *
   * @param message
   *          the message
   * @param cause
   *          the cause
   */
  public ParserException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new parser exception.
   *
   * @param message
   *          the message
   */
  public ParserException(String message) {
    super(message);
  }

  /**
   * Instantiates a new parser exception.
   *
   * @param cause
   *          the cause
   */
  public ParserException(Throwable cause) {
    super(cause);
  }

}
