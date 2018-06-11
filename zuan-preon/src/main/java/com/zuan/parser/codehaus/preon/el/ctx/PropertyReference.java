/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ctx;

import java.lang.reflect.Field;

import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.InvalidExpressionException;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * The Class PropertyReference.
 *
 * @author zuan_
 * @param <T>
 *          the generic type
 */
public class PropertyReference<T> implements Reference<T> {

  /** The field. */
  private Field field;

  /** The reference. */
  private Reference<T> reference;

  /** The context. */
  private ReferenceContext<T> context;

  /**
   * Include the type in the description generated.
   */
  private boolean includeType = true;

  /**
   * Instantiates a new property reference.
   *
   * @param reference
   *          the reference
   * @param type
   *          the type
   * @param name
   *          the name
   * @param context
   *          the context
   */
  public PropertyReference(Reference<T> reference, Class< ? > type, String name,
      ReferenceContext<T> context) {
    this.reference = reference;
    this.context = context;
    try {
      field = type.getDeclaredField(name);
      field.setAccessible(true);
    } catch (SecurityException e) {
      throw new BindingException("Binding to " + name + " forbidden.");
    } catch (NoSuchFieldException e) {
      throw new BindingException("No field named " + name + ".");
    }
  }

  /**
   * Instantiates a new property reference.
   *
   * @param reference
   *          the reference
   * @param field
   *          the field
   * @param context
   *          the context
   */
  private PropertyReference(Reference<T> reference, Field field, ReferenceContext<T> context) {
    this.reference = reference;
    this.field = field;
    this.context = context;
  }

  /**
   * Instantiates a new property reference.
   *
   * @param reference
   *          the reference
   * @param type
   *          the type
   * @param name
   *          the name
   * @param context
   *          the context
   * @param includeType
   *          the include type
   */
  public PropertyReference(Reference<T> reference, Class< ? > type, String name,
      ReferenceContext<T> context, boolean includeType) {
    this(reference, type, name, context);
    this.includeType = includeType;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */
  @Override
  public Object resolve(T context) {
    try {
      return field.get(reference.resolve(context));
    } catch (IllegalArgumentException e) {
      throw new BindingException("Cannot resolve " + field.getName() + " on context.", e);
    } catch (IllegalAccessException e) {
      throw new BindingException("Access denied for field  " + field.getName(), e);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
   * java.lang.String)
   */
  @Override
  public Reference<T> selectAttribute(String name) {
    return new PropertyReference<>(this, this.getType(), name, context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<T> selectItem(String index) {
    try {
      Expression<Integer, T> expr = Expressions.createInteger(context, index);
      return selectItem(expr);
    } catch (InvalidExpressionException e) {
      throw new BindingException("Invalid index.", e);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
   * zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<T> selectItem(Expression<Integer, T> index) {
    Class< ? > type = this.field.getType();
    return new ArrayElementReference<>(this, type.getComponentType(), index, context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getType()
   */
  @Override
  public Class< ? > getType() {
    return field.getType();
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (other instanceof PropertyReference) {
      return equals((PropertyReference<T>) other);
    } else {
      return false;
    }
  }

  /**
   * Equals.
   *
   * @param other
   *          the other
   * @return true, if successful
   */
  public boolean equals(PropertyReference<T> other) {
    return field.equals(other.field) && reference.equals(other.reference);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    target.text("the " + field.getName());
    if (includeType) {
      target.text(" (a ");
      target.text(getType().getSimpleName());
      target.text(") ");
    } else {
      target.text(" ");
    }
    target.text("of ");
    reference.document(target);

  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getReferenceContext()
   */
  @Override
  public ReferenceContext<T> getReferenceContext() {
    return context;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
   * Class)
   */
  @Override
  public boolean isAssignableTo(Class< ? > type) {
    return field.getType().isAssignableFrom(type);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<T> narrow(Class< ? > type) {
    if (field.getType().isAssignableFrom(type)) {
      return this;
    } else {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isBasedOn(com.virdict.
   * tool.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isBasedOn(ReferenceContext<T> context) {
    return reference.isBasedOn(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Reference<T> rescope(ReferenceContext<T> other) {
    return new PropertyReference<>(reference.rescope(other), field, this.context);
  }

}
