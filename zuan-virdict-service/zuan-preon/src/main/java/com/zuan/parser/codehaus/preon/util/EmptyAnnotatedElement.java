/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * The Class EmptyAnnotatedElement.
 *
 * @author zuan_
 */
public class EmptyAnnotatedElement implements AnnotatedElement {

  /** The instance. */
  public static final EmptyAnnotatedElement INSTANCE = new EmptyAnnotatedElement();

  /**
   * Instantiates a new empty annotated element.
   */
  private EmptyAnnotatedElement() {

  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getAnnotation(java.lang.Class)
   */
  @Override
  public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getAnnotations()
   */
  @Override
  public Annotation[] getAnnotations() {
    return new Annotation[0];
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getDeclaredAnnotations()
   */
  @Override
  public Annotation[] getDeclaredAnnotations() {
    return new Annotation[0];
  }

  /*
   * (non-Javadoc)
   * @see
   * java.lang.reflect.AnnotatedElement#isAnnotationPresent(java.lang.Class)
   */
  @Override
  public boolean isAnnotationPresent(Class< ? extends Annotation> annotationType) {
    return false;
  }

}
