/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

/**
 * The interface to be implemented by objects with the capability to create
 * default instances of arbitrary types being passed in. This interface was
 * introduced to allow {@link Codec Codecs} to create instances of a certain
 * type without having to worry on how these instances need to be constructed.
 * <p/>
 * <p>
 * This is needed in particular when the {@link Codec Codecs} need to create
 * instances of (non-static) inner classes. In those cases, the new instances
 * need to be constructed by passing a reference to the outer instance. Since
 * {@link Codec Codecs} are not aware of the outer instance, they would never be
 * able to create instances of the inner class. By introducing the
 * {@link Builder} we are able to pass in a context from the owning
 * {@link Codec} with the capability to create instances of inner classes.
 * </p>
 */
@FunctionalInterface
public interface Builder {

  /**
   * Creates a default instance of T.
   *
   * @param <T>
   *          The type of instance we need.
   * @param type
   *          The type of instance desired.
   * @return An instance of T.
   * @throws InstantiationException
   *           If we can't instantiate the given type.
   * @throws IllegalAccessException
   *           If we are not allowed to instantiate the given type.
   */
  <T> T create(Class<T> type) throws InstantiationException, IllegalAccessException;

}
