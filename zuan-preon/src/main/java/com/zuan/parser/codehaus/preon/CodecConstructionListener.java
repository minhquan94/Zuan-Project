/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

/**
 * An interface implemented by some objects to notify listeners that new
 * {@link Codec Codecs} were created.
 *
 * @see CodecConstructionEvent
 */
@FunctionalInterface
public interface CodecConstructionListener {

  /**
   * Signals the creation of a new {@link Codec}.
   *
   * @param codec
   *          The new {@link Codec} constructed.
   */
  void constructed(Codec< ? > codec);

}
