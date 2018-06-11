/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import com.zuan.parser.codehaus.preon.el.Document;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.Footnote;
import nl.flotsam.pecia.Para;

/**
 * The Class ParaContentsDocument.
 *
 * @author zuan_
 */
@SuppressWarnings({"unchecked", "rawtypes" })
public class ParaContentsDocument implements Para {

  /** The document. */
  private Document document;

  /**
   * Instantiates a new para contents document.
   *
   * @param document
   *          the document
   */
  public ParaContentsDocument(Document document) {
    this.document = document;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.DocumentElement#end()
   */
  @Override
  public Object end() {
    return null; // No relevant implementation
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.DocumentElement#getParent()
   */
  @Override
  public Object getParent() {
    return null; // No relevant implementation
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#code(java.lang.String)
   */
  @Override
  public Para code(String text) {
    document.text(text);
    return this;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#email(java.lang.String)
   */
  @Override
  public Para email(String email) {
    document.text(email);
    return this;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#emphasis(java.lang.String)
   */
  @Override
  public Para emphasis(String text) {
    document.text(text);
    return this;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#footnote()
   */
  @Override
  public Footnote footnote() {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#link(java.lang.Object, java.lang.String)
   */
  @Override
  public Para link(Object id, String text) {
    document.link(id, text);
    return this;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#term(java.lang.Object, java.lang.String)
   */
  @Override
  public Para term(Object id, String text) {
    document.text(text);
    return this;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#text(java.lang.String)
   */
  @Override
  public Para text(String text) {
    document.text(text);
    return this;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#xref(java.lang.String)
   */
  @Override
  public Para xref(String id) {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#footnote(java.lang.String)
   */
  @Override
  public Para footnote(String footnote) {
    return this;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#document(nl.flotsam.pecia.Documenter)
   */
  @Override
  public Para document(Documenter documenter) {
    documenter.document(this);
    return this;
  }

}
