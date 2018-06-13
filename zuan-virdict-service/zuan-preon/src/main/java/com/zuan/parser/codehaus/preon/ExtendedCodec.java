/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import com.zuan.parser.codehaus.preon.channel.BitChannel;

/**
 * The Interface ExtendedCodec.
 *
 * @author zuan_
 * @param <T>
 *          the generic type
 */
public interface ExtendedCodec<T> extends Codec<T> {

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.Codec#encode(java.lang.Object,
   * com.zuan.parser.codehaus.preon.channel.BitChannel,
   * com.zuan.parser.codehaus.preon.Resolver)
   */
  @Override
  void encode(T object, BitChannel channel, Resolver resolver);

}
