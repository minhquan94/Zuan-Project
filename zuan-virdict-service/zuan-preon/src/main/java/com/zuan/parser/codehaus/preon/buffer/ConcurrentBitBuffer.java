/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.buffer;

import java.nio.ByteBuffer;

/**
 * A threadsafe {@link BitBuffer}. The threadsafe implementation is wrapped
 * around another {@link BitBuffer}.
 */
public class ConcurrentBitBuffer extends AbstractBitBufferDecorator {

  /** The current {@link BitBuffer}, indexed by thread. */
  private ThreadLocal<BitBuffer> current = new ThreadLocal<>();

  /**
   * The source from which all other (thread-bound) {@link BitBuffer BitBuffers}
   * will be created.
   */
  private BitBuffer source;

  /**
   * Constructs a new instance, accepting a source {@link BitBuffer}. Note that
   * this instance is expected <em>not</em> to change, in order to preserve the
   * guarantee that all threads will get an (initially) identicial copy. If
   * that's something you can't guarantee, it might be better to create a
   * {@link BitBuffer#duplicate() duplicate} first.
   *
   * @param source
   *          The {@link BitBuffer} from which all thread-bound {@link BitBuffer
   *          BitBuffers} will be created.
   */
  public ConcurrentBitBuffer(BitBuffer source) {
    this.source = source;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.AbstractBitBufferDecorator#
   * getDelegate()
   */
  @Override
  public BitBuffer getDelegate() {
    BitBuffer result = current.get();
    if (result == null) {
      result = source.duplicate();
      current.set(result);
    }
    return result;
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.AbstractBitBufferDecorator#
   * duplicate()
   */
  @Override
  public BitBuffer duplicate() {
    return new ConcurrentBitBuffer(getDelegate().duplicate());
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.AbstractBitBufferDecorator#
   * slice(long)
   */
  @Override
  public BitBuffer slice(long length) {
    return new SlicedBitBuffer(duplicate(), length);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByteBuffer(int)
   */
  @Override
  public ByteBuffer readAsByteBuffer(int length) {
    return getDelegate().readAsByteBuffer(length);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByteBuffer()
   */
  @Override
  public ByteBuffer readAsByteBuffer() {
    return getDelegate().readAsByteBuffer();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#getActualBitPos()
   */
  @Override
  public long getActualBitPos() {
    return getDelegate().getActualBitPos();
  }

}
