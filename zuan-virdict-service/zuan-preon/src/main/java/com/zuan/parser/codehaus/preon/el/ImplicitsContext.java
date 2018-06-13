/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el;

/**
 * A context that 'imports' constant references such as true and false.
 *
 * @param <E>
 *          the element type
 */
public class ImplicitsContext<E> implements ReferenceContext<E> {

  /** The nested. */
  private ReferenceContext<E> nested;

  /**
   * Instantiates a new implicits context.
   *
   * @param nested
   *          the nested
   */
  public ImplicitsContext(ReferenceContext<E> nested) {
    this.nested = nested;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectAttribute(
   * java.lang.String)
   */
  @Override
  public Reference<E> selectAttribute(String name) throws BindingException {
    if ("true".equals(name) || "false".equals(name)) {
      return new BooleanLiteralReference<>(Boolean.parseBoolean(name), this);
    } else {
      return nested.selectAttribute(name);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(java.
   * lang.String)
   */
  @Override
  public Reference<E> selectItem(String index) throws BindingException {
    return nested.selectItem(index);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.ReferenceContext#selectItem(com.
   * zuan.parser.codehaus.preon.el.Expression)
   */
  @Override
  public Reference<E> selectItem(Expression<Integer, E> index) throws BindingException {
    return nested.selectItem(index);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.el.Descriptive#document(com.virdict.
   * tool.parser.codehaus.preon.el.Document)
   */
  @Override
  public void document(Document target) {
    nested.document(target);
  }

}
