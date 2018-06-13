/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

import java.util.List;

import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;

/**
 * A {@link ResolverContext} that also provides access to all bindings defined
 * for a certain type.
 */
public interface ObjectResolverContext extends ResolverContext {

  /**
   * Gets the resolver.
   *
   * @param context
   *          the context
   * @param resolver
   *          the resolver
   * @return the resolver
   */
  Resolver getResolver(Object context, Resolver resolver);

  /**
   * Gets the bindings.
   *
   * @return the bindings
   */
  List<com.zuan.parser.codehaus.preon.binding.Binding> getBindings();

  /**
   * Adds the.
   *
   * @param name
   *          the name
   * @param binding
   *          the binding
   */
  void add(String name, com.zuan.parser.codehaus.preon.binding.Binding binding);

}
