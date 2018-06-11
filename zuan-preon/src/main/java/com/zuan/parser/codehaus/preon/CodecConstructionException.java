/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.InvalidExpressionException;

/**
 * The exception thrown when a {@link CodecFactory} fails to construct a
 * {@link Codec}. See different constructors for the situations in which this
 * exception might be thrown.
 */
@SuppressWarnings("serial")
public class CodecConstructionException extends RuntimeException {

  /**
   * Constructs an exception for the case in which the {@link CodecFactory}
   * fails to parse the expression.
   *
   * @param ice
   *          The exception containing the details about the reason why it
   *          turned out to be impossible to build an interpreter for an
   *          expression.
   */
  public CodecConstructionException(InvalidExpressionException ice) {
    super("Failed to construct codec.", ice);
  }

  /**
   * Constructs an exception for the case in which the {@link CodecFactory}
   * fails to bind the expression to the context.
   *
   * @param be
   *          the be
   */
  public CodecConstructionException(BindingException be) {
    super("Failed to construct codec.", be);
  }

  /**
   * Instantiates a new codec construction exception.
   *
   * @param message
   *          the message
   */
  public CodecConstructionException(String message) {
    super(message);
  }

}
