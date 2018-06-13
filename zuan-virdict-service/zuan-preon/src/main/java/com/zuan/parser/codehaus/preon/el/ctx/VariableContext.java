/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ctx;

import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * The Class VariableContext.
 *
 * @author zuan_
 */
public class VariableContext implements ReferenceContext<VariableResolver> {

  /** The defs. */
  private VariableDefinitions defs;

  /**
   * Instantiates a new variable context.
   *
   * @param defs
   *          the defs
   */
  public VariableContext(VariableDefinitions defs) {
    this.defs = defs;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
   * java.lang.String)
   */
  @Override
  public Reference<VariableResolver> selectAttribute(String name) {
    return new VariableReference(name, this);
  }

  /**
   * The Class VariableReference.
   */
  private static class VariableReference implements Reference<VariableResolver> {

    /** The name. */
    private String name;

    /** The context. */
    private VariableContext context;

    /**
     * Instantiates a new variable reference.
     *
     * @param name
     *          the name
     * @param context
     *          the context
     */
    public VariableReference(String name, VariableContext context) {
      this.name = name;
      this.context = context;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
     */
    @Override
    public Object resolve(VariableResolver context) {
      return context.get(name);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
     * java.lang.String)
     */
    @Override
    public Reference<VariableResolver> selectAttribute(String name) {
      return new PropertyReference<>(this, context.defs.getType(this.name), name, context);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
     * lang.String)
     */
    @Override
    public Reference<VariableResolver> selectItem(String index) {
      throw new BindingException("No indexed values defined for VariableContext.");
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
     * zuan.parser.codehaus.preon.el.Expression)
     */
    @Override
    public Reference<VariableResolver> selectItem(
        Expression<Integer, VariableResolver> index) {
      throw new BindingException("No indexed values defined for VariableContext.");
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((context == null) ? 0 : context.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
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
      VariableReference other = (VariableReference) obj;
      if (context == null) {
        if (other.context != null) {
          return false;
        }
      } else if (!context.equals(other.context)) {
        return false;
      }
      if (name == null) {
        if (other.name != null) {
          return false;
        }
      } else if (!name.equals(other.name)) {
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
      target.text("the " + name);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#getReferenceContext()
     */
    @Override
    public ReferenceContext<VariableResolver> getReferenceContext() {
      return context;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
     * Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean isAssignableTo(Class< ? > type) {
      return context.defs.getType(name).isAssignableFrom(type);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#getType()
     */
    @Override
    public Class< ? > getType() {
      return context.defs.getType(name);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Reference<VariableResolver> narrow(Class< ? > type) {
      if (context.defs.getType(name).isAssignableFrom(type)) {
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
    public boolean isBasedOn(ReferenceContext<VariableResolver> other) {
      return context.equals(other);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
     * parser.codehaus.preon.el.ReferenceContext)
     */
    @Override
    public Reference<VariableResolver> rescope(
        ReferenceContext<VariableResolver> variableResolverReferenceContext) {
      return this;
    }

  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<VariableResolver> selectItem(String index) {
    throw new BindingException("Index not supported");
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
   * zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<VariableResolver> selectItem(Expression<Integer, VariableResolver> index) {
    throw new BindingException("Index not supported");
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    defs.document(target);
  }

}
