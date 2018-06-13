/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.BoundBuffer;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A factory for creating BoundBufferCodec objects.
 *
 * @author zuan_
 */
public class BoundBufferCodecFactory implements CodecFactory {

  /** The byte class. */
  private static final Class< ? > BYTE_CLASS = (new byte[0]).getClass();

  /**
   * Creates the.
   *
   * @param <T>
   *          the generic type
   * @param metadata
   *          the metadata
   * @param type
   *          the type
   * @param context
   *          the context
   * @return the codec
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    if (type.isArray() && BYTE_CLASS.equals(type)
        && metadata.isAnnotationPresent(BoundBuffer.class)) {
      return (Codec<T>) new BoundBufferCodec(
          metadata.getAnnotation(BoundBuffer.class).match());
    }
    return null;
  }

  /**
   * The Class BoundBufferCodec.
   */
  private static class BoundBufferCodec implements Codec<Object> {

    /** The criterion. */
    private byte[] criterion;

    /**
     * Instantiates a new bound buffer codec.
     *
     * @param matches
     *          the matches
     */
    public BoundBufferCodec(byte[] matches) {
      this.criterion = matches;
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
     * @return the object
     * @throws DecodingException
     *           the decoding exception
     */
    @Override
    public Object decode(BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      for (byte element : criterion) {
        if (element != buffer.readAsByte(8)) {
          throw new DecodingException(
              "First " + criterion.length + " bytes do not match expected value.");
        }
      }
      return criterion;
    }

    /**
     * Encode.
     *
     * @param object
     *          the object
     * @param channel
     *          the channel
     * @param resolver
     *          the resolver
     * @throws IOException
     *           Signals that an I/O exception has occurred.
     */
    @Override
    public void encode(Object object, BitChannel channel, Resolver resolver)
        throws IOException {
      channel.write(criterion, 0, criterion.length);
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
        public <C extends SimpleContents< ? >> Documenter<C> details(String bufferReference) {
          return target -> target.para()
              .text("The sequence is expected to match this hexidecimal sequence: ")
              .document(Documenters.forHexSequence(criterion)).text(".").end();
        }

        @Override
        public String getTitle() {
          return null;
        }

        @Override
        public <C extends ParaContents< ? >> Documenter<C> reference(final Adjective adjective,
            final boolean startWithCapital) {
          return target -> target.text(adjective.asTextPreferA(startWithCapital))
              .text(" sequence of bytes");
        }

        @Override
        public boolean requiresDedicatedSection() {
          return false;
        }

        @Override
        public <C extends ParaContents< ? >> Documenter<C> summary() {
          return target -> target.document(reference(Adjective.A, true)).text(".");
        }

      };
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return Expressions.createInteger(criterion.length * 8, Resolver.class);
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class< ? > getType() {
      return byte.class;
    }

    /**
     * Gets the types.
     *
     * @return the types
     */
    @Override
    public Class< ? >[] getTypes() {
      return new Class< ? >[]{byte.class };
    }
  }

}
