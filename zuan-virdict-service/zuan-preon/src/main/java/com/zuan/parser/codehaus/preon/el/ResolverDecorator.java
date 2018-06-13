/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import com.zuan.parser.codehaus.preon.Resolver;

/**
 * The Interface ResolverDecorator.
 *
 * @author zuan_
 */
@FunctionalInterface
public interface ResolverDecorator {

  /**
   * Decorate.
   *
   * @param decorated
   *          the decorated
   * @return the resolver
   */
  Resolver decorate(Resolver decorated);

}
