/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.el.util;

/**
 * The Class Converters.
 *
 * @author zuan_
 */
public class Converters {

  /**
   * Instantiates a new converters.
   */
  private Converters() {
    super();
  }

  /** The byte to integer. */
  private static ByteToIntegerConverter byteToInteger = new ByteToIntegerConverter();

  /** The short to integer. */
  private static ShortToIntegerConverter shortToInteger = new ShortToIntegerConverter();

  /** The long to integer. */
  private static LongToIntegerConverter longToInteger = new LongToIntegerConverter();

  /**
   * Gets the.
   *
   * @param <T>
   *          the generic type
   * @param <V>
   *          the value type
   * @param from
   *          the from
   * @param to
   *          the to
   * @return the converter
   */
  @SuppressWarnings("unchecked")
  public static <T, V> Converter<T, V> get(Class<T> from, Class<V> to) {
    if (to == Integer.class || to == int.class) {
      if (from == Byte.class || from == byte.class) {
        return (Converter<T, V>) byteToInteger;
      } else if (from == Short.class || from == short.class) {
        return (Converter<T, V>) shortToInteger;
      } else if (from == Long.class || from == long.class) {
        return (Converter<T, V>) longToInteger;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  /**
   * To int.
   *
   * @param <T>
   *          the generic type
   * @param value
   *          the value
   * @return the int
   */
  @SuppressWarnings("unchecked")
  public static <T> int toInt(T value) {
    Converter<T, Integer> converter =
        (Converter<T, Integer>) get(value.getClass(), Integer.class);
    return converter.convert(value);
  }

  /**
   * The Class ByteToIntegerConverter.
   */
  private static class ByteToIntegerConverter implements Converter<Byte, Integer> {

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.util.Converter#convert(java.lang.
     * Object)
     */
    @Override
    public Integer convert(Byte instance) {
      return instance.intValue();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.util.Converter#getTargetType()
     */
    @Override
    public Class<Integer> getTargetType() {
      return Integer.class;
    }

  }

  /**
   * The Class ShortToIntegerConverter.
   */
  private static class ShortToIntegerConverter implements Converter<Short, Integer> {

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.util.Converter#convert(java.lang.
     * Object)
     */
    @Override
    public Integer convert(Short instance) {
      return instance.intValue();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.util.Converter#getTargetType()
     */
    @Override
    public Class<Integer> getTargetType() {
      return Integer.class;
    }

  }

  /**
   * The Class LongToIntegerConverter.
   */
  private static class LongToIntegerConverter implements Converter<Long, Integer> {

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.util.Converter#convert(java.lang.
     * Object)
     */
    @Override
    public Integer convert(Long instance) {
      return instance.intValue();
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.el.util.Converter#getTargetType()
     */
    @Override
    public Class<Integer> getTargetType() {
      return Integer.class;
    }

  }

}
