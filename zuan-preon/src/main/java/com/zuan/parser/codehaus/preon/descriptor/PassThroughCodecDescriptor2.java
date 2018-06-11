/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.descriptor;

import com.zuan.parser.codehaus.preon.CodecDescriptor;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * The Class PassThroughCodecDescriptor2.
 *
 * @author zuan_
 */
public class PassThroughCodecDescriptor2 implements CodecDescriptor {

  /** The delegate. */
  private CodecDescriptor delegate;

  /** The requires dedicated section. */
  private boolean requiresDedicatedSection;

  /**
   * Instantiates a new pass through codec descriptor2.
   *
   * @param delegate
   *          the delegate
   * @param requiresDedicatedSection
   *          the requires dedicated section
   */
  public PassThroughCodecDescriptor2(CodecDescriptor delegate,
      boolean requiresDedicatedSection) {
    this.delegate = delegate;
    this.requiresDedicatedSection = requiresDedicatedSection;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#details(java.lang.
   * String)
   */
  @Override
  public <C extends SimpleContents< ? >> Documenter<C> details(String bufferReference) {
    return delegate.details(bufferReference);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#getTitle()
   */
  @Override
  public String getTitle() {
    return delegate.getTitle();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#reference(com.virdict.
   * tool.parser.codehaus.preon.CodecDescriptor.Adjective, boolean)
   */
  @Override
  public <C extends ParaContents< ? >> Documenter<C> reference(Adjective adjective,
      boolean startWithCapital) {
    return delegate.reference(adjective, false);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#
   * requiresDedicatedSection()
   */
  @Override
  public boolean requiresDedicatedSection() {
    return requiresDedicatedSection;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.CodecDescriptor#summary()
   */
  @Override
  public <C extends ParaContents< ? >> Documenter<C> summary() {
    return delegate.summary();
  }

}
