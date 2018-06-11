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
import com.zuan.parser.codehaus.preon.annotation.Bound;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A {@link CodecFactory} capable of creating {@link Codec Codecs} that deal
 * with booleans.
 */
public class BooleanCodecFactory implements CodecFactory {

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.CodecFactory#create(java.lang.reflect.AnnotatedElement,
   * java.lang.Class, org.codehaus.preon.ResolverContext)
   */

  @Override
  @SuppressWarnings("unchecked")
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    if (metadata == null || metadata.isAnnotationPresent(Bound.class)) {
      if (boolean.class.equals(type)) {
        return (Codec<T>) new BooleanCodec(true);
      } else if (Boolean.class.equals(type)) {
        return (Codec<T>) new BooleanCodec(false);
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  /**
   * The Class BooleanCodec.
   */
  private static class BooleanCodec implements Codec<Boolean> {

    /** The primitive. */
    private boolean primitive;

    /**
     * Instantiates a new boolean codec.
     *
     * @param primitive
     *          the primitive
     */
    public BooleanCodec(boolean primitive) {
      this.primitive = primitive;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#decode(com.zuan.parser.
     * codehaus.preon.buffer.BitBuffer, com.zuan.parser.codehaus.preon.Resolver,
     * com.zuan.parser.codehaus.preon.Builder)
     */
    @Override
    public Boolean decode(BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      return buffer.readAsBoolean();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#encode(java.lang.Object,
     * com.zuan.parser.codehaus.preon.channel.BitChannel,
     * com.zuan.parser.codehaus.preon.Resolver)
     */
    @Override
    public void encode(Boolean value, BitChannel channel, Resolver resolver)
        throws IOException {
      channel.write(value);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#getCodecDescriptor()
     */
    @Override
    public CodecDescriptor getCodecDescriptor() {
      return new CodecDescriptor() {

        @Override
        public <T extends SimpleContents< ? >> Documenter<T> details(String bufferReference) {
          return target -> {
          };
        }

        @Override
        public String getTitle() {
          return null;
        }

        @Override
        public <T extends ParaContents< ? >> Documenter<T> reference(final Adjective adjective,
            boolean startWithCapital) {
          return target -> {
            target.text(adjective == Adjective.A ? "a " : "the ");
            target.text("boolean value");
          };
        }

        @Override
        public boolean requiresDedicatedSection() {
          return false;
        }

        @Override
        public <T extends ParaContents< ? >> Documenter<T> summary() {
          return target -> {
            target.text("A one-bit representation of a boolean value: ");
            target.text("1 = true; 0 = false.");
          };
        }

      };
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#getTypes()
     */
    @Override
    public Class< ? >[] getTypes() {
      if (primitive) {
        return new Class[]{boolean.class };
      } else {
        return new Class[]{Boolean.class };
      }
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#getSize()
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return Expressions.createInteger(1, Resolver.class);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#getType()
     */
    @Override
    public Class< ? > getType() {
      return Boolean.class;
    }

  }

}
