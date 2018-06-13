/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for adding metadata to String fields, informing the framework
 * how to decode and encode the data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BoundString {

  /**
   * The Class Encoding.
   */
  class Encoding {

    /** The Constant ASCII. */
    /*
     * This is a compatibility stub. Encodings are strings now, and this ensures
     * that anyone using the old syntax will get the same results.
     */
    public static final String ASCII = "US-ASCII";

    /** The Constant ISO_8859_1. */
    public static final String ISO_8859_1 = "ISO-8859-1";

  }

  /**
   * Returns the number of bytes to be interpreted as a String.
   *
   * @return The number of bytes to be interpreted as a String. (Can be a Limbo
   *         expression.)
   */
  String size() default "";

  /**
   * Returns the type of encoding used for the String.
   *
   * @return The type of encoding used for the String.
   */
  String encoding() default "US-ASCII";

  /**
   * The String that needs to be matched.
   *
   * @return The String that needs to be matched. Or the empty String if
   *         matching is not important.
   */

  String match() default "";

  /*
   * I've left this in, but I don't use this code anywhere in the actual
   * factory. It might be possible to alter the factories to use ByteConverters,
   * by wrapping around ByteBuffer in some clever way, but my feeling is that
   * the aims of this code would be better achieved by Charsets.
   */

  /**
   * Converter.
   *
   * @return the class<? extends byte converter>
   */
  Class< ? extends ByteConverter> converter() default NullConverter.class;

  /**
   * The Interface ByteConverter.
   */
  public interface ByteConverter {

    /**
     * Convert.
     *
     * @param in
     *          the in
     * @return the byte
     */
    byte convert(byte in);

    /**
     * Revert.
     *
     * @param in
     *          the in
     * @return the byte
     */
    byte revert(byte in);

    /**
     * Gets the description.
     *
     * @return the description
     */
    String getDescription();

  }

  /**
   * The Class NullConverter.
   */
  public class NullConverter implements ByteConverter {

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.annotation.BoundString.ByteConverter#
     * convert(byte)
     */
    @Override
    public byte convert(byte in) {
      return in;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.annotation.BoundString.ByteConverter#
     * revert(byte)
     */
    @Override
    public byte revert(byte in) {
      return in;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.annotation.BoundString.ByteConverter#
     * getDescription()
     */
    @Override
    public String getDescription() {
      return "";
    }

  }

}
