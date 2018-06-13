/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.reflect;

/**
 * The Class RuntimeSecurityException.
 *
 * @author zuan_
 */
@SuppressWarnings("serial")
public class RuntimeSecurityException extends ReflectionException {

  /**
   * Instantiates a new runtime security exception.
   *
   * @param se
   *          the se
   */
  RuntimeSecurityException(SecurityException se) {
    super(se);
  }

}
