/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.BoundNumber;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.util.EnumUtils;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.Para;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * Created by IntelliJ IDEA. User: wilfred Date: Oct 24, 2009 Time: 5:03:58 PM
 * To change this template use File | Settings | File Templates.
 *
 * @param <T>
 *          the generic type
 */
public class EnumCodec<T> implements Codec<T> {

  /** The type. */
  private final Class<T> type;

  /** The mapping. */
  private final Map<Long, T> mapping;

  /** The inverse mapping. */
  private final Map<T, Long> inverseMapping;

  /** The size. */
  private final Expression<Integer, Resolver> size;

  /** The byte order. */
  private final ByteOrder byteOrder;

  /**
   * Instantiates a new enum codec.
   *
   * @param type
   *          the type
   * @param mapping
   *          the mapping
   * @param sizeExpr
   *          the size expr
   * @param endian
   *          the endian
   */
  public EnumCodec(Class<T> type, Map<Long, T> mapping, Expression<Integer, Resolver> sizeExpr,
      ByteOrder endian) {
    assert type != null;
    assert mapping != null;
    assert sizeExpr != null;
    assert endian != null;
    this.type = type;
    this.mapping = mapping;
    this.size = sizeExpr;
    this.byteOrder = endian;
    inverseMapping = new HashMap<>();
    for (Map.Entry<Long, T> entry : mapping.entrySet()) {
      inverseMapping.put(entry.getValue(), entry.getKey());
    }
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
    long value = buffer.readAsLong(size.eval(resolver), byteOrder);
    T result = mapping.get(value);
    if (result == null) {
      result = mapping.get(null);
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
    channel.write(size.eval(resolver), inverseMapping.get(object), byteOrder);
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

  /**
   * Gets the size.
   *
   * @return the size
   */
  @Override
  public Expression<Integer, Resolver> getSize() {
    return size;
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
          Para< ? > para = target.para();
          if (!size.isParameterized()) {
            para.text("The symbol is represented as a ")
                .document(Documenters.forNumericValue(size.eval(null), byteOrder)).text(".");
          } else {
            para.text("The symbol is represented as a numeric value (")
                .document(Documenters.forByteOrder(byteOrder)).text(". The number of bits is ")
                .document(Documenters.forExpression(size)).text(".");
          }
          para.text(" The numeric value corresponds to the following symbols:").end();
          for (Map.Entry<Long, T> entry : mapping.entrySet()) {
            if (entry.getKey() != null) {
              target.para().text(Long.toString(entry.getKey())).text(": ")
                  .text(entry.getValue().toString()).end();
            }
          }
          T defaultValue = mapping.get(null);
          if (defaultValue != null) {
            target.para().text("The default value is " + defaultValue.toString() + ".").end();
          }
        };
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(final Adjective adjective,
          boolean startWithCapital) {
        return target -> target.text(adjective.asTextPreferAn(false))
            .text("index of an enumeration");
      }

      @Override
      public boolean requiresDedicatedSection() {
        return false;
      }

      @Override
      public String getTitle() {
        return null;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> summary() {
        return target -> target
            .text("A value from a set of symbols, represented by a numeric value.");
      }

    };
  }

  /**
   * A {@link org.codehaus.preon.CodecFactory} creating
   * {@link org.codehaus.preon.Codec Codecs} capable of decoding enum values. At
   * this state, it will be triggered by enum type of fields with a
   * {@link org.codehaus.preon.annotation.BoundNumber} annotation, to pass
   * metadata on the number of bits (and endianness) from which the enum's value
   * needs to get constructed.
   *
   * @author Wilfred Springer
   */
  public static class Factory implements CodecFactory {

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
    @Override
    public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
        ResolverContext context) {
      if (type.isEnum() && metadata.isAnnotationPresent(BoundNumber.class)) {
        Map<Long, T> mapping = EnumUtils.getBoundEnumOptionIndex(type);
        BoundNumber settings = metadata.getAnnotation(BoundNumber.class);
        Expression<Integer, Resolver> sizeExpr =
            Expressions.createInteger(context, settings.size());
        return new EnumCodec<>(type, mapping, sizeExpr, settings.byteOrder());

      } else {
        return null;
      }
    }

  }
}
