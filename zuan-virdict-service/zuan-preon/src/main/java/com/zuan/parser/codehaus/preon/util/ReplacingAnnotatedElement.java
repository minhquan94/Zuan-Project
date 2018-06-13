/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * An {@link AnnotatedElement} wrapper, replacing one of the annotations with
 * another one.
 */
public class ReplacingAnnotatedElement implements AnnotatedElement {

  /** The replacement annotation. */
  private Annotation replacement;

  /** The other annotations. */
  private AnnotatedElement wrapped;

  /**
   * Instantiates a new replacing annotated element.
   *
   * @param wrapped
   *          the wrapped
   * @param replacement
   *          the replacement
   */
  public ReplacingAnnotatedElement(AnnotatedElement wrapped, Annotation replacement) {
    this.wrapped = wrapped;
    this.replacement = replacement;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getAnnotation(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
    if (annotationType.isAssignableFrom(replacement.getClass())) {
      return (T) replacement;
    } else {
      return wrapped.getAnnotation(annotationType);
    }
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getAnnotations()
   */

  @Override
  public Annotation[] getAnnotations() {
    Annotation[] annotations = wrapped.getAnnotations();
    for (int i = 0; i < annotations.length; i++) {
      if (annotations[i].getClass().isAssignableFrom(replacement.getClass())) {
        annotations[i] = replacement;
      }
    }
    return annotations;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getDeclaredAnnotations()
   */

  @Override
  public Annotation[] getDeclaredAnnotations() {
    Annotation[] annotations = wrapped.getDeclaredAnnotations();
    for (int i = 0; i < annotations.length; i++) {
      if (annotations[i].getClass() == replacement.getClass()) {
        annotations[i] = replacement;
      }
    }
    return annotations;
  }

  /*
   * (non-Javadoc)
   * @see
   * java.lang.reflect.AnnotatedElement#isAnnotationPresent(java.lang.Class)
   */

  @Override
  public boolean isAnnotationPresent(Class< ? extends Annotation> annotationType) {
    if (wrapped.isAnnotationPresent(annotationType)) {
      return true;
    } else {
      return replacement.getClass() == annotationType;
    }
  }

}
