/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zuan.parser.codehaus.preon.Resolver;

/**
 * The Class ImportSupportingObjectResolverContext.
 *
 * @author zuan_
 */
public class ImportSupportingObjectResolverContext implements ObjectResolverContext {

  /** The context. */
  private ObjectResolverContext context;

  /** The references. */
  private Map<String, Reference<Resolver>> references;

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ObjectResolverContext#add(java.lang
   * .String, com.zuan.parser.codehaus.preon.el.Binding)
   */
  @Override
  public void add(String name, com.zuan.parser.codehaus.preon.binding.Binding binding) {
    context.add(name, binding);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ObjectResolverContext#getBindings()
   */
  @Override
  public List<com.zuan.parser.codehaus.preon.binding.Binding> getBindings() {
    return context.getBindings();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ObjectResolverContext#getResolver(
   * java.lang.Object, com.zuan.parser.codehaus.preon.Resolver)
   */
  @Override
  public Resolver getResolver(Object context, Resolver resolver) {
    return this.context.getResolver(context, resolver);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
   * java.lang.String)
   */
  @Override
  public Reference<Resolver> selectAttribute(String name) throws BindingException {
    if (references.containsKey(name)) {
      return references.get(name);
    } else {
      return context.selectAttribute(name);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<Resolver> selectItem(String expr) throws BindingException {
    throw new BindingException("No indexes supported.");
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
   * zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<Resolver> selectItem(Expression<Integer, Resolver> expr)
      throws BindingException {
    throw new BindingException("No indexes supported.");
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document doc) {
    // Not expected to be called
  }

  /**
   * Decorate.
   *
   * @param context
   *          the context
   * @param type
   *          the type
   * @return the object resolver context
   */
  public static ObjectResolverContext decorate(ObjectResolverContext context,
      Class< ? > type) {
    if (type.isAnnotationPresent(ImportStatic.class)) {
      ImportSupportingObjectResolverContext replacement =
          new ImportSupportingObjectResolverContext();
      Map<String, Reference<Resolver>> references = new HashMap<>();
      for (Class< ? > imported : type.getAnnotation(ImportStatic.class).value()) {
        references.put(imported.getSimpleName(), new ClassReference(imported, replacement));
      }
      replacement.context = context;
      replacement.references = references;
      return replacement;
    } else {
      return context;
    }
  }

  /**
   * The Class ClassReference.
   */
  public static class ClassReference implements Reference<Resolver> {

    /** The imported. */
    private final Class< ? > imported;

    /** The context. */
    private ReferenceContext<Resolver> context;

    /**
     * Instantiates a new class reference.
     *
     * @param imported
     *          the imported
     * @param context
     *          the context
     */
    public ClassReference(Class< ? > imported, ReferenceContext<Resolver> context) {
      this.imported = imported;
      this.context = context;
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
     * @see com.zuan.parser.codehaus.preon.el.Reference#getType()
     */
    @Override
    public Class< ? > getType() {
      return imported;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
     * Class)
     */
    @Override
    public boolean isAssignableTo(Class< ? > other) {
      return false;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
     */
    @Override
    public Reference<Resolver> narrow(Class< ? > other) {
      return this; // Forgot how to implement this
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#isBasedOn(com.virdict.
     * tool.parser.codehaus.preon.el.ReferenceContext)
     */
    @Override
    public boolean isBasedOn(ReferenceContext<Resolver> resolverReferenceContext) {
      return true;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
     * parser.codehaus.preon.el.ReferenceContext)
     */
    @Override
    public Reference<Resolver> rescope(ReferenceContext<Resolver> resolverReferenceContext) {
      return this;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
     */
    @Override
    public Object resolve(Resolver resolver) {
      return imported;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
     * java.lang.String)
     */
    @Override
    public Reference<Resolver> selectAttribute(String name) throws BindingException {
      Field fld = null;
      try {
        fld = imported.getField(name);
        if (fld == null || !Modifier.isStatic(fld.getModifiers())) {
          throw new BindingException(
              "Class " + imported.getSimpleName() + " does not define field " + name);
        } else {
          fld.setAccessible(true);
          return new StaticFieldReference(fld, context);
        }
      } catch (SecurityException e) {
        throw new BindingException("Not allowed to access ");
      } catch (NoSuchFieldException e) {
        throw new BindingException("No attribute called " + name + " defined.");
      }
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
     * lang.String)
     */
    @Override
    public Reference<Resolver> selectItem(String expr) throws BindingException {
      throw new BindingException("No indexed values on class " + imported.getSimpleName());
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
     * zuan.parser.codehaus.preon.el.Expression)
     */
    @Override
    public Reference<Resolver> selectItem(Expression<Integer, Resolver> expr)
        throws BindingException {
      throw new BindingException("No indexed values on class " + imported.getSimpleName());
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
     * tool.parser.codehaus.preon.el.Document)
     */
    @Override
    public void document(Document document) {
      // Not expected to be called
      assert false;
    }

  }

  /**
   * The Class StaticFieldReference.
   */
  private static class StaticFieldReference implements Reference<Resolver> {

    /** The fld. */
    private final Field fld;

    /** The context. */
    private final ReferenceContext<Resolver> context;

    /**
     * Instantiates a new static field reference.
     *
     * @param fld
     *          the fld
     * @param context
     *          the context
     */
    public StaticFieldReference(Field fld, ReferenceContext<Resolver> context) {
      this.fld = fld;
      this.context = context;
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
     * @see com.zuan.parser.codehaus.preon.el.Reference#getType()
     */
    @Override
    public Class< ? > getType() {
      return fld.getType();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
     * Class)
     */
    @Override
    public boolean isAssignableTo(Class< ? > other) {
      return other.isAssignableFrom(fld.getType());
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
     */
    @Override
    public Reference<Resolver> narrow(Class< ? > other) {
      return this;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#isBasedOn(com.virdict.
     * tool.parser.codehaus.preon.el.ReferenceContext)
     */
    @Override
    public boolean isBasedOn(ReferenceContext<Resolver> resolverReferenceContext) {
      return true;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
     * parser.codehaus.preon.el.ReferenceContext)
     */
    @Override
    public Reference<Resolver> rescope(ReferenceContext<Resolver> resolverReferenceContext) {
      return this;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
     */
    @Override
    public Object resolve(Resolver resolver) {
      try {
        return fld.get(null);
      } catch (IllegalArgumentException e) {
        throw new BindingException("Failed to resolve field value.", e);
      } catch (IllegalAccessException e) {
        throw new BindingException("Failed to resolve field value.", e);
      }
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
     * java.lang.String)
     */
    @Override
    public Reference<Resolver> selectAttribute(String name) throws BindingException {
      throw new BindingException("No more attributes supported.");
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
     * lang.String)
     */
    @Override
    public Reference<Resolver> selectItem(String expr) throws BindingException {
      throw new BindingException("No indexes supported.");
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
     * zuan.parser.codehaus.preon.el.Expression)
     */
    @Override
    public Reference<Resolver> selectItem(Expression<Integer, Resolver> arg0)
        throws BindingException {
      throw new BindingException("No indexes supported.");
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
     * tool.parser.codehaus.preon.el.Document)
     */
    @Override
    public void document(Document doc) {
      doc.text(fld.getName());
    }

  }

}
