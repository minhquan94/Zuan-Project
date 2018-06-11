/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import com.zuan.parser.codehaus.preon.CodecDescriptor.Adjective;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * The Interface CodecDescriptor.
 */
@SuppressWarnings("unused")
public interface CodecDescriptor {

  /** An enumeration with different adjectives. */
  public enum Adjective {

    /** The a. */
    A,
    /** The the. */
    THE,
    /** The none. */
    NONE;

    /**
     * As text prefer a.
     *
     * @param startWithCapital
     *          the start with capital
     * @return the string
     */
    public String asTextPreferA(boolean startWithCapital) {
      if (startWithCapital) {
        switch (this) {
        case A:
          return "A ";
        case THE:
          return "The ";
        default:
          return "";
        }
      } else {
        switch (this) {
        case A:
          return "a ";
        case THE:
          return "the ";
        default:
          return "";
        }
      }
    }

    /**
     * As text prefer an.
     *
     * @param startWithCapital
     *          the start with capital
     * @return the string
     */
    public String asTextPreferAn(boolean startWithCapital) {
      if (startWithCapital) {
        switch (this) {
        case A:
          return "An ";
        case THE:
          return "The ";
        default:
          return "";
        }
      } else {
        switch (this) {
        case A:
          return "an ";
        case THE:
          return "the ";
        default:
          return "";
        }
      }
    }

  }

  /**
   * Returns an object capable of writing a one-line summary of the data
   * structure. Expect the summary to be printed at the beginning of a
   * paragraph, but make sure the paragraph is ended in such a way that more
   * lines might be appended to that paragraph, if required, by some other
   * component. I.e. make sure you end with a dot-space. (". ") Typically starts
   * with {@link Adjective#A}. Will often rely on
   * {@link #writeReference(ParaContents, Adjective)} for its implementation.
   *
   * @param <C>
   *          the generic type
   * @return the documenter
   */
  <C extends ParaContents< ? >> Documenter<C> summary();

  /**
   * Returns an object capable of rendering a short reference to the type of
   * data for which the Codec provides the decoder. This reference should <em>at
   * least</em> include a reference to the type of data decoded by 'sub'-Codecs.
   * The {@link Adjective} argument allows the implementor to generate a correct
   * reference, such as 'a list' instead of 'an list'.
   * <p/>
   * <p>
   * Note that implementers should assume that the particular piece of data that
   * is going to be referenced here will be detailed further along the road.
   * Unless {@link #requiresDedicatedSection()} returns <code>true</code>, that
   * could be within the same section.
   * </p>
   *
   * @param <C>
   *          the generic type
   * @param adjective
   *          The adjective to use; <code>null</code> if no adjective should be
   *          used.
   * @param startWithCapital
   *          the start with capital
   * @return the documenter
   */
  <C extends ParaContents< ? >> Documenter<C> reference(Adjective adjective,
      boolean startWithCapital);

  /**
   * Returns an object capable of writing detailed information on the format to
   * the document section passed in. Typically implemented by writing a (couple
   * of) paragraph(s), and forwarding to the CodecDescriptor of a nested
   * {@linkplain Codec}. Note that - while forwarding - the descriptor has the
   * option to replace the way the buffer is referenced.
   *
   * @param <C>
   *          the generic type
   * @param bufferReference
   *          A String based human readable reference to the encoded data.
   * @return the documenter
   */
  <C extends SimpleContents< ? >> Documenter<C> details(String bufferReference);

  /**
   * Returns a boolean indicating if the type of data for which the Codec
   * provides the decoder should be documented in a dedicated section.
   *
   * @return A boolean indicating if the type of data for which the Codec
   *         provides the decoder should be documented in a dedicated section:
   *         <code>true</code> if it does; <code>false</code> if it doesn't.
   */
  boolean requiresDedicatedSection();

  /**
   * Returns the title of the section to be rendered, in case
   * {@link #requiresDedicatedSection()} returns <code>true</code>.
   *
   * @return The title of the section to be rendered, in case
   *         {@link #requiresDedicatedSection()} returns <code>true</code>.
   */
  String getTitle();

}
