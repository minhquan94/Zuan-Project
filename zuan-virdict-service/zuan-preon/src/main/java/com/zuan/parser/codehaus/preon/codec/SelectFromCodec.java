/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.Choices;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.ContextReplacingReference;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;
import nl.flotsam.pecia.Table2Cols;

/**
 * A Codec supporting the {@link Choices} annotation.
 *
 * @param <T>
 *          The type of object to be returned.
 */
public class SelectFromCodec<T> implements Codec<T> {

  /**
   * The size of the prefix, in number of bits.
   */
  private int prefixSize;

  /**
   * The byte order of the prefix.
   */
  private ByteOrder byteOrder;

  /**
   * A list of all conditions.
   */
  private List<Expression<Boolean, Resolver>> conditions;

  /**
   * A list of all {@link Codec}s.
   */
  private List<Codec< ? >> codecs;

  /**
   * The types potentially returned by this {@link Codec}.
   */
  private Class< ? >[] types;

  /**
   * The common type.
   */
  private Class< ? > type;

  /**
   * The {@link Codec} to apply when none of the conditions are met.
   */
  private Codec< ? > defaultCodec;

  /**
   * Constructs a new instance, accepting the type, choices, a
   * {@link ResolverContext} to wrap for introducing the <code>prefix</code>
   * variable, the {@link CodecFactory} to delegate to, and the metadata.
   *
   * @param type
   *          the type
   * @param choices
   *          the choices
   * @param context
   *          the context
   * @param factory
   *          the factory
   * @param metadata
   *          the metadata
   */
  public SelectFromCodec(Class< ? > type, Choices choices, ResolverContext context,
      CodecFactory factory, AnnotatedElement metadata) {
    this.prefixSize = choices.prefixSize();
    this.types = new Class< ? >[choices.alternatives().length];
    this.byteOrder = choices.byteOrder();
    conditions = new ArrayList<>();
    codecs = new ArrayList<>();
    if (choices.defaultType() != Void.class) {
      defaultCodec = factory.create(null, choices.defaultType(), context);
    }
    ResolverContext passThroughContext = new PrefixResolverContext(context, prefixSize);
    for (int i = 0; i < choices.alternatives().length; i++) {
      types[i] = choices.alternatives()[i].type();
      conditions.add(Expressions.createBoolean(passThroughContext,
          choices.alternatives()[i].condition()));
      codecs.add(factory.create(null, choices.alternatives()[i].type(), passThroughContext));
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
  @SuppressWarnings("unchecked")
  @Override
  public T decode(BitBuffer buffer, Resolver resolver, Builder builder)
      throws DecodingException {
    if (prefixSize <= 0) {
      for (int i = 0; i < conditions.size(); i++) {
        if (conditions.get(i).eval(resolver)) {
          return (T) codecs.get(i).decode(buffer, resolver, builder);
        }
      }
    } else {
      int prefix = buffer.readAsInt(this.prefixSize, byteOrder);
      for (int i = 0; i < conditions.size(); i++) {
        if (conditions.get(i).eval(new PrefixResolver(resolver, prefix))) {
          return (T) codecs.get(i).decode(buffer, resolver, builder);
        }
      }
    }
    if (defaultCodec != null) {
      return (T) defaultCodec.decode(buffer, resolver, builder);
    } else {
      return null;
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
  @Override
  public void encode(T value, BitChannel channel, Resolver resolver) {
    throw new UnsupportedOperationException();
  }

  /**
   * Gets the size.
   *
   * @return the size
   */
  @Override
  public Expression<Integer, Resolver> getSize() {
    Integer result = null;
    for (Codec< ? > codec : codecs) {
      Expression<Integer, Resolver> size = codec.getSize();
      if (size == null || size.isParameterized()) {
        return null;
      } else {
        if (result == null) {
          result = size.eval(null); // Not parameterized, so we can do
          // this.
        } else {
          if (!result.equals(size.eval(null))) {
            return null;
          }
        }
      }
    }
    if (result != null) {
      return Expressions.createInteger(result, Resolver.class);
    } else {
      return null;
    }
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
   * Gets the types.
   *
   * @return the types
   */
  @Override
  public Class< ? >[] getTypes() {
    return types;
  }

  /**
   * The Class PrefixResolverContext.
   */
  private static class PrefixResolverContext implements ResolverContext {

    /** The context. */
    private ResolverContext context;

    /** The prefix size. */
    private int prefixSize;

    /** The Constant PREFIX. */
    final public static String PREFIX = "prefix";

    /**
     * Instantiates a new prefix resolver context.
     *
     * @param context
     *          the context
     * @param prefixSize
     *          the prefix size
     */
    public PrefixResolverContext(ResolverContext context, int prefixSize) {
      this.context = context;
      this.prefixSize = prefixSize;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
     * java.lang.String)
     */
    @Override
    public Reference<Resolver> selectAttribute(String name) {
      if (PREFIX.equals(name)) {
        return new PrefixReference(context, prefixSize);
      } else {
        return new ContextReplacingReference(this, context.selectAttribute(name));
      }
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
     * lang.String)
     */
    @Override
    public Reference<Resolver> selectItem(String index) {
      return new ContextReplacingReference(this, context.selectItem(index));
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
     * zuan.parser.codehaus.preon.el.Expression)
     */
    @Override
    public Reference<Resolver> selectItem(Expression<Integer, Resolver> index) {
      return new ContextReplacingReference(this, context.selectItem(index));
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
     * tool.parser.codehaus.preon.el.Document)
     */
    @Override
    public void document(Document target) {
      target.text("either the prefix variable or ");
      context.document(target);
      target.text(" (" + context.getClass() + ")");
    }

    /**
     * The Class PrefixReference.
     */
    private static class PrefixReference implements Reference<Resolver> {

      /** The context. */
      private ReferenceContext<Resolver> context;

      /** The prefix size. */
      private int prefixSize;

      /**
       * Instantiates a new prefix reference.
       *
       * @param context
       *          the context
       * @param prefixSize
       *          the prefix size
       */
      public PrefixReference(ReferenceContext<Resolver> context, int prefixSize) {
        this.context = context;
        this.prefixSize = prefixSize;
      }

      /**
       * Gets the reference context.
       *
       * @return the reference context
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
      @Override
      public boolean isAssignableTo(Class< ? > type) {
        return Integer.class.isAssignableFrom(type);
      }

      /**
       * Resolve.
       *
       * @param resolver
       *          the resolver
       * @return the object
       */
      @Override
      public Object resolve(Resolver resolver) {
        return resolver.get(PREFIX);
      }

      /**
       * Select attribute.
       *
       * @param name
       *          the name
       * @return the reference
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
      @Override
      public void document(Document target) {
        target.text("the value of the first ");
        target.text(Integer.toString(prefixSize));
        target.text(" bits");
      }

      /**
       * Gets the type.
       *
       * @return the type
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
      @Override
      public boolean isBasedOn(ReferenceContext<Resolver> resolverReferenceContext) {
        return context.equals(resolverReferenceContext);
      }

      /**
       * Rescope.
       *
       * @param context
       *          the context
       * @return the reference
       */
      @Override
      public Reference<Resolver> rescope(ReferenceContext<Resolver> context) {
        return this;
      }

    }

  }

  /**
   * The Class PrefixResolver.
   */
  private static class PrefixResolver implements Resolver {

    /** The resolver. */
    private Resolver resolver;

    /** The prefix. */
    private int prefix;

    /**
     * Instantiates a new prefix resolver.
     *
     * @param resolver
     *          the resolver
     * @param prefix
     *          the prefix
     */
    public PrefixResolver(Resolver resolver, int prefix) {
      this.resolver = resolver;
      this.prefix = prefix;
    }

    /**
     * Gets the.
     *
     * @param name
     *          the name
     * @return the object
     */
    @Override
    public Object get(String name) {
      if (PrefixResolverContext.PREFIX.equals(name)) {
        return prefix;
      } else {
        return resolver.get(name);
      }
    }

    /**
     * Gets the original resolver.
     *
     * @return the original resolver
     */
    @Override
    public Resolver getOriginalResolver() {
      return this;
    }

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
          target.para()
              .text("The particular type of data structure is selected based on the value of "
                  + prefixSize + " leading bits.")
              .text(" These bits are interpreted as an unsigned int.")
              .text(
                  " The table below lists the conditions, and the data structure assumed when these conditions are met.")
              .end();
          Table2Cols< ? > table2Cols = target.table2Cols();
          table2Cols.header().entry().para().text("Condition").end().entry().para()
              .text("Data structure").end().end();
          for (int i = 0; i < conditions.size(); i++) {
            table2Cols.row().entry().para()
                .document(Documenters.forExpression(conditions.get(i))).end().entry().para()
                .document(codecs.get(i).getCodecDescriptor().reference(Adjective.A, false))
                .end().end();
          }
          table2Cols.end();
        };
      }

      @Override
      public String getTitle() {
        return null;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(final Adjective adjective,
          boolean startWithCapital) {
        return target -> {
          if (conditions.size() > 3) {
            switch (adjective) {
            case A:
              target.text("a data structure selected from a list of " + conditions.size());
              break;
            case THE:
              target.text("the data structure selected from a list of " + conditions.size());
              break;
            case NONE:
              target.text("either one of " + conditions.size());
              break;
            }
          } else {
            for (int i = 0; i < conditions.size(); i++) {
              target.document(codecs.get(i).getCodecDescriptor().reference(adjective, false));
              if (i < conditions.size() - 2) {
                target.text(", ");
              }
              if (i == conditions.size() - 2) {
                target.text(" or ");
              }
            }
          }
        };
      }

      @Override
      public boolean requiresDedicatedSection() {
        return false;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> summary() {
        return target -> target.document(reference(Adjective.A, false)).text(".");
      }

    };
  }
}
