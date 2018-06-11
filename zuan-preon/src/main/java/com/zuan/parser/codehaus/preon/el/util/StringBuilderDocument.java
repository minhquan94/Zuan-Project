/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.util;

import com.zuan.parser.codehaus.preon.el.Document;

/**
 * The Class StringBuilderDocument.
 *
 * @author zuan_
 */
public class StringBuilderDocument implements Document {

  /** The builder. */
  private StringBuilder builder;

  /**
   * Instantiates a new string builder document.
   */
  public StringBuilderDocument() {
    this(new StringBuilder());
  }

  /**
   * Instantiates a new string builder document.
   *
   * @param builder
   *          the builder
   */
  public StringBuilderDocument(StringBuilder builder) {
    this.builder = builder;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Document#link(java.lang.Object,
   * java.lang.String)
   */
  @Override
  public void link(Object object, String text) {
    builder.append(text);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Document#text(java.lang.String)
   */
  @Override
  public void text(String text) {
    builder.append(text);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return builder.toString();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Document#detail(java.lang.String)
   */
  @Override
  public Document detail(String text) {
    return new NullDocument();
  }

}
