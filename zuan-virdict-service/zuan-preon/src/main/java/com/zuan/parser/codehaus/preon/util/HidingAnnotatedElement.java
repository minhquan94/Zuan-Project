/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * A wrapper of {@link AnnotatedElement}s, hiding certain annotations.
 */
public class HidingAnnotatedElement implements AnnotatedElement {

  /** The type of annotation that needs to be hidden. */
  private Class< ? extends Annotation> hidden;

  /** The {@link AnnotatedElement} to wrap. */
  private AnnotatedElement delegate;

  /**
   * Constructs a new instance, accepting the annotation that needs to be
   * hidden, as well as the {@link AnnotatedElement} that (probably) carries
   * that and other annotations.
   *
   * @param hidden
   *          The type of annotation that need to be hidden.
   * @param delegate
   *          The {@link AnnotatedElement} from which a certain type of
   *          annotation needs to be hidden.
   */
  public HidingAnnotatedElement(Class< ? extends Annotation> hidden,
      AnnotatedElement delegate) {
    this.hidden = hidden;
    this.delegate = delegate;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getAnnotation(java.lang.Class)
   */

  @Override
  public <T extends Annotation> T getAnnotation(Class<T> type) {
    if (hidden.equals(type)) {
      return null;
    } else {
      return delegate.getAnnotation(type);
    }
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getAnnotations()
   */

  @Override
  public Annotation[] getAnnotations() {
    if (delegate.isAnnotationPresent(hidden)) {
      Annotation[] unhidden = delegate.getAnnotations();
      Annotation[] result = new Annotation[unhidden.length - 1];
      int j = 0;
      for (int i = 0; i < unhidden.length; i++) {
        if (!hidden.equals(unhidden[i].getClass())) {
          result[j++] = unhidden[i];
        }
      }
      return result;
    } else {
      return delegate.getAnnotations();
    }
  }

  /*
   * (non-Javadoc)
   * @see java.lang.reflect.AnnotatedElement#getDeclaredAnnotations()
   */

  @Override
  public Annotation[] getDeclaredAnnotations() {
    if (delegate.isAnnotationPresent(hidden)) {
      Annotation[] unhidden = delegate.getDeclaredAnnotations();
      Annotation[] result = new Annotation[unhidden.length - 1];
      int j = 0;
      for (int i = 0; i < unhidden.length; i++) {
        if (!hidden.equals(unhidden[i].getClass())) {
          result[j++] = unhidden[i];
        }
      }
      return result;
    } else {
      return delegate.getDeclaredAnnotations();
    }
  }

  /*
   * (non-Javadoc)
   * @see
   * java.lang.reflect.AnnotatedElement#isAnnotationPresent(java.lang.Class)
   */

  @Override
  public boolean isAnnotationPresent(Class< ? extends Annotation> type) {
    if (hidden.equals(type)) {
      return false;
    } else {
      return delegate.isAnnotationPresent(type);
    }
  }

}
