/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import com.zuan.parser.codehaus.preon.el.Document;

import nl.flotsam.pecia.ParaContents;

/**
 * The Class DocumentParaContents.
 *
 * @author zuan_
 */
public class DocumentParaContents implements Document {

  /** The contents. */
  private ParaContents< ? > contents;

  /**
   * Instantiates a new document para contents.
   *
   * @param contents
   *          the contents
   */
  public DocumentParaContents(ParaContents< ? > contents) {
    this.contents = contents;
  }

  /**
   * Detail.
   *
   * @param text
   *          the text
   * @return the document
   */
  @Override
  public Document detail(String text) {
    return null;
  }

  /**
   * Link.
   *
   * @param object
   *          the object
   * @param text
   *          the text
   */
  @Override
  public void link(Object object, String text) {
    contents.link(object, text);
  }

  /**
   * Text.
   *
   * @param text
   *          the text
   */
  @Override
  public void text(String text) {
    contents.text(text);
  }

}
