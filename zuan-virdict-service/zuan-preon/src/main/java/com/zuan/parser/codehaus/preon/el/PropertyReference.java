/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import java.lang.reflect.Field;

import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.rendering.CamelCaseRewriter;
import com.zuan.parser.codehaus.preon.rendering.IdentifierRewriter;

/**
 * A {@link Reference} to a property. (And in this case, property means a
 * private field, not a bean property.)
 */
public class PropertyReference implements Reference<Resolver> {

  /** The rewriter. */
  private static IdentifierRewriter rewriter = new CamelCaseRewriter(false);

  /** The field representing the property. */
  private Field field;

  /**
   * The {@link Reference} to the object this {@link PropertyReference} is based
   * upon.
   */
  private Reference<Resolver> reference;

  /** The context for constructing references. */
  private ReferenceContext<Resolver> context;

  /** Include the type in the description generated. */
  private boolean includeType = true;

  /**
   * Constructs a new {@link Reference}.
   *
   * @param reference
   *          The source for this property.
   * @param type
   *          The type of the source.
   * @param name
   *          The name of the property.
   * @param context
   *          The original context, to be used when constructing other
   *          references.
   */
  public PropertyReference(Reference<Resolver> reference, Class< ? > type, String name,
      ReferenceContext<Resolver> context) {
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
   * @param type
   *          the type
   * @param name
   *          the name
   * @param context
   *          the context
   * @param includeType
   *          the include type
   */
  public PropertyReference(Reference<Resolver> reference, Class< ? > type, String name,
      ReferenceContext<Resolver> context, boolean includeType) {
    this(reference, type, name, context);
    this.includeType = includeType;
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
  private PropertyReference(Reference<Resolver> reference, Field field,
      ReferenceContext<Resolver> context) {
    this.reference = reference;
    this.field = field;
    this.context = context;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */
  @Override
  public Object resolve(Resolver context) {
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
  public Reference<Resolver> selectAttribute(String name) {
    return new PropertyReference(this, this.getType(), name, context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<Resolver> selectItem(String index) {
    try {
      Expression<Integer, Resolver> expr = Expressions.createInteger(context, index);
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
  public Reference<Resolver> selectItem(Expression<Integer, Resolver> index) {
    Class< ? > type = this.field.getType();
    return new ArrayElementReference(this, type.getComponentType(), index, context);
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
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((field == null) ? 0 : field.hashCode());
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PropertyReference other = (PropertyReference) obj;
    if (field == null) {
      if (other.field != null) {
        return false;
      }
    } else if (!field.equals(other.field)) {
      return false;
    }
    if (reference == null) {
      if (other.reference != null) {
        return false;
      }
    } else if (!reference.equals(other.reference)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    target.text("the " + rewriter.rewrite(field.getName()));
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
  public ReferenceContext<Resolver> getReferenceContext() {
    return context;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
   * Class)
   */
  @Override
  public boolean isAssignableTo(Class< ? > type) {
    return type.isAssignableFrom(field.getType());
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<Resolver> narrow(Class< ? > type) {
    if (type.isAssignableFrom(field.getType())) {
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
  public boolean isBasedOn(ReferenceContext<Resolver> context) {
    return this.context.equals(context);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Reference<Resolver> rescope(ReferenceContext<Resolver> context) {
    return new PropertyReference(reference.rescope(context), field, context);
  }

}
