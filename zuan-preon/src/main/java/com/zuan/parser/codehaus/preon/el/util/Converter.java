/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.util;

/**
 * The interface defining an object capable of converting instances of {@code T}
 * to {@code V}.
 *
 * @param <T>
 *          The type of object to be converted.
 * @param <V>
 *          The target type.
 */
public interface Converter<T, V> {

  /**
   * Accepts an instance of {@code T} and turns it into the target type.
   *
   * @param instance
   *          The object to be converted.
   * @return A new instance of {@code V}.
   */
  V convert(T instance);

  /**
   * The target type.
   *
   * @return The target type.
   */
  Class<V> getTargetType();

}
