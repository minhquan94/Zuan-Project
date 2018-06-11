/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDecorator;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.ByteAlign;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.el.Expression;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A {@link CodecDecorator} that will make sure that reading stops at a
 * byte-aligned position.
 */
public class ByteAligningDecorator implements CodecDecorator {

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDecorator#decorate(com.virdict.
   * tool.parser.codehaus.preon.Codec, java.lang.reflect.AnnotatedElement,
   * java.lang.Class, com.zuan.parser.codehaus.preon.ResolverContext)
   */
  @Override
  public <T> Codec<T> decorate(Codec<T> decorated, AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    if (type.isAnnotationPresent(ByteAlign.class)
        || (metadata != null && metadata.isAnnotationPresent(ByteAlign.class))) {
      return new ByteAligningCodec<>(decorated);
    } else {
      return decorated;
    }
  }

  /**
   * The Class ByteAligningCodec.
   *
   * @param <T>
   *          the generic type
   */
  private static class ByteAligningCodec<T> implements Codec<T> {

    /** The decorated. */
    private Codec<T> decorated;

    /**
     * Instantiates a new byte aligning codec.
     *
     * @param decorated
     *          the decorated
     */
    public ByteAligningCodec(Codec<T> decorated) {
      this.decorated = decorated;
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
     * @return the t
     * @throws DecodingException
     *           the decoding exception
     */
    @Override
    public T decode(BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      T result = decorated.decode(buffer, resolver, builder);
      long pos = buffer.getBitPos() % 8;
      if (pos > 0) {
        buffer.setBitPos(buffer.getBitPos() + 8 - pos);
      }
      return result;
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
    public void encode(T object, BitChannel channel, Resolver resolver) throws IOException {
      int bits = 8 - channel.getRelativeBitPos();
      if (bits != 8) {
        channel.write(bits, (byte) 0);
      }
      decorated.encode(object, channel, resolver);
    }

    /**
     * Gets the types.
     *
     * @return the types
     */
    @Override
    public Class< ? >[] getTypes() {
      return decorated.getTypes();
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return null;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class< ? > getType() {
      return decorated.getType();
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
          return target -> target.para().text("If - after reading ")
              .document(decorated.getCodecDescriptor().reference(Adjective.THE, false))
              .text(" - the pointer is ").emphasis("not")
              .text(" at the end of the byte, then the last couple of bits will be skipped.")
              .end();
        }

        @Override
        public String getTitle() {
          return null;
        }

        @Override
        public <C extends ParaContents< ? >> Documenter<C> reference(Adjective adjective,
            boolean startWithCapital) {
          return decorated.getCodecDescriptor().reference(adjective, false);
        }

        @Override
        public boolean requiresDedicatedSection() {
          return false;
        }

        @Override
        public <C extends ParaContents< ? >> Documenter<C> summary() {
          return decorated.getCodecDescriptor().summary();
        }

      };
    }

  }

}
