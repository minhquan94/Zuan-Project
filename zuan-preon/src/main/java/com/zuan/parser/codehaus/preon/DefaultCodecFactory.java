/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zuan.parser.codehaus.preon.binding.BindingDecorator;
import com.zuan.parser.codehaus.preon.binding.BindingFactory;
import com.zuan.parser.codehaus.preon.binding.ConditionalBindingFactory;
import com.zuan.parser.codehaus.preon.binding.DecoratingBindingFactory;
import com.zuan.parser.codehaus.preon.binding.StandardBindingFactory;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.codec.ArrayCodecFactory;
import com.zuan.parser.codehaus.preon.codec.BooleanCodecFactory;
import com.zuan.parser.codehaus.preon.codec.BoundBufferCodecFactory;
import com.zuan.parser.codehaus.preon.codec.ByteAligningDecorator;
import com.zuan.parser.codehaus.preon.codec.CachingCodecFactory;
import com.zuan.parser.codehaus.preon.codec.CompoundCodecFactory;
import com.zuan.parser.codehaus.preon.codec.EnumCodec;
import com.zuan.parser.codehaus.preon.codec.ExplicitCodecFactory;
import com.zuan.parser.codehaus.preon.codec.InitCodecDecorator;
import com.zuan.parser.codehaus.preon.codec.LazyLoadingCodecDecorator;
import com.zuan.parser.codehaus.preon.codec.ListCodecFactory;
import com.zuan.parser.codehaus.preon.codec.MapCodecFactory;
import com.zuan.parser.codehaus.preon.codec.NumericCodec;
import com.zuan.parser.codehaus.preon.codec.ObjectCodecFactory;
import com.zuan.parser.codehaus.preon.codec.SlicingCodecDecorator;
import com.zuan.parser.codehaus.preon.codec.StringCodecFactory;
import com.zuan.parser.codehaus.preon.el.Expression;

import nl.flotsam.pecia.AnnotatedSection;
import nl.flotsam.pecia.Contents;
import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * The default {@link CodecFactory} implementation, constructing {@link Codecs}
 * based on all {@link CodecFactory CodecFactories} available by default. The
 * {@link Codec Codecs} constructed by this factory will most likely wrap an
 * {@link org.codehaus.preon.codec.ObjectCodecFactory}. The main difference
 * between that {@link Codec} and the one created by this factory is that this
 * one actually remembers all of the {@link Codec Codecs} that were constructed,
 * making it a better candidate for building documentation.
 */
public class DefaultCodecFactory implements CodecFactory {

