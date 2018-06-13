/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.Footnote;
import nl.flotsam.pecia.Para;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.builder.ParaBuilder;

/**
 * The Class CaseCorrectingParaBuilder.
 *
 * @author zuan_
 * @param <T>
 *          the generic type
 */
public class CaseCorrectingParaBuilder<T> implements ParaBuilder<T> {

  /** The started. */
  private boolean started;

  /** The wrapped. */
  private ParaBuilder<T> wrapped;

  /**
   * Instantiates a new case correcting para builder.
   *
   * @param wrapped
   *          the wrapped
   */
  public CaseCorrectingParaBuilder(ParaBuilder<T> wrapped) {
    this.wrapped = wrapped;
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.DocumentElement#end()
   */
  @Override
  public T end() {
    return wrapped.end();
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.DocumentElement#getParent()
   */
  @Override
  public T getParent() {
    return wrapped.getParent();
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#code(java.lang.String)
   */
  @Override
  public Para<T> code(String text) {
    started = true;
    return wrapped.code(text);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#document(nl.flotsam.pecia.Documenter)
   */
  @Override
  public Para<T> document(Documenter<ParaContents< ? >> target) {
    started = true;
    return wrapped.document(target);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#email(java.lang.String)
   */
  @Override
  public Para<T> email(String email) {
    started = true;
    return wrapped.email(email);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#emphasis(java.lang.String)
   */
  @Override
  public Para<T> emphasis(String text) {
    started = true;
    return wrapped.emphasis(text);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#footnote()
   */
  @Override
  public Footnote< ? extends Para<T>> footnote() {
    started = true;
    return wrapped.footnote();
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#footnote(java.lang.String)
   */
  @Override
  public Para<T> footnote(String text) {
    started = true;
    return wrapped.footnote(text);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#link(java.lang.Object, java.lang.String)
   */
  @Override
  public Para<T> link(Object id, String text) {
    started = true;
    return wrapped.link(id, text);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#term(java.lang.Object, java.lang.String)
   */
  @Override
  public Para<T> term(Object id, String text) {
    started = true;
    return wrapped.term(id, text);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#text(java.lang.String)
   */
  @Override
  public Para<T> text(String text) {
    if (!started) {
      StringBuilder builder = new StringBuilder();
      if (text.length() >= 1) {
        builder.append(Character.toUpperCase(text.charAt(0)));
      }
      builder.append(text.substring(1));
      wrapped.text(builder.toString());
    } else {
      wrapped.text(text);
    }
    started = true;
    return wrapped.text(text);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.ParaContents#xref(java.lang.String)
   */
  @Override
  public Para<T> xref(String id) {
    started = true;
    return wrapped.xref(id);
  }

  /*
   * (non-Javadoc)
   * @see nl.flotsam.pecia.builder.Initializer#start()
   */
  @Override
  public ParaBuilder<T> start() {
    wrapped.start();
    started = false;
    return this;
  }

}
