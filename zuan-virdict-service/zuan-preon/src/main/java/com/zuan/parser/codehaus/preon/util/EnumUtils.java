/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zuan.parser.codehaus.preon.annotation.BoundEnumOption;

/**
 * The Class EnumUtils.
 *
 * @author zuan_
 */
public class EnumUtils {

  /**
   * Instantiates a new enum utils.
   */
  private EnumUtils() {
    super();
  }

  /**
   * Gets the bound enum option index.
   *
   * @param <T>
   *          the generic type
   * @param enumType
   *          the enum type
   * @return the bound enum option index
   */
  @SuppressWarnings("unchecked")
  public static <T> Map<Long, T> getBoundEnumOptionIndex(Class<T> enumType) {
    if (!enumType.isEnum()) {
      return Collections.emptyMap();
    }
    Map<Long, T> result = new HashMap<>();
    Field[] fields = enumType.getFields();
    for (Field field : fields) {
      if (field.isEnumConstant()) {
        try {
          field.setAccessible(true);
          BoundEnumOption annotation = field.getAnnotation(BoundEnumOption.class);
          if (annotation == null) {
            result.put(null, (T) field.get(null));
          } else {
            result.put(annotation.value(), (T) field.get(null));
          }
        } catch (IllegalAccessException iae) {
          iae.printStackTrace(); // Should never happen.
        }
      }
    }
    return result;
  }

}
