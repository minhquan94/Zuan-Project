/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.rendering;

/**
 * The Interface IdentifierRewriter.
 *
 * @author zuan_
 */
@FunctionalInterface
public interface IdentifierRewriter {

  /**
   * Rewrite.
   *
   * @param name
   *          the name
   * @return the string
   */
  String rewrite(String name);

}
