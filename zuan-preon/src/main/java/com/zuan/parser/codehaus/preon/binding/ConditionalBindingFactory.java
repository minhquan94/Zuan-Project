/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.binding;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Set;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.If;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.InvalidExpressionException;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A {@link BindingFactory} that wraps another {@link BindingFactory}, creating
 * {@link Binding Bindings} that are bound conditionally, depending on the
 * conditions of the {@link If} metadata defined for the Fields that are bound.
 */
public class ConditionalBindingFactory implements BindingFactory {

  /**
   * The {@link BindingFactory} creating the {@link Binding}s that will be
   * wrapped with {@link ConditionalBinding} instances.
   */
  private BindingFactory decorated;

  /**
   * Constructs a new instance, accepting the {@link BindingFactory} of the
   * {@link Binding Bindings} to decorate.
   *
   * @param decorated
   *          The {@link BindingFactory} of the {@link Binding Bindings} to
   *          decorate.
   */
  public ConditionalBindingFactory(BindingFactory decorated) {
    this.decorated = decorated;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.binding.BindingFactory#create(java.lang.reflect.
   * AnnotatedElement, java.lang.reflect.Field, org.codehaus.preon.Codec,
   * org.codehaus.preon.ResolverContext)
   */

  @Override
  public Binding create(AnnotatedElement metadata, Field field, Codec< ? > codec,
      ResolverContext context, Documenter<ParaContents< ? >> containerReference) {
    If condition = metadata.getAnnotation(If.class);
    if (condition != null) {
      Expression<Boolean, Resolver> expr = null;
      String value = condition.value();
      try {
        expr = Expressions.createBoolean(context, value);
        return new ConditionalBinding(expr,
            decorated.create(metadata, field, codec, context, containerReference));
      } catch (InvalidExpressionException e) {
        System.err.println("All wrong");
        throw e;
      }
    } else {
      return decorated.create(metadata, field, codec, context, containerReference);
    }
  }

  /**
   * The Class ConditionalBinding.
   */
  private static class ConditionalBinding implements Binding {

    /** The expr. */
    private Expression<Boolean, Resolver> expr;

    /** The binding. */
    private Binding binding;

    /**
     * Instantiates a new conditional binding.
     *
     * @param expr
     *          the expr
     * @param binding
     *          the binding
     */
    public ConditionalBinding(Expression<Boolean, Resolver> expr, Binding binding) {
      this.expr = expr;
      this.binding = binding;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.binding.Binding#load(java.lang.Object,
     * com.zuan.parser.codehaus.preon.buffer.BitBuffer,
     * com.zuan.parser.codehaus.preon.Resolver,
     * com.zuan.parser.codehaus.preon.Builder)
     */
    @Override
    public void load(Object object, BitBuffer buffer, Resolver resolver, Builder builder)
        throws DecodingException {
      if (expr.eval(resolver)) {
        binding.load(object, buffer, resolver, builder);
      }
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.binding.Binding#getTypes()
     */
    @Override
    public Class< ? >[] getTypes() {
      return binding.getTypes();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.binding.Binding#get(java.lang.Object)
     */
    @Override
    public Object get(Object context) throws IllegalArgumentException, IllegalAccessException {
      return binding.get(context);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.binding.Binding#getName()
     */
    @Override
    public String getName() {
      return binding.getName();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.binding.Binding#writeReference(nl.
     * flotsam.pecia.ParaContents)
     */
    @Override
    public <T, V extends ParaContents<T>> V writeReference(V contents) {
      return binding.writeReference(contents);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.binding.Binding#getSize()
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return new ConditionalValue(expr, binding.getSize());
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.binding.Binding#getId()
     */
    @Override
    public String getId() {
      return binding.getId();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.binding.Binding#getType()
     */
    @Override
    public Class< ? > getType() {
      return binding.getType();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.binding.Binding#save(java.lang.Object,
     * com.zuan.parser.codehaus.preon.channel.BitChannel,
     * com.zuan.parser.codehaus.preon.Resolver)
     */
    @Override
    public void save(Object value, BitChannel channel, Resolver resolver) throws IOException {
      if (expr.eval(resolver)) {
        binding.save(value, channel, resolver);
      }
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.binding.Binding#describe(nl.flotsam.
     * pecia.SimpleContents)
     */
    @Override
    public <V extends SimpleContents< ? >> V describe(V contents) {
      binding.describe(contents);
      contents.para().text("Only if ").document(Documenters.forExpression(expr)).text(".")
          .end();
      return contents;
    }

  }

  /**
   * The Class ConditionalValue.
   */
  private static class ConditionalValue implements Expression<Integer, Resolver> {

    /** The condition. */
    private Expression<Boolean, Resolver> condition;

    /** The expr. */
    private Expression<Integer, Resolver> expr;

    /**
     * Instantiates a new conditional value.
     *
     * @param condition
     *          the condition
     * @param expr
     *          the expr
     */
    public ConditionalValue(Expression<Boolean, Resolver> condition,
        Expression<Integer, Resolver> expr) {
      this.condition = condition;
      this.expr = expr;
    }

    /**
     * Eval.
     *
     * @param resolver
     *          the resolver
     * @return the integer
     * @throws BindingException
     *           the binding exception
     */
    @Override
    public Integer eval(Resolver resolver) throws BindingException {
      if (condition.eval(resolver)) {
        return expr.eval(resolver);
      } else {
        return 0;
      }
    }

    /**
     * Gets the references.
     *
     * @return the references
     */
    @Override
    public Set<Reference<Resolver>> getReferences() {
      return expr.getReferences();
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    /**
     * Checks if is parameterized.
     *
     * @return true, if is parameterized
     */
    @Override
    public boolean isParameterized() {
      return condition.isParameterized() || expr.isParameterized();
    }

    /**
     * Simplify.
     *
     * @return the expression
     */
    @Override
    public Expression<Integer, Resolver> simplify() {
      return new ConditionalValue(condition.simplify(), expr.simplify());
    }

    /**
     * Checks if is constant for.
     *
     * @param context
     *          the context
     * @return true, if is constant for
     */
    @Override
    public boolean isConstantFor(ReferenceContext<Resolver> context) {
      return condition.isConstantFor(context) && expr.isConstantFor(context);
    }

    /**
     * Rescope.
     *
     * @param context
     *          the context
     * @return the expression
     */
    @Override
    public Expression<Integer, Resolver> rescope(ReferenceContext<Resolver> context) {
      return new ConditionalValue(condition.rescope(context), expr.rescope(context));
    }

    /**
     * Document.
     *
     * @param document
     *          the document
     */
    @Override
    public void document(Document document) {
      expr.document(document);
      document.text(" if ");
      condition.document(document);
      document.text(" or else 0");
    }

  }

}
