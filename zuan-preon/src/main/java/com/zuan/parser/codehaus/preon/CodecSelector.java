/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import java.io.IOException;
import java.util.Collection;

import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.el.Expression;

import nl.flotsam.pecia.ParaContents;

/**
 * The interface to be implemented by objects that have the capability to select
 * a {@link Codec} based on data on the buffer and a context for resolving
 * references.
 */
public interface CodecSelector {

  /**
   * Selects the {@link Codec} to be used for decoding, based on the bits on the
   * {@link BitBuffer} and the references that can be resolved using the
   * resolver.
   *
   * @param buffer
   *          The buffer providing the bits.
   * @param resolver
   *          The resolver for resolving references.
   * @return The {@link Codec} that needs to be used.
   * @throws DecodingException
   *           If we fail to select a {@link Codec} for the data found in the
   *           {@link BitBuffer}.
   */
  Codec< ? > select(BitBuffer buffer, Resolver resolver) throws DecodingException;

  <T> Codec< ? > select(Class<T> type, BitChannel channel, Resolver resolver)
      throws IOException;

  /**
   * Returns the collection of all choices this selector will have to choose
   * from.
   *
   * @return The <code>Collection</code> of all choices this selector will have
   *         to choose from.
   */
  Collection<Codec< ? >> getChoices();

  /**
   * Documents the procedure for deciding among a couple of {@link Codec}s.
   *
   * @param para
   *          The context for generating the content.
   */
  void document(ParaContents< ? > para);

  /**
   * Returns an expression representing the number of bits inhabited by the
   * actual selecting bit.
   */
  Expression<Integer, Resolver> getSize();

}
