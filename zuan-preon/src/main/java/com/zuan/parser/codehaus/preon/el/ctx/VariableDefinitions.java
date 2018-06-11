/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.ctx;

import com.zuan.parser.codehaus.preon.el.Descriptive;

/**
 * The Interface VariableDefinitions.
 *
 * @author zuan_
 */
public interface VariableDefinitions extends Descriptive {

  /**
   * Gets the names.
   *
   * @return the names
   */
  String[] getNames();

  /**
   * Gets the type.
   *
   * @param name
   *          the name
   * @return the type
   */
  @SuppressWarnings("rawtypes")
  Class getType(String name);

}
