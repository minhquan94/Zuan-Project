/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * The Class AnnotationWrapper.
 *
 * @author zuan_
 */
public class AnnotationWrapper implements AnnotatedElement {

  /** The annotation. */
  private Annotation annotation;

  /**
   * Instantiates a new annotation wrapper.
   *
   * @param <E>
   *          the element type
   * @param annotation
   *          the annotation
   */
  public <E extends Annotation> AnnotationWrapper(E annotation) {
    this.annotation = annotation;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getAnnotation(java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
    if (annotationType.isAssignableFrom(annotation.getClass())) {
      return (T) annotation;
    } else {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getAnnotations()
   */
  @Override
  public Annotation[] getAnnotations() {
    return new Annotation[]{annotation };
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getDeclaredAnnotations()
   */
  @Override
  public Annotation[] getDeclaredAnnotations() {
    return new Annotation[]{annotation };
  }

  /*
   * (non-Javadoc)
   * @see
   * java.lang.reflect.AnnotatedElement#isAnnotationPresent(java.lang.Class)
   */
  @Override
  public boolean isAnnotationPresent(Class< ? extends Annotation> annotationType) {
    return annotationType.isAssignableFrom(annotation.getClass());
  }

}
