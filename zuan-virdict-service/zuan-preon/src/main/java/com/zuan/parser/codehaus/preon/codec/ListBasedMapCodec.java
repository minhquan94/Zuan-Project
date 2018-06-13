/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.el.Expression;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * Created by IntelliJ IDEA. User: wilfred Date: Jun 20, 2010 Time: 3:49:00 AM
 * To change this template use File | Settings | File Templates.
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class ListBasedMapCodec<K, V> implements Codec<Map<K, V>> {

  /** The list codec. */
  private final Codec<List<Map.Entry<K, V>>> listCodec;

  /**
   * Instantiates a new list based map codec.
   *
   * @param listCodec
   *          the list codec
   */
  public ListBasedMapCodec(Codec<List<Map.Entry<K, V>>> listCodec) {
    this.listCodec = listCodec;
  }

  /**
   * Decode.
   *
   * @param buffer
   *          the buffer
   * @param resolver
   *          the resolver
   * @param builder
   *          the builder
   * @return the map
   * @throws DecodingException
   *           the decoding exception
   */
  @Override
  public Map<K, V> decode(BitBuffer buffer, Resolver resolver, Builder builder)
      throws DecodingException {
    List< ? extends Map.Entry<K, V>> entries = listCodec.decode(buffer, resolver, builder);
    Map<K, V> result = new HashMap<>(entries.size());
    for (Map.Entry<K, V> entry : entries) {
      result.put(entry.getKey(), entry.getValue());
    }
    return result;
  }

  /**
   * Encode.
   *
   * @param value
   *          the value
   * @param channel
   *          the channel
   * @param resolver
   *          the resolver
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void encode(Map value, BitChannel channel, Resolver resolver) throws IOException {
    throw new UnsupportedOperationException();
  }

  /**
   * Gets the size.
   *
   * @return the size
   */
  @Override
  public Expression<Integer, Resolver> getSize() {
    return listCodec.getSize();
  }

  /**
   * Gets the codec descriptor.
   *
   * @return the codec descriptor
   */
  @Override
  public CodecDescriptor getCodecDescriptor() {
    return new CodecDescriptor() {

      @Override
      public <C extends ParaContents< ? >> Documenter<C> summary() {
        return target -> target.document(reference(Adjective.A, true)).text(".");
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(final Adjective adjective,
          final boolean startWithCapital) {
        return target -> target.text(adjective.asTextPreferA(startWithCapital))
            .text("map produced from a ")
            .document(listCodec.getCodecDescriptor().reference(Adjective.NONE, false));
      }

      @Override
      public <C extends SimpleContents< ? >> Documenter<C> details(String bufferReference) {
        return listCodec.getCodecDescriptor().details(bufferReference);
      }

      @Override
      public boolean requiresDedicatedSection() {
        return false;
      }

      @Override
      public String getTitle() {
        return null;
      }
    };
  }

  /**
   * Gets the types.
   *
   * @return the types
   */
  @Override
  public Class< ? >[] getTypes() {
    return new Class< ? >[]{List.class };
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public Class< ? > getType() {
    return List.class;
  }
}
