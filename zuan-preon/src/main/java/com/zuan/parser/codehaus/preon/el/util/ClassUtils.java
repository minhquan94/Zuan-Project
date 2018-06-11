/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.util;

/**
 * A couple of utilities supporting classes.
 */
public class ClassUtils {

  /**
   * Instantiates a new class utils.
   */
  private ClassUtils() {
    super();
  }

  /**
   * Determines the common base type for a couple of types passed in.
   *
   * @param types
   *          The collection of types to for which we need a common base class.
   * @return The common type.
   */
  public static Class< ? > calculateCommonSuperType(Class< ? >... types) {
    switch (types.length) {
    case 0:
      return null;
    case 1:
      return types[0];
    case 2:
      return calculateCommonSuperType(types[0], types[1]);
    default: {
      Class< ? > common = types[0];
      for (int i = 1; i < types.length; i++) {
        if (common == java.lang.Object.class) {
          break;
        }
        common = calculateCommonSuperType(common, types[i]);
      }
      return common;
    }
    }
  }

  /**
   * Determines the common base type for two types passed in.
   *
   * @param first
   *          The first type.
   * @param second
   *          The second type.
   * @return The common base class.
   */
  public static Class< ? > calculateCommonSuperType(Class< ? > first, Class< ? > second) {
    if (first != null && first.isPrimitive()) {
      return calculateCommonSuperType(second, getBoxedType(first));
    } else {
      if (first == null || second == null) {
        return Object.class;
      }
      if (first.isAssignableFrom(second)) {
        return first;
      } else if (second.isAssignableFrom(first)) {
        return first;
      } else {
        return calculateCommonSuperType(second.getSuperclass(), first);
      }
    }
  }

  /**
   * Gets the boxed type.
   *
   * @param type
   *          the type
   * @return the boxed type
   */
  private static Class< ? > getBoxedType(Class< ? > type) {
    if (double.class == type) {
      return Double.class;
    } else if (long.class == type) {
      return Long.class;
    } else if (int.class == type) {
      return Integer.class;
    } else if (short.class == type) {
      return Short.class;
    } else if (byte.class == type) {
      return Byte.class;
    } else if (boolean.class == type) {
      return Boolean.class;
    } else if (float.class == type) {
      return Float.class;
    } else {
      throw new IllegalArgumentException("Not a primitive type: " + type.getSimpleName());
    }
  }

  /**
   * Gets the guaranteed boxed version.
   *
   * @param type
   *          the type
   * @return the guaranteed boxed version
   */
  public static Class< ? > getGuaranteedBoxedVersion(Class< ? > type) {
    if (type.isPrimitive()) {
      return getBoxedType(type);
    } else {
      return type;
    }
  }

}
