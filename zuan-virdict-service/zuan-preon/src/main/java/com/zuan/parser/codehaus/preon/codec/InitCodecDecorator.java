/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDecorator;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.Init;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.PassThroughCodecDescriptor2;
import com.zuan.parser.codehaus.preon.el.Expression;

/**
 * A decorator that will inspect all methods on the object constructed by the
 * {@link Codec} to be decorated, and create a decorated Codec that will invoke
 * a method after the object has been constructed, based on the {@link Init}
 * annotation.
 */
public class InitCodecDecorator implements CodecDecorator {

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.CodecDecorator#decorate(org.codehaus.preon.Codec,
   * java.lang.reflect.AnnotatedElement, java.lang.Class,
   * org.codehaus.preon.ResolverContext)
   */

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.codehaus.preon.CodecDecorator#decorate(com.zuan.parser.codehaus.preon.Codec,
   *      java.lang.reflect.AnnotatedElement, java.lang.Class,
   *      com.zuan.parser.codehaus.preon.ResolverContext)
   */
  @Override
  public <T> Codec<T> decorate(Codec<T> decorated, AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    for (Method method : type.getMethods()) {
      if (!Modifier.isStatic(method.getModifiers()) && method.getParameterTypes().length == 0
          && method.isAnnotationPresent(Init.class)) {
        method.setAccessible(true);
        return new InitCodec<>(decorated, method);
      }
    }
    return decorated;
  }

  /**
   * A {@link Codec}, calling the method annotated with the {@link Init}
   * annotation on the result, once all data of that result has been read.
   *
   * @param <T>
   *          the generic type
   */
  private class InitCodec<T> implements Codec<T> {

    /** The {@link Codec} producing the result. */
    private Codec<T> codec;

    /** The method to be called. */
    private Method method;

    /**
     * Constructs a new instance, accepting the {@link Codec} producing the
     * result, as well as the method to be invoked on the result once it has
     * been succesfully decoded.
     *
     * @param codec
     *          The {@link Codec} producing th result.
     * @param method
     *          The method to be called once it has been successfully decoded.
     */
    public InitCodec(Codec<T> codec, Method method) {
      this.codec = codec;
      this.method = method;
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
      T result = codec.decode(buffer, resolver, builder);
      if (result != null) {
        try {
          method.invoke(result);
        } catch (IllegalArgumentException e) {
          throw new DecodingException("Failed to invoke init method: " + e);
        } catch (IllegalAccessException e) {
          throw new DecodingException("Failed to invoke init method " + e);
        } catch (InvocationTargetException e) {
          throw new DecodingException("Failed to invoke init method " + e);
        }
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
    public void encode(T value, BitChannel channel, Resolver resolver) throws IOException {
      codec.encode(value, channel, resolver);
    }

    /**
     * Gets the types.
     *
     * @return the types
     */
    @Override
    public Class< ? >[] getTypes() {
      return codec.getTypes();
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return codec.getSize();
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class< ? > getType() {
      return codec.getType();
    }

    /**
     * Gets the codec descriptor.
     *
     * @return the codec descriptor
     */
    @Override
    public CodecDescriptor getCodecDescriptor() {
      return new PassThroughCodecDescriptor2(codec.getCodecDescriptor(), true);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + ((codec == null) ? 0 : codec.hashCode());
      return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      InitCodec other = (InitCodec) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (codec == null) {
        if (other.codec != null)
          return false;
      } else if (!codec.equals(other.codec))
        return false;
      return true;
    }

    /**
     * Gets the outer type.
     *
     * @return the outer type
     */
    private InitCodecDecorator getOuterType() {
      return InitCodecDecorator.this;
    }

  }

}
