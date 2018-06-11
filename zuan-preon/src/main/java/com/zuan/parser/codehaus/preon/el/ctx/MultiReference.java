/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ctx;

import java.util.ArrayList;
import java.util.List;

import com.zuan.parser.codehaus.preon.el.BindingException;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Reference;
import com.zuan.parser.codehaus.preon.el.ReferenceContext;
import com.zuan.parser.codehaus.preon.el.util.ClassUtils;

/**
 * A reference encapsulating a number of references. The reference can be
 * treated as a single reference. Trying to resolve a property by name or a
 * value by index will result in a new {@link MultiReference}. There may cases
 * in which {@link #selectAttribute(String)} will throw {@link BindingException}
 * s for some of the references managed by this {@link MultiReference}. In those
 * cases, the policy of this implementation is to skip these references, and
 * proceed with the others.
 *
 * @param <E>
 *          The type of context expected by the {@link Reference} when resolving
 *          values.
 */
@SuppressWarnings({"rawtypes", "unchecked" })
public class MultiReference<E> implements Reference<E> {

  /** The reference array type. */
  private static Reference[] REFERENCE_ARRAY_TYPE = new Reference[0];

  /**
   * A collection of references.
   */
  private Reference<E>[] references;

  /**
   * The (common) {@link ReferenceContext}.
   */
  private ReferenceContext<E> context;

  /**
   * The common supertype of anything that can be resolved through this
   * reference.
   */
  private Class< ? > commonSuperType;

  /**
   * Constructs a new instance, accepting the references this single reference
   * needs to be constructed from.
   *
   * @param references
   *          the references
   */
  public MultiReference(Reference<E>... references) {
    commonSuperType = calculateCommonSuperType(references);
    this.references = references;
    for (Reference<E> reference : references) {
      if (context != null) {
        if (!context.equals(reference.getReferenceContext())) {
          throw new BindingException("Multiple types of runtime contexts.");
        }
      } else {
        context = reference.getReferenceContext();
      }
    }
  }

  /**
   * Calculate common super type.
   *
   * @param <E>
   *          the element type
   * @param references
   *          the references
   * @return the class
   */
  private static <E> Class< ? > calculateCommonSuperType(Reference<E>... references) {
    Class< ? >[] types = new Class< ? >[references.length];
    for (int i = 0; i < references.length; i++) {
      types[i] = references[i].getType();
    }
    return ClassUtils.calculateCommonSuperType(types);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getReferenceContext()
   */
  @Override
  public ReferenceContext<E> getReferenceContext() {
    return context;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#resolve(java.lang.Object)
   */
  @Override
  public Object resolve(E context) {
    for (Reference<E> reference : references) {
      try {
        return reference.resolve(context);
      } catch (BindingException rre) {
        // Let's try one more.
      }
    }
    throw new BindingException("Failed to resolve reference for all options.");
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<E> selectItem(String index) {
    List<Reference<E>> results = new ArrayList<>();
    for (Reference<E> reference : references) {
      results.add(reference.selectItem(index));
    }
    return new MultiReference<>(results.toArray(REFERENCE_ARRAY_TYPE));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
   * zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<E> selectItem(Expression<Integer, E> index) {
    List<Reference<E>> results = new ArrayList<>();
    for (Reference<E> reference : references) {
      results.add(reference.selectItem(index));
    }
    return new MultiReference<>(results.toArray(REFERENCE_ARRAY_TYPE));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
   * java.lang.String)
   */
  @Override
  public Reference<E> selectAttribute(String name) {
    List<Reference<E>> results = new ArrayList<>();
    for (Reference<E> reference : references) {
      try {
        results.add(reference.selectAttribute(name));
      } catch (BindingException e) {
        // This is ok, we are just not going to add this reference.
      }
    }
    return new MultiReference<>(results.toArray(REFERENCE_ARRAY_TYPE));
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    if (references.length > 1) {
      target.text("either ");
    }
    references[0].document(target);
    if (references.length > 2) {
      for (int i = 1; i <= references.length - 2; i++) {
        target.text(", ");
        references[i].document(target);
      }
    }
    if (references.length > 1) {
      target.text(" or ");
      references[references.length - 1].document(target);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isAssignableTo(java.lang.
   * Class)
   */
  @Override
  public boolean isAssignableTo(Class< ? > type) {
    for (Reference< ? > reference : references) {
      if (!reference.isAssignableTo(type)) {
        return false;
      }
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#getType()
   */
  @Override
  public Class< ? > getType() {
    return commonSuperType;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#narrow(java.lang.Class)
   */
  @Override
  public Reference<E> narrow(Class< ? > type) throws BindingException {
    List<Reference<E>> resulting = new ArrayList<>();
    for (Reference<E> reference : references) {
      Reference<E> result = reference.narrow(type);
      if (result != null) {
        resulting.add(result);
      }
    }
    if (resulting.size() == 0) {
      return null;
    } else {
      return new MultiReference<>(resulting.toArray(REFERENCE_ARRAY_TYPE));
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#isBasedOn(com.virdict.
   * tool.parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public boolean isBasedOn(ReferenceContext<E> context) {
    for (Reference<E> reference : references) {
      if (!reference.isBasedOn(context)) {
        return false;
      }
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Reference#rescope(com.zuan.
   * parser.codehaus.preon.el.ReferenceContext)
   */
  @Override
  public Reference<E> rescope(ReferenceContext<E> eReferenceContext) {
    Reference<E>[] replacements = new Reference[this.references.length];
    int i = 0;
    for (Reference<E> reference : references) {
      replacements[i] = reference.rescope(context);
    }
    return new MultiReference<>(replacements);
  }

}
