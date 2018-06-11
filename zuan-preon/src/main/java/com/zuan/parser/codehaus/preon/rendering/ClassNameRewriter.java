/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.rendering;

/**
 * The Class ClassNameRewriter.
 *
 * @author zuan_
 */
public class ClassNameRewriter implements IdentifierRewriter {

  /** The rewriter. */
  private CamelCaseRewriter rewriter = new CamelCaseRewriter();

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.rendering.IdentifierRewriter#rewrite(
   * java.lang.String)
   */
  @Override
  public String rewrite(String name) {
    String[] parts = name.split("[\\.\\$]");
    return rewriter.rewrite(parts[parts.length - 1], true);
  }

}
