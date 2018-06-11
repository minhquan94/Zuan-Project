/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.channel.BoundedBitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.el.Expression;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * {@link Codec} decoration, preventing the underlying {@link Codec} from being
 * able to read beyond a certain section of the
 * {@link org.codehaus.preon.buffer.BitBuffer} passed in.
 *
 * @param <T>
 *          The type of object expected to be returned by this {@link Codec}.
 */
class SlicingCodec<T> implements Codec<T> {

  /** The size expr. */
  private final Expression<Integer, Resolver> sizeExpr;

  /** The wrapped. */
  private final Codec<T> wrapped;

  /**
   * Constructs a new instance.
   *
   * @param wrapped
   *          The {@link Codec} to be wrapped.
   * @param sizeExpr
   *          The size of the slice, expressed in bits, as a Limbo expression.
   */
  public SlicingCodec(Codec<T> wrapped, Expression<Integer, Resolver> sizeExpr) {
    this.sizeExpr = sizeExpr;
    this.wrapped = wrapped;
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
    BitBuffer slice = buffer.slice(sizeExpr.eval(resolver));
    return wrapped.decode(slice, resolver, builder);
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
  @Override
  public void encode(T value, BitChannel channel, Resolver resolver) throws IOException {
    wrapped.encode(value, new BoundedBitChannel(channel, sizeExpr.eval(resolver)), resolver);
  }

  /**
   * Gets the types.
   *
   * @return the types
   */
  @Override
  public Class< ? >[] getTypes() {
    return wrapped.getTypes();
  }

  /**
   * Gets the size.
   *
   * @return the size
   */
  @Override
  public Expression<Integer, Resolver> getSize() {
    return sizeExpr;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public Class< ? > getType() {
    return wrapped.getType();
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
      public <C extends SimpleContents< ? >> Documenter<C> details(
          final String bufferReference) {
        return target -> {
          target.para().text("The format reserves only ")
              .document(Documenters.forExpression(sizeExpr)).text(" bits for ")
              .document(wrapped.getCodecDescriptor().reference(Adjective.THE, false)).end();
          target.document(wrapped.getCodecDescriptor().details(bufferReference));
        };
      }

      @Override
      public String getTitle() {
        return null;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(Adjective adjective,
          boolean startWithCapital) {
        return wrapped.getCodecDescriptor().reference(adjective, false);
      }

      @Override
      public boolean requiresDedicatedSection() {
        return false;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> summary() {
        return wrapped.getCodecDescriptor().summary();
      }

    };
  }
}
