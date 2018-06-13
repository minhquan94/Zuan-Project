/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.binding.Binding;
import com.zuan.parser.codehaus.preon.el.ArrayElementReference;
import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.ObjectResolverContext;
import com.zuan.parser.codehaus.preon.el.OuterReference;
import com.zuan.parser.codehaus.preon.el.PropertyReference;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;
import com.zuan.parser.codehaus.preon.el.ctx.MultiReference;
import com.zuan.parser.codehaus.preon.el.util.StringBuilderDocument;
import com.zuan.parser.codehaus.preon.util.ParaContentsDocument;

/**
 * A {@link ResolverContext} based on a collection of {@link Binding Bindings}.
 */
public class BindingsContext implements ObjectResolverContext {

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(BindingsContext.class);

  /** All {@link Binding}s. */
  private List<Binding> orderedBindings;

  /** All {@link Binding}s, indexed by their name. */
  private HashMap<String, Binding> bindingsByName;

  /** The "outer" {@link ResolverContext}. */
  private ResolverContext outer;

  /**
   * Constructs a new instance.
   *
   * @param outer
   *          The "outer" {@link ResolverContext}.
   */
  public BindingsContext(ResolverContext outer) {
    this.orderedBindings = new ArrayList<>();
    this.bindingsByName = new HashMap<>();
    this.outer = outer;
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.codehaus.preon.el.ObjectResolverContext#add(java.lang.String,
   *      com.zuan.parser.codehaus.preon.binding.Binding)
   */
  @Override
  public void add(String name, Binding binding) {
    orderedBindings.add(binding);
    bindingsByName.put(name, binding);
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(java.lang.String)
   */
  @Override
  public Reference<Resolver> selectAttribute(String name) {
    if ("outer".equals(name)) {
      return new OuterReference(outer, this);
    } else {
      Binding binding = bindingsByName.get(name);

      if (binding == null) {
        throw new BindingException("Failed to create binding for bound data called " + name);
      }
      return new BindingReference(binding);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.lang.String)
   */
  @Override
  public Reference<Resolver> selectItem(String index) {
    throw new BindingException("Cannot resolve index on BindingContext.");
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<Resolver> selectItem(Expression<Integer, Resolver> index) {
    StringBuilder builder = new StringBuilder();
    index.document(new StringBuilderDocument(builder));
    throw new BindingException("Cannot resolve index on BindingContext.");
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.zuan.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    if (bindingsByName.size() > 0) {
      target.text("one of ");
      boolean passedFirst = false;
      for (Binding binding : bindingsByName.values()) {
        if (passedFirst) {
          target.text(", ");
        }
        target.text(binding.getName());
        passedFirst = true;
      }
    } else {
      target.text("no variables");
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.codehaus.preon.el.ObjectResolverContext#getResolver(java.lang.Object,
   *      com.zuan.parser.codehaus.preon.Resolver)
   */
  @Override
  public Resolver getResolver(Object context, Resolver resolver) {
    return new BindingsResolver(context, resolver);
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.codehaus.preon.el.ObjectResolverContext#getBindings()
   */
  @Override
  public List<Binding> getBindings() {
    return orderedBindings;
  }

  /** A {@link Reference} referring to a {@link Binding}. */
  private class BindingReference implements Reference<Resolver> {

    /** The {@link Binding} it refers to. */
    private Binding binding;

    /**
     * The most specific supertype of anything that can be returned from this
     * {@link Reference}.
     */
    private Class< ? > commonType;

    /**
     * Constructs a new instance.
     *
     * @param binding
     *          The {@link Binding}.
     */
    public BindingReference(Binding binding) {
      this.binding = binding;
      commonType = binding.getType();
    }

    /**
     * Gets the reference context.
     *
     * @return the reference context
     */
    @Override
    public ResolverContext getReferenceContext() {
      return BindingsContext.this;
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

      for (Class< ? > bound : binding.getTypes()) {

        if (bound.isAssignableFrom(type)) {
          return true;
        }
      }

      return false;
    }

    /**
     * Resolve.
     *
     * @param context
     *          the context
     * @return the object
     */
    @Override
    public Object resolve(Resolver context) {
      try {
        String name = binding.getName();
        return context.get(name);
      } catch (IllegalArgumentException e) {
        throw new BindingException("Failed to bind to " + binding.getName(), e);
      }
    }

    /**
     * Select attribute.
     *
     * @param name
     *          the name
     * @return the reference
     */
    @Override
    @SuppressWarnings("unchecked")
    public Reference<Resolver> selectAttribute(String name) {
      Reference<Resolver>[] template = new Reference[0];
      List<Reference<Resolver>> references = new ArrayList<>();
      for (Class< ? > bound : binding.getTypes()) {
        try {
          references
              .add(new PropertyReference(this, bound, name, BindingsContext.this, false));
        } catch (BindingException be) {
          // Ok, let's skip this one.
          LOGGER.error("", be);
        }
      }
      if (references.isEmpty()) {
        throw new BindingException("Attribute " + name + " not defined for any type.");
      } else {
        return new MultiReference<>(references.toArray(template));
      }
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
      Expression<Integer, Resolver> expr;
      expr = Expressions.createInteger(BindingsContext.this, index);

      return selectItem(expr);
    }

    /**
     * Select item.
     *
     * @param index
     *          the index
     * @return the reference
     */
    @Override
    @SuppressWarnings("unchecked")
    public Reference<Resolver> selectItem(Expression<Integer, Resolver> index) {

      if (binding.getTypes().length > 1) {
        Reference<Resolver>[] references = new Reference[binding.getTypes().length];

        for (int i = 0; i < binding.getTypes().length; i++) {
          references[i] = new ArrayElementReference(this, binding.getTypes()[i], index,
              BindingsContext.this);
        }

        return new MultiReference<>(references);
      } else {
        return new ArrayElementReference(this, binding.getType().getComponentType(), index,
            BindingsContext.this);
      }
    }

    /**
     * Document.
     *
     * @param target
     *          the target
     */
    @SuppressWarnings("unchecked")
    @Override
    public void document(final Document target) {
      binding.writeReference(new ParaContentsDocument(target));
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class< ? > getType() {
      return commonType;
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
      if (type.isAssignableFrom(binding.getType())) {
        return this;
      } else {
        return null;
      }
    }

    /**
     * Checks if is based on.
     *
     * @param context
     *          the context
     * @return true, if is based on
     */
    @Override
    public boolean isBasedOn(ReferenceContext<Resolver> context) {
      return BindingsContext.this.equals(context);
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
      if (BindingsContext.this.equals(context)) {
        return this;
      } else {
        throw new IllegalStateException();
      }
    }

  }

  /**
   * A {@link Resolver} resolving to bindings. In addition, it also resolves
   * outer.
   *
   * @author Wilfred Springer (wis)
   */
  private class BindingsResolver implements Resolver {

    /** The instance on which the objects need to be resolved. */
    private Object context;

    /** The outer Resolver. */
    private Resolver outer;

    /**
     * Constructs a new instance.
     *
     * @param context
     *          The object for resolving bindings.
     * @param outer
     *          A reference to the outer context.
     */
    public BindingsResolver(Object context, Resolver outer) {
      this.context = context;
      this.outer = outer;
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
      if ("outer".equals(name)) {
        return outer;
      }
      if (context == null) {
        StringBuilderDocument document = new StringBuilderDocument();
        throw new BindingException(
            "Failed to resolve " + document.toString() + " due to incomplete context.");
      }

      if (!bindingsByName.containsKey(name)) {
        throw new BindingException("Failed to resolve " + name + " on " + context.getClass());
      }
      Binding binding = bindingsByName.get(name);
      try {
        return binding.get(context);
      } catch (IllegalArgumentException e) {
        throw new BindingException("Failed to bind to " + binding.getName(), e);
      } catch (IllegalAccessException e) {
        throw new BindingException("Forbidded to access " + binding.getName(), e);
      }
    }

    /**
     * Returns the {@link Resolver} from which all new references need to be
     * constructed.
     *
     * @return the original resolver
     */
    @Override
    public Resolver getOriginalResolver() {
      return this;
    }

  }

}
