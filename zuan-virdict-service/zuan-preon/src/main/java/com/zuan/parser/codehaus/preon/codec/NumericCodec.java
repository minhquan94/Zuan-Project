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
import com.zuan.parser.codehaus.preon.annotation.Bound;
import com.zuan.parser.codehaus.preon.annotation.BoundNumber;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.descriptor.NullDocumenter;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.util.Converters;
import com.zuan.parser.codehaus.preon.el.util.StringBuilderDocument;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * The {@link org.codehaus.preon.Codec} capable of decoding numeric types in a
 * sensible way.
 */
public class NumericCodec implements Codec<Object> {

  /** The numeric types. */
  static final Map<Class< ? >, NumericType> NUMERIC_TYPES = new HashMap<>(8);

  static {
    NumericCodec.NUMERIC_TYPES.put(Integer.class, NumericCodec.NumericType.INTEGER);
    NumericCodec.NUMERIC_TYPES.put(Long.class, NumericCodec.NumericType.LONG);
    NumericCodec.NUMERIC_TYPES.put(Short.class, NumericCodec.NumericType.SHORT);
    NumericCodec.NUMERIC_TYPES.put(Byte.class, NumericCodec.NumericType.BYTE);
    NumericCodec.NUMERIC_TYPES.put(int.class, NumericCodec.NumericType.INTEGER);
    NumericCodec.NUMERIC_TYPES.put(long.class, NumericCodec.NumericType.LONG);
    NumericCodec.NUMERIC_TYPES.put(short.class, NumericCodec.NumericType.SHORT);
    NumericCodec.NUMERIC_TYPES.put(byte.class, NumericCodec.NumericType.BYTE);
    NumericCodec.NUMERIC_TYPES.put(float.class, NumericCodec.NumericType.FLOAT);
    NumericCodec.NUMERIC_TYPES.put(Float.class, NumericCodec.NumericType.FLOAT);
    NumericCodec.NUMERIC_TYPES.put(double.class, NumericCodec.NumericType.DOUBLE);
    NumericCodec.NUMERIC_TYPES.put(Double.class, NumericCodec.NumericType.DOUBLE);
  }

  /** The size expr. */
  protected Expression<Integer, Resolver> sizeExpr;

  /** The byte order. */
  protected ByteOrder byteOrder;

  /** The type. */
  protected NumericType type;

  /** The match expr. */
  private Expression<Integer, Resolver> matchExpr;

