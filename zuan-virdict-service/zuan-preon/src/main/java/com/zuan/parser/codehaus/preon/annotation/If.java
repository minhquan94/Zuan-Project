/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zuan.parser.codehaus.preon.buffer.BitBuffer;

/**
 * A simple annotation for marking particular fields to be optional, depending
 * on the condition. The condition is based on the Limbo notation. Variables are
 * expected to be resolved relatively to the object holding the annotated field.
 * <p/>
 * <p>
 * Here is an example snippet:
 * </p>
 * <p/>
 *
 * <pre>
 * private int databaseVersion;
 *
 * &#64;author Wilfred Springer
 * &#64;If(&quot;databaseVersion &gt; 700&quot;)
 * &#64;Bound private int foobar;
 * </pre>
 * <p/>
 * <p>
 * In the above case, <code>foobar</code> is only expected to be read from the
 * {@link BitBuffer} if the condition holds. If <code>databaseVersion</code> is
 * 300, it will be skipped.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface If {

  /**
   * The expression to be evaluated.
   *
   * @return The expression to be evaluated.
   */
  String value();

}
