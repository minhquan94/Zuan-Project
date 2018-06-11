/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import com.zuan.parser.codehaus.preon.el.ReferenceContext;

/**
 * A convenience interface, defining a {@link ReferenceContext} of
 * {@link Resolver}. {@link Resolver} is the only context used at runtime.
 */
public interface ResolverContext extends ReferenceContext<Resolver> {

}
