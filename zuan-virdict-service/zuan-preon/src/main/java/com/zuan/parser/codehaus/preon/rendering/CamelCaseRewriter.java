/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.rendering;

import java.util.ArrayList;
import java.util.List;

/**
 * An object that will take a camelcase identifier, and break it apart into
 * different chunks.
 */
public class CamelCaseRewriter implements IdentifierRewriter {

  /** The start with uppercase. */
  private boolean startWithUppercase;

  /**
   * Instantiates a new camel case rewriter.
   */
  public CamelCaseRewriter() {
    startWithUppercase = true;
  }

  /**
   * Instantiates a new camel case rewriter.
   *
   * @param startWithUppercase
   *          the start with uppercase
   */
  public CamelCaseRewriter(boolean startWithUppercase) {
    this.startWithUppercase = startWithUppercase;
  }

  /**
   * Rewrite.
   *
   * @param name
   *          the name
   * @param startWithCapital
   *          the start with capital
   * @return the string
   */
  public String rewrite(String name, boolean startWithCapital) {
    List<String> parts = new ArrayList<>();
    StringBuilder portion = new StringBuilder();
    int length = name.length();
    for (int i = 0; i < length; i++) {
      char c = name.charAt(i);
      if (Character.isUpperCase(c)) {
        String part = portion.toString();
        if (part.length() > 0) {
          parts.add(part);
        }
        portion.setLength(0);
        portion.append(Character.toLowerCase(c));
      } else {
        portion.append(c);
      }
    }
    String part = portion.toString();
    if (part.length() > 0) {
      parts.add(part);
    }
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < parts.size(); i++) {
      if (i != 0) {
        result.append(' ');
      }
      if (i == 0 && startWithCapital) {
        result.append(Character.toUpperCase(parts.get(i).charAt(0)));
        result.append(parts.get(i).substring(1));
      } else {
        result.append(parts.get(i));
      }
    }
    return result.toString();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.rendering.IdentifierRewriter#rewrite(
   * java.lang.String)
   */
  @Override
  public String rewrite(String name) {
    return rewrite(name, startWithUppercase);
  }

}
