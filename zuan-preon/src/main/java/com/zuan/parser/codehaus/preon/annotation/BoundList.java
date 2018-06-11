/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import com.zuan.parser.codehaus.preon.buffer.BitBuffer;

/**
 * The annotation used to mark {@link List} fields as potential candidates to be
 * bound to a {@link BitBuffer}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BoundList {

  /**
   * The type of object to be inserted into the {@link List}. Note that this
   * allows you to have a field of a super type of the actual type that you
   * expect to inject. So you might have something like this:
   * <p/>
   *
   * <pre>
   *         class A {
   *         }
   * <p/>
   *         class B extends A {
   *         }
   * <p/>
   *         ...
   *         &#064;BoundList(type=&quot;B&quot;)
   *         private List&lt;A&gt;; // List will contain instances of B.
   *         ...
   * </pre>
   *
   * @return The type of object to be inserted.
   */
  Class< ? > type() default Void.class;

  /**
   * The types of objects to be decoded. Use this if you want the framework to
   * select a certain class based on a couple of leading bits prefixing the
   * actual data. Note that it expects every type in the array to have the
   * {@link TypePrefix} annotation.
   *
   * @return The types of object to be decoded.
   */
  Class< ? >[] types() default {};

  /**
   * The size of the List. Is expected to be interpreted as a Limbo expression.
   *
   * @return The size of the List, as a Limbo expression.
   */
  String size() default "";

  /**
   * The offset for each individual element. A Limbo expression, accepting a
   * parameter 'index', representing the current index. So an expression could
   * be <code>index*2</code> or <code>offsets[index]</code>, assuming that a
   * certain offsets variable would be in scope.
   *
   * @return A Limbo expression, representing the offset for the individual
   *         elements.
   */
  String offset() default "";

  /**
   * Indicates that the type prefix must be ignored. Note that this is fairly
   * experimental. Use this with cause.
   */
  boolean ommitTypePrefix() default false;

  /**
   * The choices to select from, based on a prefix of a certain size.
   *
   * @return The choices to select from, based on a prefix of a certain size.
   */
  Choices selectFrom() default @Choices(alternatives = {});

}
