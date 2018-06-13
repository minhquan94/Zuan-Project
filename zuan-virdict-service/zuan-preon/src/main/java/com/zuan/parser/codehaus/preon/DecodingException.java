/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import java.io.UnsupportedEncodingException;

import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.el.BindingException;

/**
 * The exception thrown when the {@link Codec} fails to decode a value from the
 * {@link BitBuffer}. See JavaDoc comments of the constructors for more
 * information on the typical circumstances causing this exception to be thrown.
 */
@SuppressWarnings("serial")
public class DecodingException extends CodecException {

  /**
   * Constructs an exception to be thrown when the {@link Codec} fails to
   * instantiate the value.
   *
   * @param ie
   *          the ie
   */
  public DecodingException(InstantiationException ie) {
    super(ie);
  }

  /**
   * Instantiates a new decoding exception.
   *
   * @param iae
   *          the iae
   */
  public DecodingException(IllegalAccessException iae) {
    super(iae);
  }

  /**
   * Instantiates a new decoding exception.
   *
   * @param be
   *          the be
   */
  public DecodingException(BindingException be) {
    super("Failed to decode data ", be);
  }

  /**
   * Instantiates a new decoding exception.
   *
   * @param uee
   *          the uee
   */
  public DecodingException(UnsupportedEncodingException uee) {
    super(uee);
  }

  /**
   * Instantiates a new decoding exception.
   *
   * @param ise
   *          the ise
   */
  public DecodingException(IllegalStateException ise) {
    super(ise);
  }

  /**
   * Instantiates a new decoding exception.
   *
   * @param message
   *          the message
   */
  public DecodingException(String message) {
    super(message);
  }

  /**
   * Instantiates a new decoding exception.
   *
   * @param type
   *          the type
   * @param ie
   *          the ie
   */
  public DecodingException(Class< ? > type, InstantiationException ie) {
    super("Failed to create instance of " + type.getSimpleName(), ie);
  }

}
