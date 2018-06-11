/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecException;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;

/**
 * A {@link List} that will lazily load objects from a {@link BitBuffer}. Note
 * that it does <em>not</em> cache the elements in any way. Since the objects
 * will be instantiated on the fly, different threads will return instances with
 * a different object identity.
 *
 * @param <E>
 *          The type of elements in the {@link List}.
 */
public class EvenlyDistributedLazyList<E> implements List<E> {

  /** The policy. */
  private CodecExceptionPolicy<E> policy;

  /** The {@link Codec} used for reading elements of the list. */
  private Codec<E> codec;

  /**
   * The starting point of the list, relative to the first position in the
   * bitbuffer.
   */
  private long offset;

  /** The {@link BitBuffer} from which data has to be read. */
  private BitBuffer buffer;

  /** The maximum number of elements in the list. */
  private int maxSize;

  /**
   * The size of the element in number of bits. (Remember, this implementation
   * of List is for decoding equally-sized elements.)
   */
  private int elementSize;

  /**
   * A reference to the {@link Resolver} resolving variables referenced in
   * {@link org.codehaus.preon.el.Expression}s.
   */
  private Resolver resolver;

  /**
   * The object capable of creating new instances of classes. (Required in order
   * to make sure it can take the outer instance into account.)
   */
  private Builder builder;

  /**
   * Constructs a new instance. Currently the preferred way of constructing a
   * {@link EvenlyDistributedLazyList}.
   *
   * @param codec
   *          The {@link Codec} responsible for decoding elements in the list.
   * @param offset
   *          The start position of the encoded list, relative to the start of
   *          the {@link BitBuffer}.
   * @param buffer
   *          The {@link BitBuffer} from which data will be decoded.
   * @param numberOfElements
   *          The number of elements in the list.
   * @param builder
   *          The object capable of constructing new instances of a class,
   *          including non-static inner classes.
   * @param resolver
   *          The context for evaluating expressions.
   * @param elementSize
   *          the element size
   */
  public EvenlyDistributedLazyList(Codec<E> codec, long offset, BitBuffer buffer,
      int numberOfElements, Builder builder, Resolver resolver, int elementSize) {
    this.codec = codec;
    this.offset = offset;
    this.buffer = buffer;
    this.builder = builder;
    this.maxSize = numberOfElements;
    this.resolver = resolver;
    this.elementSize = elementSize;
    this.policy = ce -> {
      // There is really no way to be prepared for this.
      throw new RuntimeException(ce);
    };
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#add(java.lang.Object)
   */

  @Override
  public boolean add(E o) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#add(int, java.lang.Object)
   */

  @Override
  public void add(int index, E element) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#addAll(java.util.Collection)
   */

  @Override
  public boolean addAll(Collection< ? extends E> c) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#addAll(int, java.util.Collection)
   */

  @Override
  public boolean addAll(int index, Collection< ? extends E> c) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#clear()
   */

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#contains(java.lang.Object)
   */

  @Override
  public boolean contains(Object o) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#containsAll(java.util.Collection)
   */

  @Override
  public boolean containsAll(Collection< ? > c) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#get(int)
   */

  @Override
  public E get(int index) {
    if (index < 0 || index >= maxSize) {
      throw new IndexOutOfBoundsException();
    }
    buffer.setBitPos(offset + index * elementSize);
    try {
      return codec.decode(buffer, resolver, builder);
    } catch (DecodingException de) {
      return policy.handle(de);
    }
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#indexOf(java.lang.Object)
   */

  @Override
  public int indexOf(Object o) {
    // Requires full table scan. Way to expensive.
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#isEmpty()
   */

  @Override
  public boolean isEmpty() {
    return maxSize != 0;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#iterator()
   */

  @Override
  public Iterator<E> iterator() {
    return new LazyListIterator();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#lastIndexOf(java.lang.Object)
   */

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#listIterator()
   */

  @Override
  public ListIterator<E> listIterator() {
    return new LazyListIterator();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#listIterator(int)
   */

  @Override
  public ListIterator<E> listIterator(int index) {
    return new LazyListIterator(index);
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#remove(java.lang.Object)
   */

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#remove(int)
   */

  @Override
  public E remove(int index) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#removeAll(java.util.Collection)
   */

  @Override
  public boolean removeAll(Collection< ? > c) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#retainAll(java.util.Collection)
   */

  @Override
  public boolean retainAll(Collection< ? > c) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#set(int, java.lang.Object)
   */

  @Override
  public E set(int index, E element) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#size()
   */

  @Override
  public int size() {
    return maxSize;
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#subList(int, int)
   */

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    return new EvenlyDistributedLazyList<>(codec, offset + elementSize * fromIndex, buffer,
        toIndex - fromIndex, builder, resolver, elementSize);
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#toArray()
   */

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * @see java.util.List#toArray(T[])
   */

  @Override
  public <T> T[] toArray(T[] a) {
    throw new UnsupportedOperationException();
  }

  /** A {@link ListIterator} that will lazily load elements. */
  private class LazyListIterator implements ListIterator<E> {

    /** The current referenced by the {@link Iterator}. */
    private int position = -1;

    /**
     * Instantiates a new lazy list iterator.
     */
    public LazyListIterator() {
      // do nothing
    }

    /**
     * Constructs a new iterator, accepting the position to start at.
     *
     * @param position
     *          The position to start at.
     */
    public LazyListIterator(int position) {
      this.position = position;
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#add(java.lang.Object)
     */

    @Override
    public void add(E o) {
      throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#hasNext()
     */

    @Override
    public boolean hasNext() {
      return position < maxSize - 1;
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#hasPrevious()
     */

    @Override
    public boolean hasPrevious() {
      return position > 0;
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#next()
     */

    @Override
    public E next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      } else {
        return get(++position);
      }
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#nextIndex()
     */

    @Override
    public int nextIndex() {
      return position + 1;
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#previous()
     */

    @Override
    public E previous() {
      if (!hasPrevious()) {
        throw new NoSuchElementException();
      } else {
        return get(--position);
      }
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#previousIndex()
     */

    @Override
    public int previousIndex() {
      return position - 1;
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#remove()
     */

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * @see java.util.ListIterator#set(java.lang.Object)
     */

    @Override
    public void set(E o) {
      throw new UnsupportedOperationException();
    }

  }

  /**
   * The problem with lazy loading data is that the exception occurs not while
   * calling the decode operation on the {@link Codec}, but later on, while
   * obtaining the data. This interface allows this class to have a strategy for
   * dealing with that.
   * <p/>
   *
   * @param <E>
   *          the element type
   */
  @FunctionalInterface
  public interface CodecExceptionPolicy<E> {

    /**
     * Handle.
     *
     * @param ce
     *          the ce
     * @return the e
     */
    E handle(CodecException ce);

  }

}
