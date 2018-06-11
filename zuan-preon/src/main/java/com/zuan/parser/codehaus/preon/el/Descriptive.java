/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

/**
 * The interface to be implemented by objects capable of describing itself using
 * an object passed in. This object could be a {@link StringBuilder}, a
 * {@link StringBuffer}, or whatever you prefer.
 */
@FunctionalInterface
public interface Descriptive {

  /**
   * Documents the object, targeting the object passed in.
   *
   * @param target
   *          The receiving object.
   */
  void document(Document target);

}
