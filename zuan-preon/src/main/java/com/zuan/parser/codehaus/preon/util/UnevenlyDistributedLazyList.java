/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A {@link List} implementation that will lazy load its elements. Big
 * difference with {@link EvenlyDistributedLazyList} is that this implementation
 * does not assume all elements to have the same size.
 *
 * @param <E>
 *          the element type
 */
public class UnevenlyDistributedLazyList<E> implements List<E> {

  /*
   * (non-Javadoc)
   * @see java.util.List#add(java.lang.Object)
   */
  @Override
  public boolean add(E element) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#add(int, java.lang.Object)
   */
  @Override
  public void add(int position, E element) {
    // do nothing
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection< ? extends E> elements) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#addAll(int, java.util.Collection)
   */
  @Override
  public boolean addAll(int position, Collection< ? extends E> elements) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#clear()
   */
  @Override
  public void clear() {
    // do nothing
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#contains(java.lang.Object)
   */
  @Override
  public boolean contains(Object object) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#containsAll(java.util.Collection)
   */
  @Override
  public boolean containsAll(Collection< ? > elements) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#get(int)
   */
  @Override
  public E get(int position) {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Override
  public int indexOf(Object object) {
    return 0;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#iterator()
   */
  @Override
  public Iterator<E> iterator() {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#lastIndexOf(java.lang.Object)
   */
  @Override
  public int lastIndexOf(Object object) {
    return 0;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#listIterator()
   */
  @Override
  public ListIterator<E> listIterator() {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#listIterator(int)
   */
  @Override
  public ListIterator<E> listIterator(int position) {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object object) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#remove(int)
   */
  @Override
  public E remove(int position) {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#removeAll(java.util.Collection)
   */
  @Override
  public boolean removeAll(Collection< ? > elements) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#retainAll(java.util.Collection)
   */
  @Override
  public boolean retainAll(Collection< ? > elements) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#set(int, java.lang.Object)
   */
  @Override
  public E set(int position, E element) {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#size()
   */
  @Override
  public int size() {
    return 0;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#subList(int, int)
   */
  @Override
  public List<E> subList(int start, int end) {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#toArray()
   */
  @Override
  public Object[] toArray() {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#toArray(java.lang.Object[])
   */
  @Override
  public <T> T[] toArray(T[] elements) {
    return null;
  }

}