  /**
   * Creates the.
   *
   * @param <T>
   *          the generic type
   * @param type
   *          the type
   * @return the codec
   */
  public <T> Codec<T> create(Class<T> type) {
    return create(null, type, null);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecFactory#create(java.lang.reflect.
   * AnnotatedElement, java.lang.Class,
   * com.zuan.parser.codehaus.preon.ResolverContext)
   */
  @Override
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    return create(metadata, type, new CodecFactory[0], new CodecDecorator[0],
        new BindingDecorator[0]);
  }

  /**
   * Creates the.
   *
   * @param <T>
   *          the generic type
   * @param metadata
   *          the metadata
   * @param type
   *          the type
   * @param addOnFactories
   *          the add on factories
   * @param addOnDecorators
   *          the add on decorators
   * @param bindingDecorators
   *          the binding decorators
   * @return the codec
   */
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      CodecFactory[] addOnFactories, CodecDecorator[] addOnDecorators,
      BindingDecorator[] bindingDecorators) {

    // The actual cache of Codecs.
    final List<Codec< ? >> created = new ArrayList<>();

    // Create the default BindingFactory.
    BindingFactory bindingFactory = new StandardBindingFactory();
    bindingFactory = new ConditionalBindingFactory(bindingFactory);
    if (bindingDecorators.length != 0) {
      bindingFactory = new DecoratingBindingFactory(bindingFactory, bindingDecorators);
    }

    // Eventually, *every* request for a Codec will be processed by this
    // CodecFactory.
    CompoundCodecFactory codecFactory = new CompoundCodecFactory();

    // We need a decorating CodecFactory for all decorators.
    List<CodecDecorator> decorators = new ArrayList<>();
    decorators.add(new LazyLoadingCodecDecorator());
    decorators.add(new SlicingCodecDecorator());
    decorators.add(new ByteAligningDecorator());
    decorators.add(new InitCodecDecorator());
    decorators.addAll(Arrays.asList(addOnDecorators));

    DecoratingCodecFactory top = new DecoratingCodecFactory();

    // Add additional CodecFactories passed in.
    for (CodecFactory factory : addOnFactories) {
      codecFactory.add(factory);
    }

    // Add other default CodecFactories.
    codecFactory.add(new ExplicitCodecFactory());
    codecFactory.add(new BoundBufferCodecFactory());
    codecFactory.add(new NumericCodec.Factory());
    codecFactory.add(new StringCodecFactory());
    codecFactory.add(new BooleanCodecFactory());
    codecFactory.add(new EnumCodec.Factory());

    // Create an ObjectCodecFactory that delegates to the
    // CompoundCodecFactory for each of its members.
    ObjectCodecFactory objectCodecFactory = new ObjectCodecFactory(top, bindingFactory);

    // Make sure that Codecs created by the ObjectCodecFactory can be
    // cached.
    CachingCodecFactory cache =
        new CachingCodecFactory(objectCodecFactory, codec -> created.add(codec));

    // Not when you are constructing Lists or arrays of objects.
    codecFactory.add(new ListCodecFactory(top));
    codecFactory.add(new ArrayCodecFactory(top));
    codecFactory.add(new MapCodecFactory(top));

    // Add the (cached) ObjectCodecFactory as a last resort.
    codecFactory.add(cache);
    return new DefaultCodec<>(top.create(metadata, type, null), created);
  }

  /**
   * The default {@link Codec}.
   *
   * @author Wilfred Springer
   * @param <T>
   *          the generic type
   */
  private class DefaultCodec<T> implements Codec<T> {

    /** The delegate. */
    private Codec<T> delegate;

    /** The created. */
    private List<Codec< ? >> created;

    /**
     * Instantiates a new default codec.
     *
     * @param delegate
     *          the delegate
     * @param created
     *          the created
     */
    public DefaultCodec(Codec<T> delegate, List<Codec< ? >> created) {
      this.delegate = delegate;
      this.created = created;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#decode(com.zuan.parser.
     * codehaus.preon.buffer.BitBuffer, com.zuan.parser.codehaus.preon.Resolver,
     * com.zuan.parser.codehaus.preon.Builder)
     */
    @Override
    public T decode(BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      return delegate.decode(buffer, resolver, builder);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#encode(java.lang.Object,
     * com.zuan.parser.codehaus.preon.channel.BitChannel,
     * com.zuan.parser.codehaus.preon.Resolver)
     */
    @Override
    public void encode(T value, BitChannel channel, Resolver resolver) throws IOException {
      delegate.encode(value, channel, resolver);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#getTypes()
     */
    @Override
    public Class< ? >[] getTypes() {
      return delegate.getTypes();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#getSize()
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return delegate.getSize();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.Codec#getType()
     */
    @Override
    public Class< ? > getType() {
      return delegate.getType();
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
            created.remove(delegate);
            target.document(delegate.getCodecDescriptor().details(bufferReference));
            for (Codec< ? > codec : created) {
              assert codec != null;
              CodecDescriptor descriptor = codec.getCodecDescriptor();
              assert descriptor != null;
              if (descriptor.requiresDedicatedSection() && target instanceof Contents) {
                AnnotatedSection< ? > section =
                    ((Contents< ? >) target).section(descriptor.getTitle());
                section.mark(descriptor.getTitle())
                    .document(descriptor.details(bufferReference));
                section.end();
              }
            }
          };
        }

        @Override
        public String getTitle() {
          return delegate.getCodecDescriptor().getTitle();
        }

        @Override
        public <C extends ParaContents< ? >> Documenter<C> reference(Adjective adjective,
            boolean startWithCapital) {
          return delegate.getCodecDescriptor().reference(adjective, false);
        }

        @Override
        public boolean requiresDedicatedSection() {
          return delegate.getCodecDescriptor().requiresDedicatedSection();
        }

        @Override
        public <C extends ParaContents< ? >> Documenter<C> summary() {
          return delegate.getCodecDescriptor().summary();
        }

      };
    }

  }

  /**
   * A {@link CodecFactory}, decorating the {@link Codec}s constructed.
   *
   * @author Wilfred Springer
   */
  private static class DecoratingCodecFactory implements CodecFactory {

    /**
     * The {@link CodecFactory} performing the actual work.
     */
    private CodecFactory delegate;

    /**
     * The decorators for decorating the {@link Codec}s constructed.
     */
    private List<CodecDecorator> decorators;

    /**
     * {@inheritDoc} Every Codec constructed will be decorated by the
     * {@link #decorators}.
     */
    @Override
    public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
        ResolverContext context) {
      Codec<T> codec = delegate.create(metadata, type, context);
      if (codec != null) {
        for (CodecDecorator decorator : decorators) {
          codec = decorator.decorate(codec, metadata, type, context);
        }
      }
      return codec;
    }

  }

}
