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
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.LazyLoading;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.PassThroughCodecDescriptor2;
import com.zuan.parser.codehaus.preon.el.Expression;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * An attempt to create a general purpose {@link CodecFactory} whose
 * {@link Codec Codecs} will only load their data once operations are invoked
 * upon them.
 * <p/>
 * <p>
 * Note that there are some concurrency issues with this approach. The
 * {@link Codec} will create a proxy. When an operation is invoked on that
 * proxy, the proxy will check if it already obtained the actual value. This is
 * where it's getting nasty. Multiple threads might come in simultaneously.
 * </p>
 */
public class LazyLoadingCodecDecorator implements CodecDecorator {

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDecorator#decorate(com.virdict.
   * tool.parser.codehaus.preon.Codec, java.lang.reflect.AnnotatedElement,
   * java.lang.Class, com.zuan.parser.codehaus.preon.ResolverContext)
   */
  @Override
  public <T> Codec<T> decorate(Codec<T> decorated, AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    if (metadata != null && metadata.isAnnotationPresent(LazyLoading.class)) {
      return new LazyLoadingCodec<>(decorated, type);
    } else {
      return decorated;
    }
  }

  /**
   * A {@link Codec} that will only start loading the data when one of the
   * methods of that object are invoked.
   *
   * @author Wilfred Springer (wis)
   * @param <T>
   *          the generic type
   */
  public static class LazyLoadingCodec<T> implements Codec<T> {

    /**
     * The {@link Codec} to use.
     */
    private Codec<T> wrapped;

    /**
     * The type of object created.
     */
    private Class<T> type;

    /**
     * Constructs a new instance.
     *
     * @param wrapped
     *          The {@link Codec} to use when loading the data.
     * @param type
     *          The type of object that will be returned.
     */
    public LazyLoadingCodec(Codec<T> wrapped, Class<T> type) {
      this.wrapped = wrapped;
      this.type = type;
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#decode(org.codehaus.preon.buffer.BitBuffer,
     * org.codehaus.preon.Resolver, org.codehaus.preon.Builder)
     */

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
    @SuppressWarnings("unchecked")
    public T decode(final BitBuffer buffer, final Resolver resolver, final Builder builder)
        throws DecodingException {
      final int size = wrapped.getSize().eval(resolver);
      final long pos = buffer.getBitPos();
      ClassLoader loader = this.getClass().getClassLoader();
      Enhancer enhancer = new Enhancer();
      enhancer.setClassLoader(loader);
      enhancer.setSuperclass(type);
      enhancer.setCallback((MethodInterceptor) (arg0, arg1, arg2, arg3) -> null);
      buffer.setBitPos(pos + size);
      return (T) enhancer.create();
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
      wrapped.encode(value, channel, resolver);
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getTypes()
     */

    /**
     * Gets the types.
     *
     * @return the types
     */
    @Override
    public Class< ? >[] getTypes() {
      return wrapped.getTypes();
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
      return wrapped.getSize();
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getType()
     */

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
      return new PassThroughCodecDescriptor2(wrapped.getCodecDescriptor(), false);
    }

  }

}