  /**
   * Instantiates a new numeric codec.
   *
   * @param sizeExpr
   *          the size expr
   * @param byteOrder
   *          the byte order
   * @param type
   *          the type
   * @param matchExpr
   *          the match expr
   */
  public NumericCodec(Expression<Integer, Resolver> sizeExpr, ByteOrder byteOrder,
      NumericType type, Expression<Integer, Resolver> matchExpr) {
    this.sizeExpr = sizeExpr;
    this.byteOrder = byteOrder;
    this.type = type;
    this.matchExpr = matchExpr;
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
    int size = this.sizeExpr.eval(resolver).intValue();
    Object result = type.decode(buffer, size, byteOrder);
    if (matchExpr != null && !matchExpr.eval(resolver).equals(Converters.toInt(result))) {
      StringBuilder stringBuilder = new StringBuilder();
      Document document = new StringBuilderDocument(stringBuilder);
      if (matchExpr.isParameterized()) {
        stringBuilder.append("Expected different value than " + result);
      } else {
        stringBuilder.append("Expected ");
        matchExpr.document(document);
        stringBuilder.append(" but got ");
        stringBuilder.append(result);
      }
      throw new DecodingException(stringBuilder.toString());
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
  @Override
  public void encode(Object value, BitChannel channel, Resolver resolver) throws IOException {
    type.encode(channel, sizeExpr.eval(resolver), byteOrder, value);
  }

  /**
   * Gets the types.
   *
   * @return the types
   */
  @Override
  public Class< ? >[] getTypes() {
    return new Class[]{type.getType() };
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
    return type.getType();
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
        if (sizeExpr.isParameterized()) {
          return target -> target.para().text("The number of bits is ")
              .document(Documenters.forExpression(sizeExpr)).text(".").end();
        } else {
          return new NullDocumenter<>();
        }
      }

      @Override
      public String getTitle() {
        return null;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(final Adjective adjective,
          final boolean startWithCapital) {
        return target -> {
          if (sizeExpr.isParameterized()) {
            target.text(adjective.asTextPreferAn(startWithCapital)).text(" integer value (")
                .document(Documenters.forByteOrder(byteOrder)).text(")");
          } else {
            target.text(adjective.asTextPreferA(startWithCapital)).text(" ")
                .document(Documenters.forExpression(sizeExpr)).text("-bit integer value (")
                .document(Documenters.forByteOrder(byteOrder)).text(")");
          }
        };
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

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Codec of " + byteOrder + " " + type;
  }

  /**
   * The Enum NumericType.
   */
  public enum NumericType {

    /** The Float. */
    FLOAT {

      @Override
      public int getDefaultSize() {
        return 32;
      }

      @Override
      public Float decode(BitBuffer buffer, int size, ByteOrder endian) {
        int value = buffer.readAsInt(size, endian);
        return java.lang.Float.intBitsToFloat(value);
      }

      @Override
      public void encode(BitChannel channel, int size, ByteOrder endian, Object value) {
        throw new UnsupportedOperationException("Encoding not supported for floats.");
      }

      @Override
      public Class< ? > getType() {
        return Float.class;
      }

    },

    /** The Double. */
    DOUBLE {

      @Override
      public int getDefaultSize() {
        return 64;
      }

      @Override
      public Double decode(BitBuffer buffer, int size, ByteOrder endian) {
        return java.lang.Double.longBitsToDouble(buffer.readAsLong(size, endian));
      }

      @Override
      public void encode(BitChannel channel, int size, ByteOrder endian, Object value) {
        throw new UnsupportedOperationException("Encoding not supported for doubles.");
      }

      @Override
      public Class< ? > getType() {
        return Double.class;
      }

    },

    /** The Integer. */
    INTEGER {
      @Override
      public int getDefaultSize() {
        return 32;
      }

      @Override
      public Integer decode(BitBuffer buffer, int size, ByteOrder endian) {
        return buffer.readAsInt(size, endian);
      }

      @Override
      public void encode(BitChannel channel, int size, ByteOrder endian, Object value)
          throws IOException {
        channel.write(size, (Integer) value, endian);
      }

      @Override
      public Class< ? > getType() {
        return Integer.class;
      }
    },

    /** The Long. */
    LONG {
      @Override
      public int getDefaultSize() {
        return 64;
      }

      @Override
      public Long decode(BitBuffer buffer, int size, ByteOrder endian) {
        return buffer.readAsLong(size, endian);
      }

      @Override
      public void encode(BitChannel channel, int size, ByteOrder endian, Object value)
          throws IOException {
        channel.write(size, (Long) value, endian);
      }

      @Override
      public Class< ? > getType() {
        return Long.class;
      }
    },

    /** The Short. */
    SHORT {
      @Override
      public int getDefaultSize() {
        return 16;
      }

      @Override
      public Short decode(BitBuffer buffer, int size, ByteOrder endian) {
        return buffer.readAsShort(size, endian);
      }

      @Override
      public void encode(BitChannel channel, int size, ByteOrder endian, Object value)
          throws IOException {
        channel.write(size, (Short) value, endian);
      }

      @Override
      public Class< ? > getType() {
        return Short.class;
      }
    },

    /** The Byte. */
    BYTE {
      @Override
      public int getDefaultSize() {
        return 8;
      }

      @Override
      public Byte decode(BitBuffer buffer, int size, ByteOrder endian) {
        return buffer.readAsByte(size, endian);
      }

      @Override
      public void encode(BitChannel channel, int size, ByteOrder endian, Object value)
          throws IOException {
        channel.write(size, (Byte) value);
      }

      @Override
      public Class< ? > getType() {
        return Byte.class;
      }
    };

    /**
     * Gets the default size.
     *
     * @return the default size
     */
    public abstract int getDefaultSize();

    /**
     * Decode.
     *
     * @param buffer
     *          the buffer
     * @param size
     *          the size
     * @param endian
     *          the endian
     * @return the object
     */
    public abstract Object decode(BitBuffer buffer, int size, ByteOrder endian);

    /**
     * Encode.
     *
     * @param channel
     *          the channel
     * @param size
     *          the size
     * @param endian
     *          the endian
     * @param value
     *          the value
     * @throws IOException
     *           Signals that an I/O exception has occurred.
     */
    public abstract void encode(BitChannel channel, int size, ByteOrder endian, Object value)
        throws IOException;

    /**
     * Gets the type.
     *
     * @return the type
     */
    public abstract Class< ? > getType();

  }

  /**
   * A {@link org.codehaus.preon.CodecFactory} generating
   * {@link org.codehaus.preon.Codec Codecs} capable of decoding numbers from
   * the {@link org.codehaus.preon.buffer.BitBuffer}. Note that the
   * {@link org.codehaus.preon.Codec Codecs} created by this class are capable
   * to decode Longs, Integers, Shorts, Bytes, longs, ints, shorts and bytes.
   *
   * @author Wilfred Springer
   */
  public static class Factory implements CodecFactory {

    /**
     * Creates the.
     *
     * @param <T>
     *          the generic type
     * @param overrides
     *          the overrides
     * @param type
     *          the type
     * @param context
     *          the context
     * @return the codec
     */
    @Override
    @SuppressWarnings({"unchecked" })
    public <T> Codec<T> create(AnnotatedElement overrides, Class<T> type,
        ResolverContext context) {

      Class< ? > actualType = resolveActualType(overrides, type);
      if (NUMERIC_TYPES.keySet().contains(actualType)) {
        NumericType numericType = NUMERIC_TYPES.get(actualType);
        if (overrides == null || overrides.isAnnotationPresent(Bound.class)) {
          ByteOrder endian = ByteOrder.LITTLE_ENDIAN;
          int size = numericType.getDefaultSize();
          Expression<Integer, Resolver> sizeExpr =
              Expressions.createInteger(context, Integer.toString(size));
          return (Codec<T>) new NumericCodec(sizeExpr, endian, numericType, null);
        }
        if (overrides.isAnnotationPresent(BoundNumber.class)) {
          BoundNumber numericMetadata = overrides.getAnnotation(BoundNumber.class);
          ByteOrder endian = numericMetadata.byteOrder();
          String size = numericMetadata.size();

          if (size.length() == 0) {
            size = Integer.toString(numericType.getDefaultSize());
          }
          Expression<Integer, Resolver> sizeExpr = Expressions.createInteger(context, size);
          Expression<Integer, Resolver> matchExpr = null;
          if (numericMetadata.match().trim().length() != 0) {
            matchExpr = Expressions.createInteger(context, numericMetadata.match());
          }
          return (Codec<T>) new NumericCodec(sizeExpr, endian, numericType, matchExpr);
        }
      }
      return null;
    }

    /**
     * Resolve actual type.
     *
     * @param overrides
     *          the overrides
     * @param type
     *          the type
     * @return the class
     */
    public Class< ? > resolveActualType(AnnotatedElement overrides, Class< ? > type) {
      if (overrides != null && overrides.isAnnotationPresent(BoundNumber.class)) {
        BoundNumber numericMetadata = overrides.getAnnotation(BoundNumber.class);
        Class< ? > typeOverride = numericMetadata.type();
        if (typeOverride != Number.class) {
          return typeOverride;
        }
      }
      return type;
    }

  }
}
