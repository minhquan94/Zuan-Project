/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecConstructionListener;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.Codecs;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.PassThroughCodecDescriptor2;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.util.AnnotationUtils;

/**
 * An implementation of the {@link CodecFactory} interface that will prevent the
 * same {@link Codec} from being constructed twice.
 * <p/>
 * <p>
 * Introduced to prevent problems when creating {@link Codec Codecs} for objects
 * that introduce a circular dependency, like the example below:
 * </p>
 * <p/>
 *
 * <pre>
 * class Foo {
 * <p/>
 *     &#064;Bound
 *     private Bar value;
 * <p/>
 * }
 * <p/>
 * class Bar {
 * <p/>
 *     &#064;Bound
 *     private Foo value;
 * <p/>
 * }
 * </pre>
 * <p/>
 * <p>
 * Without using this {@link CodecFactory} decorator, the underlying
 * {@link CodecFactory} might potentially generate a stack overflow, creating
 * {@link Codec Codecs} for Foo and Bar.
 * </p>
 * <p/>
 * <p>
 * Note that this class also provides a convenient way to access the
 * {@link Codec Codecs} created, which comes in handy when generating
 * documentation for all of these {@link Codec Codecs}. (See
 * {@link #getCodecs()}.)
 * </p>
 */
public class CachingCodecFactory implements CodecFactory {

  /**
   * A list of all {@link Codecs} already constructed, indexed by just the type.
   * (In the future, this should include the metadata as well.)
   */
  private Map<Key, CodecHolder< ? >> created;

  /**
   * The object to which the actual construction of the {@link Codec} will be
   * delegated.
   */
  private CodecFactory delegate;

  /**
   * The object receiving notifications of new objects getting constructed.
   */
  private CodecConstructionListener listener = codec -> {
  };

  /**
   * Constructs a new instance, accepting the {@link CodecFactory} to which this
   * factory should delegate if it not already constructed the required
   * {@link Codec} before.
   *
   * @param delegate
   *          The {@link CodecFactory} to which this factory should delegate if
   *          it not already constructed the required {@link Codec} before.
   */
  public CachingCodecFactory(CodecFactory delegate) {
    created = new HashMap<>();
    this.delegate = delegate;
  }

  /**
   * Instantiates a new caching codec factory.
   *
   * @param delegate
   *          the delegate
   * @param listener
   *          the listener
   */
  public CachingCodecFactory(CodecFactory delegate, CodecConstructionListener listener) {
    this(delegate);
    if (listener == null) {
      throw new IllegalArgumentException("Null not allowed for listener.");
    }
    this.listener = listener;
  }

  // JavaDoc inherited

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
  @SuppressWarnings("unchecked")
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    Key key = new Key(metadata, type, context);
    Codec<T> result = (Codec<T>) created.get(key);
    if (result == null) {
      CodecHolder<T> holder = new CodecHolder<>(type);
      created.put(key, holder);
      result = delegate.create(metadata, type, context);
      if (result == null) {
        return null;
      } else {
        listener.constructed(result);
        holder.set(result);
        return result;
      }
    } else {
      if (result instanceof CodecHolder) {
        CodecHolder<T> holder = (CodecHolder<T>) result;
        if (holder.get() == null) {
          return null;
        }
      }
      return result;
    }
  }

  /**
   * Returns the {@link Codec Codecs} created by this factory.
   *
   * @return A {@link List} of {@link Codec Codecs} created by this factory.
   */
  public List<Codec< ? >> getCodecs() {
    return Collections.unmodifiableList(new ArrayList<Codec< ? >>(created.values()));
  }

  /**
   * The Class CodecHolder.
   *
   * @param <T>
   *          the generic type
   */
  private static class CodecHolder<T> implements Codec<T> {

    /** The codec. */
    private Codec<T> codec;

    /** The type. */
    private Class<T> type;

    /**
     * Instantiates a new codec holder.
     *
     * @param type
     *          the type
     */
    public CodecHolder(Class<T> type) {
      this.type = type;
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
      return codec.decode(buffer, resolver, builder);
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
      return new Class< ? >[]{type };
    }

    /**
     * Sets the.
     *
     * @param codec
     *          the codec
     */
    public void set(Codec<T> codec) {
      this.codec = codec;
    }

    /**
     * Gets the.
     *
     * @return the codec
     */
    public Codec<T> get() {
      return codec;
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
      return new PassThroughCodecDescriptor2(codec.getCodecDescriptor(),
          codec.getCodecDescriptor().requiresDedicatedSection());
    }

  }

  /**
   * The Class Key.
   */
  private static class Key {

    /** The metadata. */
    private AnnotatedElement metadata;

    /** The type. */
    private Class< ? > type;

    /**
     * Instantiates a new key.
     *
     * @param metadata
     *          the metadata
     * @param type
     *          the type
     * @param context
     *          the context
     */
    public Key(AnnotatedElement metadata, Class< ? > type, ResolverContext context) {
      this.metadata = metadata;
      this.type = type;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Key)) {
        return false;
      } else {
        Key key = (Key) obj;
        // TODO: Add ResolverContext
        return ((metadata == null && key.metadata == null)
            || AnnotationUtils.equivalent(metadata, key.metadata))
            && ((type == null && key.type == null) || type.equals(key.type));
      }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      // TODO: Add ResolverContext
      int result = 7;
      result =
          result * 31 + (metadata == null ? 0 : AnnotationUtils.calculateHashCode(metadata));
      result = result * 31 + (type == null ? 0 : type.hashCode());
      return result;
    }

  }

}
