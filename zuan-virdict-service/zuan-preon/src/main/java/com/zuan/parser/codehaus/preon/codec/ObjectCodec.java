/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.util.List;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.binding.Binding;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.ObjectResolverContext;
import com.zuan.parser.codehaus.preon.rendering.IdentifierRewriter;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;
import nl.flotsam.pecia.Table3Cols;

/**
 * <p>
 * The {@link Codec} capable of decoding instances of arbitrary classes.
 * Typicaly, this {@link Codec} will be constructed using the
 * {@link ObjectCodecFactory} companion class that's embedded in the definition
 * of this class. If you do so, then the bindings will be based on the presence
 * of annotations on the fields of the class for which you need a {@link Codec}.
 * </p>
 *
 * @param <T>
 *          the generic type
 */
public class ObjectCodec<T> implements Codec<T> {

  /** The type. */
  private final Class<T> type;

  /** The rewriter. */
  private final IdentifierRewriter rewriter;

  /** The context. */
  private final ObjectResolverContext context;

  /**
   * Instantiates a new object codec.
   *
   * @param type
   *          the type
   * @param rewriter
   *          the rewriter
   * @param context
   *          the context
   */
  public ObjectCodec(Class<T> type, IdentifierRewriter rewriter,
      ObjectResolverContext context) {
    assert type != null;
    assert rewriter != null;
    assert context != null;
    this.type = type;
    this.rewriter = rewriter;
    this.context = context;
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
    assert buffer != null;
    assert builder != null;
    try {
      final T result = builder.create(type);
      resolver = context.getResolver(result, resolver);
      for (Binding binding : context.getBindings()) {
        binding.load(result, buffer, resolver, builder);
      }
      return result;
    } catch (InstantiationException ie) {
      ie.printStackTrace();
      throw new DecodingException(type, ie);
    } catch (IllegalAccessException iae) {
      throw new DecodingException(iae);
    }
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
    resolver = context.getResolver(value, resolver);
    for (Binding binding : context.getBindings()) {
      binding.save(value, channel, resolver);
    }
  }

  /**
   * Gets the types.
   *
   * @return the types
   */
  @Override
  public Class< ? >[] getTypes() {
    return new Class[]{type };
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Codec#getSize()
   */

  /**
   * Gets the size.
   *
   * @return the size
   */
  @Override
  public Expression<Integer, Resolver> getSize() {
    List<Binding> bindings = context.getBindings();
    if (bindings != null && !bindings.isEmpty()) {
      Expression<Integer, Resolver> result = null;
      for (Binding binding : bindings) {
        if (result == null) {
          result = binding.getSize();
        } else {
          result = Expressions.add(result, binding.getSize());
        }
      }
      return result;
    } else {
      return Expressions.createInteger(0, Resolver.class);
    }
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Codec of " + type.getSimpleName();
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public Class< ? > getType() {
    return type;
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
        return target -> {
          target.para().document(reference(Adjective.THE, false))
              .text(" is composed out of several other smaller elements.")
              .text(" The table below provides an overview.").end();
          Table3Cols< ? > table3Cols = target.table3Cols();
          table3Cols = table3Cols.header().entry().para().text("Name").end().entry().para()
              .text("Description").end().entry().para().text("Size (in bits)").end().end();
          for (Binding binding : ObjectCodec.this.context.getBindings()) {
            table3Cols.row().entry().para()
                .document(Documenters.forBindingName(binding, rewriter)).end().entry()
                .document(Documenters.forBindingDescription(binding)).entry().para()
                .document(Documenters.forBits(binding.getSize())).end().end();
          }
          table3Cols.end();
        };
      }

      @Override
      public String getTitle() {
        return rewriter.rewrite(type.getName());
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(Adjective adjective,
          boolean startWithCapital) {
        return target -> target.link(getTitle(), getTitle());
      }

      @Override
      public boolean requiresDedicatedSection() {
        return true;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> summary() {
        return target -> target.text("A ").link(getTitle(), getTitle());
      }

    };
  }

}
