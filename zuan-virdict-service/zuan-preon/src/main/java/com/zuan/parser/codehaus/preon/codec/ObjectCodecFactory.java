/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecConstructionException;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.CodecSelector;
import com.zuan.parser.codehaus.preon.CodecSelectorFactory;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.Bound;
import com.zuan.parser.codehaus.preon.annotation.BoundObject;
import com.zuan.parser.codehaus.preon.binding.Binding;
import com.zuan.parser.codehaus.preon.binding.BindingFactory;
import com.zuan.parser.codehaus.preon.binding.StandardBindingFactory;
import com.zuan.parser.codehaus.preon.el.ImportSupportingObjectResolverContext;
import com.zuan.parser.codehaus.preon.el.ObjectResolverContext;
import com.zuan.parser.codehaus.preon.rendering.ClassNameRewriter;
import com.zuan.parser.codehaus.preon.rendering.IdentifierRewriter;
import com.zuan.parser.codehaus.preon.util.HidingAnnotatedElement;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;

/** The {@link CodecFactory} for {@link ObjectCodec}s. */
public class ObjectCodecFactory implements CodecFactory {

  /**
   * The object that will be used to construct
   * {@link org.codehaus.preon.binding.Binding} instances.
   */
  private BindingFactory bindingFactory;

  /**
   * The object that will be used to construct the appropriate
   * {@link org.codehaus.preon.Codec} instance. (In order to have coverage for
   * all fields defined.)
   */
  private CodecFactory codecFactory;

  /**
   * The object used to turn Java identifiers into something that is potentially
   * readable by humans.
   */
  private IdentifierRewriter rewriter = new ClassNameRewriter();

  /**
   * Constructs a new instance, using a default mechanism for constructing
   * {@link org.codehaus.preon.binding.Binding} instances.
   */
  public ObjectCodecFactory() {
    bindingFactory = new StandardBindingFactory();
  }

  /**
   * Constructs a new instance, using a default mechanism for constructing
   * {@link org.codehaus.preon.binding.Binding} instances.
   *
   * @param codecFactory
   *          The {@link org.codehaus.preon.CodecFactory} used to create
   *          <code>Codecs</code>.
   */
  public ObjectCodecFactory(CodecFactory codecFactory) {
    bindingFactory = new StandardBindingFactory();
    this.codecFactory = codecFactory;
  }

  /**
   * Constructs a new instance.
   *
   * @param codecFactory
   *          The object used to create <code>Codecs</code>.
   * @param bindingFactory
   *          The object used to create <code>Bindings</code>.
   */
  public ObjectCodecFactory(CodecFactory codecFactory, BindingFactory bindingFactory) {
    this.codecFactory = codecFactory;
    this.bindingFactory = bindingFactory;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.codehaus.preon.CodecFactory#create(java.lang.reflect.AnnotatedElement,
   * java.lang.Class, org.codehaus.preon.ResolverContext)
   */

  @Override
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    if (metadata == null || metadata.isAnnotationPresent(Bound.class)) {
      return createCodec(type, context);
    } else if (metadata.isAnnotationPresent(BoundObject.class)) {
      return createCodec(type, context, metadata);
    }
    return null;
  }

  /**
   * Creates a new ObjectCodec object.
   *
   * @param <T>
   *          the generic type
   * @param type
   *          the type
   * @param context
   *          the context
   * @return the object codec< t>
   */
  private <T> ObjectCodec<T> createCodec(Class<T> type, ResolverContext context) {
    ObjectResolverContext passThroughContext = new BindingsContext(context);
    passThroughContext =
        ImportSupportingObjectResolverContext.decorate(passThroughContext, type);
    CodecReference reference = new CodecReference();
    harvestBindings(type, passThroughContext, reference);
    if (passThroughContext.getBindings().isEmpty()) {
      throw new CodecConstructionException(
          "Failed to find a single bound field on " + type.getName());
    }
    ObjectCodec<T> result = new ObjectCodec<>(type, rewriter, passThroughContext);
    reference.setCodec(result);
    return result;
  }

  /**
   * Creates a new ObjectCodec object.
   *
   * @param <T>
   *          the generic type
   * @param type
   *          the type
   * @param context
   *          the context
   * @param metadata
   *          the metadata
   * @return the codec< t>
   */
  @SuppressWarnings({"unchecked", "rawtypes" })
  private <T> Codec<T> createCodec(Class<T> type, ResolverContext context,
      AnnotatedElement metadata) {
    BoundObject settings = metadata.getAnnotation(BoundObject.class);
    if (Void.class.equals(settings.type())) {
      if (settings.selectFrom().alternatives().length > 0
          || settings.selectFrom().defaultType() != Void.class) {
        return new SelectFromCodec(type, settings.selectFrom(), context, codecFactory,
            hideChoices(metadata));
      }
      if (settings.types().length == 0) {
        return createCodec(type, context);
      }
      List<Codec< ? >> codecs = new ArrayList<>();
      for (Class valueType : settings.types()) {
        codecs.add(codecFactory.create(null, valueType, context));
      }
      CodecSelectorFactory selectorFactory;
      selectorFactory = new TypePrefixSelectorFactory();
      CodecSelector selector = selectorFactory.create(context, codecs);
      return (Codec<T>) new SwitchingCodec(selector);
    } else {
      return (Codec<T>) createCodec(settings.type(), context);
    }
  }

  /**
   * Hide choices.
   *
   * @param metadata
   *          the metadata
   * @return the annotated element
   */
  private AnnotatedElement hideChoices(AnnotatedElement metadata) {
    return new HidingAnnotatedElement(BoundObject.class, metadata);
  }

  /**
   * Harvest bindings.
   *
   * @param <T>
   *          the generic type
   * @param type
   *          the type
   * @param context
   *          the context
   * @param reference
   *          the reference
   */
  private <T> void harvestBindings(Class<T> type, ObjectResolverContext context,
      CodecReference reference) {
    if (Object.class.equals(type)) {
      return;
    }
    harvestBindings(type.getSuperclass(), context, reference);
    Field[] fields = type.getDeclaredFields();
    // For creating the Codecs, we already need a modified
    // ReferenceContext, allowing us to incrementally bind to references
    // of fields declared before.
    for (Field field : fields) {
      if (!Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()) {
        Codec< ? > codec = codecFactory.create(field, field.getType(), context);
        if (codec != null) {
          Binding binding = bindingFactory.create(field, field, codec, context, reference);
          context.add(field.getName(), binding);
        }
      }
    }
  }

  /**
   * The Class CodecReference.
   */
  private static class CodecReference implements Documenter<ParaContents< ? >> {

    /** The codec. */
    private Codec< ? > codec;

    /*
     * (non-Javadoc)
     * @see nl.flotsam.pecia.Documenter#document(java.lang.Object)
     */
    @Override
    public void document(ParaContents< ? > target) {
      target.document(
          codec.getCodecDescriptor().reference(CodecDescriptor.Adjective.THE, false));
    }

    /**
     * Sets the codec.
     *
     * @param codec
     *          the new codec
     */
    public void setCodec(Codec< ? > codec) {
      this.codec = codec;
    }

  }

}
