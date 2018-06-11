/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecConstructionException;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.Codecs;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.BoundList;
import com.zuan.parser.codehaus.preon.annotation.BoundObject;
import com.zuan.parser.codehaus.preon.annotation.Choices;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.buffer.BitBufferUnderflowException;
import com.zuan.parser.codehaus.preon.buffer.SlicedBitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.descriptor.NullCodecDescriptor2;
import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.ContextReplacingReference;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;
import com.zuan.parser.codehaus.preon.util.AnnotationWrapper;
import com.zuan.parser.codehaus.preon.util.CodecDescriptorHolder;
import com.zuan.parser.codehaus.preon.util.EvenlyDistributedLazyList;
import com.zuan.parser.codehaus.preon.util.ParaContentsDocument;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A {@link CodecFactory} capable of supporting Lists.
 * <p/>
 * <p>
 * There are a couple of cases that we need to clarify. First of all, the
 * {@link ListCodecFactory} is triggered by the {@link BoundList} annotation.
 * The specific way of decoding the List is determined by a couple of factors.
 * </p>
 * <p/>
 * <ul>
 * <li>The size of the list (in numbers of items)</li>
 * <li>The size of the list (in bytes)</li>
 * <li>The size of the individual items in the list</li>
 * <li>If this is a constant or not</li>
 * </ul>
 * <p/>
 * <p>
 * Things are further complicated by the fact that sometimes these questions can
 * be answered at Codec construction time and in other cases at decoding time.
 * In both cases, we might not be able to determine all of these answers or even
 * any of them at all.
 * </p>
 */
public class ListCodecFactory implements CodecFactory {

  /**
   * The {@link CodecFactory} that will be used for constructing the
   * {@link Codecs} to construct elements in the List.
   */
  private CodecFactory delegate;

  /**
   * Constructs a new instance, accepting the {@link CodecFactory} creating the
   * {@link Codec Codecs} that will reconstruct elements in the List.
   *
   * @param delegate
   *          The {@link CodecFactory} creating the {@link Codec Codecs} that
   *          will reconstruct elements in the List.
   */
  public ListCodecFactory(CodecFactory delegate) {
    this.delegate = delegate;
  }

