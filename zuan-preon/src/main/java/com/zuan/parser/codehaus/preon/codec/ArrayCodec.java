/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.lang.reflect.Array;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * The {@link org.codehaus.preon.Codec} for reading the {@link java.util.List}
 * and its members, on demand. Instances of this class will <em>not</em> create
 * a standard {@link java.util.List} implementation and populate all of its data
 * immediately. Instead it will create a
 * {@link org.codehaus.preon.util.EvenlyDistributedLazyList}, constructing its
 * elements on the fly, only when it is required.
 */
class ArrayCodec implements Codec<Object> {

  /** The number of elements in the list. */
  private Expression<Integer, Resolver> size;

  /**
   * The {@link org.codehaus.preon.Codec} that will construct elements from the
   * {@link java.util.List}.
   */
  private Codec<Object> codec;

  /** The type of element to be constructed. */
  private Class< ? > type;

  /**
   * Constructs a new instance.
   *
   * @param expr
   *          An {@link org.codehaus.preon.el.Expression} representing the
   *          number of elements in the {@link java.util.List}.
   * @param codec
   *          The {@link org.codehaus.preon.Codec} constructing elements in the
   *          {@link java.util.List}.
   * @param type
   *          the type
   */
  public ArrayCodec(Expression<Integer, Resolver> expr, Codec<Object> codec, Class< ? > type) {
    this.size = expr;
    this.codec = codec;
    this.type = type;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Codec#decode(org.codehaus.preon.buffer.BitBuffer,
   * org.codehaus.preon.Resolver, org.codehaus.preon.Builder)
   */

  @Override
  public Object decode(BitBuffer buffer, Resolver resolver, Builder builder)
      throws DecodingException {
    int length = size.eval(resolver).intValue();
    Object result = Array.newInstance(type.getComponentType(), length);
    for (int i = 0; i < length; i++) {
      Object value = codec.decode(buffer, resolver, builder);
      Array.set(result, i, value);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.Codec#encode(java.lang.Object,
   * com.zuan.parser.codehaus.preon.channel.BitChannel,
   * com.zuan.parser.codehaus.preon.Resolver)
   */
  @Override
  public void encode(Object object, BitChannel channel, Resolver resolver) throws IOException {
    int numberOfElements = size.eval(resolver);
    for (int i = 0; i < numberOfElements; i++) {
      codec.encode(Array.get(object, i), channel, resolver);
    }
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Codec#getTypes()
   */

  @Override
  public Class< ? >[] getTypes() {
    return codec.getTypes();
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Codec#getSize()
   */

  @Override
  public Expression<Integer, Resolver> getSize() {
    return Expressions.multiply(size, codec.getSize());
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.Codec#getType()
   */
  @Override
  public Class< ? > getType() {
    return type;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.Codec#getCodecDescriptor()
   */
  @Override
  public CodecDescriptor getCodecDescriptor() {
    return new CodecDescriptor() {

      @Override
      public <C extends SimpleContents< ? >> Documenter<C> details(
          final String bufferReference) {
        return target -> {
          if (size != null) {
            target.para().text("The number of elements in the list ").text("is ")
                .document(Documenters.forExpression(ArrayCodec.this.size)).text(".").end();
          }
          if (!codec.getCodecDescriptor().requiresDedicatedSection()) {
            target.document(codec.getCodecDescriptor().details(bufferReference));
          }
        };
      }

      @Override
      public String getTitle() {
        return null;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(final Adjective adjective,
          final boolean startWithCapital) {
        return target -> target.text(adjective.asTextPreferA(startWithCapital))
            .text("list of ")
            .document(codec.getCodecDescriptor().reference(Adjective.NONE, false))
            .text(" elements");
      }

      @Override
      public boolean requiresDedicatedSection() {
        return false;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> summary() {
        return target -> {
          target.document(reference(Adjective.A, true));
          target.text(".");
        };
      }

    };
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Codec of array, decoding elements using " + codec;
  }
}
