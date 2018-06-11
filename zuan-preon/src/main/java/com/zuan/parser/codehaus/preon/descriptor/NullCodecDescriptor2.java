/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.descriptor;

import com.zuan.parser.codehaus.preon.CodecDescriptor;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * The Class NullCodecDescriptor2.
 *
 * @author zuan_
 */
public class NullCodecDescriptor2 implements CodecDescriptor {

  /**
   * Details.
   *
   * @param <T>
   *          the generic type
   * @param bufferReference
   *          the buffer reference
   * @return the documenter
   */
  @Override
  public <T extends SimpleContents< ? >> Documenter<T> details(String bufferReference) {
    return target -> {
    };
  }

  /**
   * Gets the title.
   *
   * @return the title
   */
  @Override
  public String getTitle() {
    return "";
  }

  /**
   * Reference.
   *
   * @param <T>
   *          the generic type
   * @param adjective
   *          the adjective
   * @param startWithCapital
   *          the start with capital
   * @return the documenter
   */
  @Override
  public <T extends ParaContents< ? >> Documenter<T> reference(Adjective adjective,
      boolean startWithCapital) {
    return target -> {
    };
  }

  /**
   * Requires dedicated section.
   *
   * @return true, if successful
   */
  @Override
  public boolean requiresDedicatedSection() {
    return false;
  }

  /**
   * Summary.
   *
   * @param <T>
   *          the generic type
   * @return the documenter
   */
  @Override
  public <T extends ParaContents< ? >> Documenter<T> summary() {
    return target -> {
    };
  }

}
