/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.binding;

/**
 * The Interface BindingDecorator.
 *
 * @author zuan_
 */
@FunctionalInterface
public interface BindingDecorator {

  /**
   * Decorate.
   *
   * @param binding
   *          the binding
   * @return the binding
   */
  Binding decorate(Binding binding);

}
