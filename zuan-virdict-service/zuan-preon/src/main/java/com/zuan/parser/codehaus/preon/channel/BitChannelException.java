/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.channel;

/**
 * The exception thrown when encountering an error while writing to a
 * {@link org.codehaus.preon.channel.BitChannel}.
 */
public class BitChannelException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3361728946520708111L;

  /**
   * Instantiates a new bit channel exception.
   *
   * @param message
   *          the message
   */
  public BitChannelException(String message) {
    super(message);
  }

}