  // JavaDoc inherited.

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
  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.CodecFactory#create(java.lang.reflect.AnnotatedElement,
   * java.lang.Class, org.codehaus.preon.ResolverContext)
   */
  @SuppressWarnings({"unchecked", "rawtypes" })
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    BoundList settings;
    if (metadata != null && (settings = metadata.getAnnotation(BoundList.class)) != null
        && java.util.List.class.equals(type)) {
      Codec< ? > codec = createElementCodec(context, settings);
      if (settings.size().length() == 0) {
        // So, we don't know the number of elements in this list.
        // This means we need to keep on reading elements until we get
        // an 'EOF' or a DecodingException. In case of a
        // DecodingException, the pointer is expected to be moved back
        // to the first position.
        return new DynamicListCodec(codec);
      } else if (settings.offset().length() != 0) {
        // So the size is known. If the offset attribute has been set,
        // it means we can calculate the position of the individual
        // elements based on the index of that element.
        Expression<Integer, Resolver> size = getSizeExpression(settings, context);
        Expression<Integer, Resolver> offsets;
        CodecDescriptorHolder holder = new CodecDescriptorHolder();
        offsets = Expressions.createInteger(new IndexedResolverContext(context, holder),
            settings.offset());
        Codec<T> result = new OffsetListCodec(offsets, size, codec);
        holder.setDescriptor(result.getCodecDescriptor());
        return result;
      } else {
        // In this case, there may be a size (number of elements) set,
        // but the size of the individual may not be constant. The size
        // of the individual elements may be determined by some
        // variables read upstream, so we won't know if the size is a
        // constant until we actually start decoding the List.

        Expression<Integer, Resolver> expr = getSizeExpression(settings, context);
        Expression<Integer, Resolver> elementSize = codec.getSize();
        if (elementSize != null
            && (!elementSize.isParameterized() || elementSize.isConstantFor(context))) {
          if (!elementSize.isParameterized()) {
            return new StaticListCodec(expr, codec, elementSize);
          } else {
            elementSize = elementSize.rescope(context);
            return new StaticListCodec(expr.rescope(context), codec, elementSize);
          }
        } else {
          return new DynamicListCodec(codec);
        }
      }
    } else {
      return null;
    }

  }

  /**
   * Creates a new ListCodec object.
   *
   * @param <T>
   *          the generic type
   * @param context
   *          the context
   * @param settings
   *          the settings
   * @return the codec<?>
   */
  @SuppressWarnings("unchecked")
  private <T> Codec< ? > createElementCodec(ResolverContext context, BoundList settings) {
    if (settings.types().length > 0) {
      BoundObject objectSettings = getObjectSettings(settings);
      return delegate.create(toAnnotatedElemented(objectSettings),
          objectSettings.type() == Void.class ? Object.class
              : (Class<Object>) objectSettings.type(),
          context);
    } else if (settings.type() != null) {
      return delegate.create(null, settings.type(), context);
    } else {
      throw new CodecConstructionException("Failed to determine the type of element.");
    }
  }

  /**
   * To annotated elemented.
   *
   * @param objectSettings
   *          the object settings
   * @return the annotated element
   */
  private AnnotatedElement toAnnotatedElemented(BoundObject objectSettings) {
    if (objectSettings == null) {
      return null;
    } else {
      return new AnnotationWrapper(objectSettings);
    }
  }

  /**
   * Returns a {@link BoundObject} annotation containing the properties it
   * shares in common with the {@link BoundList} annotation.
   *
   * @param settings
   *          The {@link BoundList} settings.
   * @return A {@link BoundObject} annotation with settings copied from the
   *         {@link BoundList} annotation passed in.
   */
  private BoundObject getObjectSettings(final BoundList settings) {
    return new BoundObject() {

      @Override
      public Class< ? > type() {
        return settings.type();
      }

      @Override
      public Class< ? >[] types() {
        return settings.types();
      }

      @Override
      public Class< ? extends Annotation> annotationType() {
        return BoundObject.class;
      }

      @Override
      public boolean ommitTypePrefix() {
        return settings.ommitTypePrefix();
      }

      @Override
      public Choices selectFrom() {
        return settings.selectFrom();
      }

    };
  }

  /**
   * The {@link Codec} for reading the {@link List} and its members, on demand.
   * Instances of this class will <em>not</em> create a standard {@link List}
   * implementation and populate all of its data immediately. Instead it will
   * create a {@link org.codehaus.preon.util.UnevenlyDistributedLazyList},
   * constructing its elements on the fly, only when it is required.
   * <p/>
   * <p/>
   * Note that this class is called <code><em>Static</em>ListCodec</code> since
   * it relies on the fact that the size of the List and the amount of data
   * required for every list member is known in advance. </p
   *
   * @param <T>
   *          the generic type
   */
  private static class StaticListCodec<T> implements Codec<List<T>> {

    /**
     * The number of elements in the list.
     */
    private Expression<Integer, Resolver> size;

    /**
     * The {@link Codec} that will construct elements from the {@link List}.
     */
    private Codec<T> codec;

    /**
     * An expression to be used to calculate the size of the elements at list
     * construction time.
     */
    private Expression<Integer, Resolver> elementSize;

    /**
     * Constructs a new instance.
     *
     * @param maxSize
     *          An {@link Expression} representing the number of elements in the
     *          {@link List}.
     * @param codec
     *          The {@link Codec} constructing elements in the {@link List}.
     * @param elementSize
     *          the element size
     */
    public StaticListCodec(Expression<Integer, Resolver> maxSize, Codec<T> codec,
        Expression<Integer, Resolver> elementSize) {
      this.size = maxSize;
      this.codec = codec;
      this.elementSize = elementSize;
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
     * @return the list
     * @throws DecodingException
     *           the decoding exception
     */
    @Override
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#decode(org.codehaus.preon.buffer.BitBuffer,
     * org.codehaus.preon.Resolver, org.codehaus.preon.Builder)
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    public List<T> decode(BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      return new EvenlyDistributedLazyList(codec, buffer.getBitPos(), buffer,
          size.eval(resolver), builder, resolver, elementSize.eval(resolver));
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
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#encode(java.lang.Object,
     * org.codehaus.preon.channel.BitChannel, org.codehaus.preon.Resolver)
     */
    @Override
    public void encode(List<T> value, BitChannel channel, Resolver resolver) {
      throw new UnsupportedOperationException();
    }

    /**
     * Gets the types.
     *
     * @return the types
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getTypes()
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
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getSize()
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return Expressions.multiply(size, elementSize);
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getType()
     */
    @Override
    public Class< ? > getType() {
      return List.class;
    }

    /**
     * Gets the codec descriptor.
     *
     * @return the codec descriptor
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getCodecDescriptor()
     */
    @Override
    public CodecDescriptor getCodecDescriptor() {
      return new CodecDescriptor() {

        @Override
        public <C extends SimpleContents< ? >> Documenter<C> details(
            final String bufferReference) {
          return target -> {
            target.para().text("The number of elements in ")
                .document(reference(Adjective.THE, false)).text(" is ")
                .document(Documenters.forExpression(size)).text(".").end();
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
              .document(codec.getCodecDescriptor().reference(Adjective.NONE, false));
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

  }

  /**
   * Returns the {@link Expression} that will be evaluated to the {@link List}
   * 's size.
   *
   * @param listSettings
   *          The annotation, holding the expression.
   * @param context
   *          the context
   * @return An {@link Expression} instance, representing the expression.
   * @throws CodecConstructionException
   *           the codec construction exception
   */
  private Expression<Integer, Resolver> getSizeExpression(BoundList listSettings,
      ResolverContext context) throws CodecConstructionException {
    return Expressions.createInteger(context, listSettings.size());
  }

  /**
   * The Class DynamicListCodec.
   *
   * @param <T>
   *          the generic type
   */
  private static class DynamicListCodec<T> implements Codec<List<T>> {

    /** The codec. */
    private Codec<T> codec;

    /**
     * Instantiates a new dynamic list codec.
     *
     * @param codec
     *          the codec
     */
    public DynamicListCodec(Codec<T> codec) {
      this.codec = codec;
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
     * @return the list
     * @throws DecodingException
     *           the decoding exception
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#decode(org.codehaus.preon.buffer.BitBuffer,
     * org.codehaus.preon.Resolver, org.codehaus.preon.Builder)
     */
    @Override
    public List<T> decode(BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      List<T> result = new LinkedList<>();
      long mark = buffer.getBitPos();
      try {
        while (true) {
          T value = codec.decode(buffer, resolver, builder);
          result.add(value);
          mark = buffer.getBitPos();
        }
      } catch (BitBufferUnderflowException oore) {
        // Trying to read beyond the end of the file.
        // TODO: Make a difference between failing half-way and failing
        // starting to read the next element.
      } catch (DecodingException de) {
        // So we can't decode the element. Maybe it's no longer an
        // element of this List. Let's consider this list to be
        // completed.
        buffer.setBitPos(mark);
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
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#encode(java.lang.Object,
     * org.codehaus.preon.channel.BitChannel, org.codehaus.preon.Resolver)
     */
    @Override
    public void encode(List<T> value, BitChannel channel, Resolver resolver) {
      throw new UnsupportedOperationException();
    }

    /**
     * Gets the types.
     *
     * @return the types
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getTypes()
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
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getSize()
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return null;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getType()
     */
    @Override
    public Class< ? > getType() {
      return List.class;
    }

    /**
     * Gets the codec descriptor.
     *
     * @return the codec descriptor
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getCodecDescriptor()
     */
    @Override
    public CodecDescriptor getCodecDescriptor() {
      return new CodecDescriptor() {

        @Override
        public <C extends SimpleContents< ? >> Documenter<C> details(
            final String bufferReference) {
          return target -> {
            target.para().text(
                "The number of elements in the list is unknown at forehand. The codec will just decode as many elements as the buffer allows to decode.")
                .end();
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
              .document(codec.getCodecDescriptor().reference(Adjective.NONE, false));
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

  }

  /**
   * A {@link Codec} for Lists. The type of List that will be created is
   * determined at runtime, right before the actual List is decoded. The
   * {@link #skipListCodec} Codec will be used when the size of the individual
   * list item can be determined before the List is getting constructed.
   *
   * @param <T>
   *          the generic type
   */
  private static class SwitchingListCodec<T> implements Codec<List<T>> {

    /** The skip list codec. */
    private Codec<List<T>> skipListCodec;

    /** The non skip list codec. */
    private Codec<List<T>> nonSkipListCodec;

    /**
     * Decode.
     *
     * @param buffer
     *          the buffer
     * @param resolver
     *          the resolver
     * @param builder
     *          the builder
     * @return the list
     * @throws DecodingException
     *           the decoding exception
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#decode(org.codehaus.preon.buffer.BitBuffer,
     * org.codehaus.preon.Resolver, org.codehaus.preon.Builder)
     */
    @Override
    public List<T> decode(BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      Expression<Integer, Resolver> sizeExpr = skipListCodec.getSize();
      if (sizeExpr != null && sizeExpr.eval(resolver) >= 0) {
        return skipListCodec.decode(buffer, resolver, builder);
      } else {
        return nonSkipListCodec.decode(buffer, resolver, builder);
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
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#encode(java.lang.Object,
     * org.codehaus.preon.channel.BitChannel, org.codehaus.preon.Resolver)
     */
    @Override
    public void encode(List<T> value, BitChannel channel, Resolver resolver) {
      throw new UnsupportedOperationException();
    }

    /**
     * Gets the types.
     *
     * @return the types
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getTypes()
     */
    @Override
    public Class< ? >[] getTypes() {
      return skipListCodec.getTypes();
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getSize()
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return null;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getType()
     */
    @Override
    public Class< ? > getType() {
      return skipListCodec.getType();
    }

    /**
     * Gets the codec descriptor.
     *
     * @return the codec descriptor
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getCodecDescriptor()
     */
    @Override
    public CodecDescriptor getCodecDescriptor() {
      // TODO Auto-generated method stub
      return new NullCodecDescriptor2();
    }

  }

  /**
   * The Class IndexedResolverContext.
   */
  private static class IndexedResolverContext implements ResolverContext {

    /** The context. */
    private ResolverContext context;

    /** The Constant INDEX. */
    final public static String INDEX = "index";

    /** The descriptor. */
    private CodecDescriptor descriptor;

    /**
     * Instantiates a new indexed resolver context.
     *
     * @param context
     *          the context
     * @param descriptor
     *          the descriptor
     */
    public IndexedResolverContext(ResolverContext context, CodecDescriptor descriptor) {
      this.context = context;
      this.descriptor = descriptor;
    }

    /**
     * Select attribute.
     *
     * @param name
     *          the name
     * @return the reference
     */
    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.preon.el.ReferenceContext#selectAttribute(java.lang.String)
     */
    @Override
    public Reference<Resolver> selectAttribute(String name) {
      if (INDEX.equals(name)) {
        return new IndexReference(context, descriptor);
      } else {
        return new ContextReplacingReference(this, context.selectAttribute(name));
      }
    }

    /**
     * Select item.
     *
     * @param index
     *          the index
     * @return the reference
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.el.ReferenceContext#selectItem(java.lang.String)
     */
    @Override
    public Reference<Resolver> selectItem(String index) {
      return new ContextReplacingReference(this, context.selectItem(index));
    }

    /**
     * Select item.
     *
     * @param index
     *          the index
     * @return the reference
     */
    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.preon.el.ReferenceContext#selectItem(org.codehaus.preon.el.
     * Expression)
     */
    @Override
    public Reference<Resolver> selectItem(Expression<Integer, Resolver> index) {
      return new ContextReplacingReference(this, context.selectItem(index));
    }

    /**
     * Document.
     *
     * @param target
     *          the target
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.
     * Document)
     */
    @Override
    public void document(Document target) {
      ParaContentsDocument doc = new ParaContentsDocument(target);
      doc.document(Documenters.forDescriptor(descriptor));
    }

    /**
     * The Class IndexReference.
     */
    private static class IndexReference implements Reference<Resolver> {

      /** The context. */
      private ReferenceContext<Resolver> context;

      /** The descriptor. */
      private CodecDescriptor descriptor;

      /**
       * Instantiates a new index reference.
       *
       * @param context
       *          the context
       * @param descriptor
       *          the descriptor
       */
      public IndexReference(ReferenceContext<Resolver> context, CodecDescriptor descriptor) {
        this.context = context;
        this.descriptor = descriptor;
      }

      /**
       * Gets the reference context.
       *
       * @return the reference context
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.Reference#getReferenceContext()
       */
      @Override
      public ReferenceContext<Resolver> getReferenceContext() {
        return context;
      }

      /**
       * Checks if is assignable to.
       *
       * @param type
       *          the type
       * @return true, if is assignable to
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.Reference#isAssignableTo(java.lang.Class)
       */
      @Override
      public boolean isAssignableTo(Class< ? > type) {
        return Integer.class.isAssignableFrom(type);
      }

      /**
       * Resolve.
       *
       * @param context
       *          the context
       * @return the object
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.Reference#resolve(java.lang.Object)
       */
      @Override
      public Object resolve(Resolver context) {
        return context.get(INDEX);
      }

      /**
       * Select attribute.
       *
       * @param name
       *          the name
       * @return the reference
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.ReferenceContext#selectAttribute(java.lang.
       * String)
       */
      @Override
      public Reference<Resolver> selectAttribute(String name) {
        throw new BindingException("No attribute selection allowed.");
      }

      /**
       * Select item.
       *
       * @param index
       *          the index
       * @return the reference
       */
      /*
       * (non-Javadoc)
       * @see
       * org.codehaus.preon.el.ReferenceContext#selectItem(java.lang.String)
       */
      @Override
      public Reference<Resolver> selectItem(String index) {
        throw new BindingException("No item selection allowed.");
      }

      /**
       * Select item.
       *
       * @param index
       *          the index
       * @return the reference
       */
      /*
       * (non-Javadoc)
       * @see
       * org.codehaus.preon.el.ReferenceContext#selectItem(org.codehaus.preon.
       * el. Expression)
       */
      @Override
      public Reference<Resolver> selectItem(Expression<Integer, Resolver> index) {
        throw new BindingException("No item selection allowed.");
      }

      /**
       * Document.
       *
       * @param target
       *          the target
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.Descriptive#document(org.codehaus.preon.el.
       * Document)
       */
      @Override
      public void document(Document target) {
        target.text("the position of an element in ");
        ParaContentsDocument doc = new ParaContentsDocument(target);
        doc.document(Documenters.forDescriptor(descriptor));
      }

      /**
       * Gets the type.
       *
       * @return the type
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.Reference#getType()
       */
      @Override
      public Class< ? > getType() {
        return Integer.class;
      }

      /**
       * Narrow.
       *
       * @param type
       *          the type
       * @return the reference
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.Reference#narrow(java.lang.Class)
       */
      @Override
      public Reference<Resolver> narrow(Class< ? > type) {
        if (type == Integer.class) {
          return this;
        } else {
          return null;
        }
      }

      /**
       * Checks if is based on.
       *
       * @param resolverReferenceContext
       *          the resolver reference context
       * @return true, if is based on
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.Reference#isBasedOn(org.codehaus.preon.el.
       * ReferenceContext)
       */
      @Override
      public boolean isBasedOn(ReferenceContext<Resolver> resolverReferenceContext) {
        return false;
      }

      /**
       * Rescope.
       *
       * @param resolverReferenceContext
       *          the resolver reference context
       * @return the reference
       */
      /*
       * (non-Javadoc)
       * @see org.codehaus.preon.el.Reference#rescope(org.codehaus.preon.el.
       * ReferenceContext)
       */
      @Override
      public Reference<Resolver> rescope(ReferenceContext<Resolver> resolverReferenceContext) {
        return this;
      }

    }

  }

  /**
   * The Class IndexResolver.
   */
  private static class IndexResolver implements Resolver {

    /** The resolver. */
    private Resolver resolver;

    /** The index. */
    private int index;

    /**
     * Instantiates a new index resolver.
     *
     * @param resolver
     *          the resolver
     */
    public IndexResolver(Resolver resolver) {
      this.resolver = resolver;
    }

    /**
     * Gets the.
     *
     * @param name
     *          the name
     * @return the object
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Resolver#get(java.lang.String)
     */
    @Override
    public Object get(String name) {
      if (IndexedResolverContext.INDEX.equals(name)) {
        return index;
      } else {
        return resolver.get(name);
      }
    }

    // public Resolver getOuter() {
    // // return resolver.getOuter();
    // }

    /**
     * Sets the index.
     *
     * @param index
     *          the new index
     */
    public void setIndex(int index) {
      this.index = index;
    }

    /**
     * Gets the original resolver.
     *
     * @return the original resolver
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Resolver#getOriginalResolver()
     */
    @Override
    public Resolver getOriginalResolver() {
      return this;
    }

  }

  /**
   * A {@link Codec} for Lists, expecting the location of the elements to be
   * defined by some Limbo expression based on the index of the element.
   *
   * @param <T>
   *          the generic type
   */
  private static class OffsetListCodec<T> implements Codec<List<T>> {

    /**
     * The expression to calculate the offset. (Note that you can use the
     * 'index' variable to point to the position of this element in the list.
     */
    private Expression<Integer, Resolver> offsets;

    /**
     * The size of the list.
     */
    private Expression<Integer, Resolver> size;

    /**
     * The Codec for decoding elements from the list.
     */
    private Codec<T> codec;

    /**
     * Constructs a new instance.
     *
     * @param offsets
     *          An expression for calculating the offset of the beginning of an
     *          element, as a function of the elements position in the list.
     * @param size
     *          An expression resolving into the size of the list.
     * @param codec
     *          The {@link Codec} to use for decoding individual list elements.
     */
    public OffsetListCodec(Expression<Integer, Resolver> offsets,
        Expression<Integer, Resolver> size, Codec<T> codec) {
      this.offsets = offsets;
      this.size = size;
      this.codec = codec;
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
     * @return the list
     * @throws DecodingException
     *           the decoding exception
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#decode(org.codehaus.preon.buffer.BitBuffer,
     * org.codehaus.preon.Resolver, org.codehaus.preon.Builder)
     */
    @Override
    public List<T> decode(BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      int maxSize = size.eval(resolver);
      List<T> result = new ArrayList<>(maxSize);
      long curPos = buffer.getBitPos();
      IndexResolver indexResolver = new IndexResolver(resolver);
      for (int i = 0; i < maxSize; i++) {
        indexResolver.setIndex(i);
        int offset = offsets.eval(indexResolver);
        if (i < maxSize - 1) {
          indexResolver.setIndex(i + 1);
          int nextOffset = offsets.eval(indexResolver); // - 1;
          buffer.setBitPos(curPos + offset);
          T value = codec.decode(new SlicedBitBuffer(buffer, nextOffset - offset), resolver,
              builder);
          result.add(value);
        } else {
          buffer.setBitPos(curPos + offset);
          result.add(codec.decode(buffer, resolver, builder));
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
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#encode(java.lang.Object,
     * org.codehaus.preon.channel.BitChannel, org.codehaus.preon.Resolver)
     */
    @Override
    public void encode(List<T> value, BitChannel channel, Resolver resolver) {
      throw new UnsupportedOperationException();
    }

    /**
     * Gets the types.
     *
     * @return the types
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getTypes()
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
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getSize()
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
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getType()
     */
    @Override
    public Class< ? > getType() {
      return List.class;
    }

    /**
     * Gets the codec descriptor.
     *
     * @return the codec descriptor
     */
    /*
     * (non-Javadoc)
     * @see org.codehaus.preon.Codec#getCodecDescriptor()
     */
    @Override
    public CodecDescriptor getCodecDescriptor() {
      return new CodecDescriptor() {

        @Override
        public <C extends SimpleContents< ? >> Documenter<C> details(
            final String bufferReference) {
          return target -> {
            target.para().text("The number of items in the list is ")
                .document(Documenters.forExpression(size))
                .text(
                    ". The position of an item in the encoded representation is based on its index in the list.")
                .text(" Given an item's index, it's position is: ")
                .document(Documenters.forExpression(offsets)).text(".").end();
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
              .document(codec.getCodecDescriptor().reference(Adjective.NONE, false));
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
  }

}
