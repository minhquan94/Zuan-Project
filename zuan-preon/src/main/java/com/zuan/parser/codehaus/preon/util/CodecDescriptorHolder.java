/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import com.zuan.parser.codehaus.preon.CodecDescriptor;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * The Class CodecDescriptorHolder.
 *
 * @author zuan_
 */
public class CodecDescriptorHolder implements CodecDescriptor {

  /** The descriptor. */
  private CodecDescriptor descriptor;

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#details(java.lang.
   * String)
   */
  @Override
  public <C extends SimpleContents< ? >> Documenter<C> details(String bufferReference) {
    return descriptor.details(bufferReference);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#getTitle()
   */
  @Override
  public String getTitle() {
    return descriptor.getTitle();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#reference(com.virdict.
   * tool.parser.codehaus.preon.CodecDescriptor.Adjective, boolean)
   */
  @Override
  public <C extends ParaContents< ? >> Documenter<C> reference(Adjective adjective,
      boolean startWithCapital) {
    return descriptor.reference(adjective, startWithCapital);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#
   * requiresDedicatedSection()
   */
  @Override
  public boolean requiresDedicatedSection() {
    return descriptor.requiresDedicatedSection();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#summary()
   */
  @Override
  public <C extends ParaContents< ? >> Documenter<C> summary() {
    return descriptor.summary();
  }

  /**
   * Sets the descriptor.
   *
   * @param descriptor
   *          the new descriptor
   */
  public void setDescriptor(CodecDescriptor descriptor) {
    this.descriptor = descriptor;
  }

}
