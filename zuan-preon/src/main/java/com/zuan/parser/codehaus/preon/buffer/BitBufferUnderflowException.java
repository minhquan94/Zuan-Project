/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.buffer;

/**
 * The Class BitBufferUnderflowException.
 *
 * @author zuan_
 */
@SuppressWarnings("serial")
public class BitBufferUnderflowException extends BitBufferException {

  /**
   * Instantiates a new bit buffer underflow exception.
   *
   * @param pos
   *          the pos
   * @param bits
   *          the bits
   */
  public BitBufferUnderflowException(long pos, long bits) {
    super("Failed to read " + bits + " bits from position " + pos);
  }

}
