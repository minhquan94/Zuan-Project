/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

/**
 * The interface implemented by objects receiving the human-readable
 * representation of an {@link Expression}.
 */
public interface Document {

  /**
   * Adds some text.
   *
   * @param text
   *          The text added.
   */
  void text(String text);

  /**
   * Adds a link.
   *
   * @param object
   *          The object linked.
   * @param text
   *          The text used for the link.
   */
  void link(Object object, String text);

  /**
   * Detail.
   *
   * @param text
   *          the text
   * @return the document
   * @deprecated
   */
  @Deprecated
  Document detail(String text);

}
