/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * A utility class, providing a couple of convenience operations for dealing
 * with reflection. (Adding Plexus Reflector or Spring ReflectionUtils is
 * considered to be overhead.)
 *
 * @author Wilfred Springer
 */
public class ReflectionUtils {

  /**
   * Instantiates a new reflection utils.
   */
  private ReflectionUtils() {
    super();
  }

  /**
   * Make the field accessible, if it not already is accessible.
   *
   * @param field
   *          The field that needs to be made accessible.
   */
  public static void makeAssessible(Field field) {
    if (!field.isAccessible()) {
      field.setAccessible(true);
    }
  }

  /**
   * Obtains the field value from the object passed in.
   * {@link IllegalAccessException} will be caught and rethrown as a runtime
   * exception.
   *
   * @param field
   *          the field
   * @param object
   *          the object
   * @return the value
   * @throws RuntimeIllegalAccessException
   *           If accessing the field passed in is not permitted.
   */
  public static Object getValue(Field field, Object object)
      throws RuntimeIllegalAccessException {
    try {
      return field.get(object);
    } catch (IllegalAccessException iae) {
      throw new RuntimeIllegalAccessException(iae);
    }
  }

  /**
   * Obtains the field value from the object passed in as an integer.
   * {@link IllegalAccessException} will be caught and rethrown as a runtime
   * exception.
   *
   * @param field
   *          the field
   * @param object
   *          the object
   * @return the value as int
   * @throws RuntimeIllegalAccessException
   *           If accessing the field passed in is not permitted.
   */
  public static int getValueAsInt(Field field, Object object)
      throws RuntimeIllegalAccessException {
    try {
      return field.getInt(object);
    } catch (IllegalAccessException iae) {
      throw new RuntimeIllegalAccessException(iae);
    }
  }

  /**
   * Obtains the field value from the object passed in as an integer.
   * {@link IllegalAccessException} will be caught and rethrown as a runtime
   * exception.
   *
   * @param name
   *          The name of the field.
   * @param object
   *          the object
   * @return the value as int
   * @throws RuntimeIllegalAccessException
   *           If accessing the field passed in is not permitted.
   */
  public static int getValueAsInt(String name, Object object)
      throws RuntimeIllegalAccessException {
    try {
      return getField(object.getClass(), name).getInt(object);
    } catch (IllegalAccessException iae) {
      throw new RuntimeIllegalAccessException(iae);
    }
  }

  /**
   * Obtains the field value from the object passed in.
   * {@link IllegalAccessException} will be caught and rethrown as a runtime
   * exception.
   *
   * @param field
   *          the field
   * @param object
   *          the object
   * @param value
   *          the value
   * @throws RuntimeIllegalAccessException
   *           If accessing the field passed in is not permitted.
   */
  public static void setValue(Field field, Object object, Object value)
      throws RuntimeIllegalAccessException {
    try {
      field.set(object, value);
    } catch (IllegalAccessException iae) {
      throw new RuntimeIllegalAccessException(iae);
    }
  }

  /**
   * Creates a new instance of the class passed in, using the default
   * constructor.
   *
   * @param <T>
   *          the generic type
   * @param cl
   *          the cl
   * @return A new instance of the class passed in.
   */
  public static <T> T create(Class<T> cl) {
    try {
      return cl.newInstance();
    } catch (InstantiationException ie) {
      throw new RuntimeInstantiationException(ie);
    } catch (IllegalAccessException iae) {
      throw new RuntimeIllegalAccessException(iae);
    }
  }

  /**
   * Creates a new instance of the class passed in, using the default
   * constructor passed in.
   *
   * @param <T>
   *          the generic type
   * @param constructor
   *          The constructor of the class.
   * @return A new instance of the class passed in.
   */
  public static <T> T create(Constructor<T> constructor) {
    try {
      return constructor.newInstance();
    } catch (InstantiationException ie) {
      throw new RuntimeInstantiationException(ie);
    } catch (IllegalAccessException iae) {
      throw new RuntimeIllegalAccessException(iae);
    } catch (InvocationTargetException ite) {
      throw new RuntimeInvocationTargetException(ite);
    }
  }

  /**
   * Creates a new instance of the class passed in, using the default
   * constructor passed in.
   *
   * @param <T>
   *          the generic type
   * @param constructor
   *          The constructor of the class.
   * @param args
   *          The arguments to be passed to the constructor.
   * @return A new instance of the class passed in.
   */
  public static <T> T create(Constructor<T> constructor, Object[] args) {
    try {
      return constructor.newInstance(args);
    } catch (InstantiationException ie) {
      throw new RuntimeInstantiationException(ie);
    } catch (IllegalAccessException iae) {
      throw new RuntimeIllegalAccessException(iae);
    } catch (InvocationTargetException ite) {
      throw new RuntimeInvocationTargetException(ite);
    }
  }

  /**
   * Returns the {@link Field} for a field with the given name on the given
   * class.
   *
   * @param cl
   *          The class for which we need the field.
   * @param name
   *          The name of the field.
   * @return A new {@link Field} instanc.e
   */
  @SuppressWarnings("rawtypes")
  public static Field getField(Class cl, String name) {
    try {
      return cl.getDeclaredField(name);
    } catch (SecurityException se) {
      throw new RuntimeSecurityException(se);
    } catch (NoSuchFieldException nse) {
      cl = cl.getSuperclass();
      if (!java.lang.Object.class.equals(cl)) {
        return getField(cl, name);
      } else {
        throw new RuntimeNoSuchFieldException(nse);
      }
    }
  }

}
