/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * The Class AnnotationUtils.
 */
public class AnnotationUtils {

  /**
   * Instantiates a new annotation utils.
   */
  private AnnotationUtils() {
    super();
  }

  /**
   * Compares two AnnotatedElement instances to see if they basically define the
   * same data. Currently ignores array type of elements.
   *
   * @param first
   *          The first {@link AnnotatedElement}.
   * @param second
   *          The second {@link AnnotatedElement}
   * @return A boolean indicating whether or not these are considered to be
   *         equivalent.
   */
  public static boolean equivalent(AnnotatedElement first, AnnotatedElement second) {
    if (first == null || second == null || first.getAnnotations() == null
        || second.getAnnotations() == null) {
      return false;
    }
    if (first.getAnnotations().length != second.getAnnotations().length) {
      return false;
    }
    for (Annotation annotation : first.getAnnotations()) {
      Annotation other = second.getAnnotation(annotation.annotationType());
      if (!annotation.equals(other)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Calculate hash code.
   *
   * @param metadata
   *          the metadata
   * @return the int
   */
  public static int calculateHashCode(AnnotatedElement metadata) {
    if (metadata != null) {
      int result = 0;
      for (Annotation annotation : metadata.getAnnotations()) {
        result += annotation.hashCode();
      }
      return result;
    } else {
      return 0;
    }
  }

}
