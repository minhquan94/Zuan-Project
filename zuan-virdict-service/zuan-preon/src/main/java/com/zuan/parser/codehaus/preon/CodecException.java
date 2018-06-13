/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

/**
 * The base exception class for any kind of problem that might occur
 * <em>while</em> encoding/decoding.
 */
@SuppressWarnings("serial")
public class CodecException extends Exception {

  /**
   * Constructs a new instance, accepting the cause of the problem.
   *
   * @param cause
   *          The cause of the problem.
   */
  public CodecException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new instance, accepting a message explaining the problem.
   *
   * @param message
   *          The message explaining the problem.
   */
  public CodecException(String message) {
    super(message);
  }

  /**
   * Constructs a new instance, accepting the cause of the problem.
   *
   * @param message
   *          A message explaining the problem.
   * @param cause
   *          The cause of the problem.
   */
  public CodecException(String message, Throwable cause) {
    super(message, cause);
  }

}
