/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.buffer;

import java.nio.ByteBuffer;

/**
 * A {@link BitBuffer} that acts upon a slice of the underyling
 * {@link BitBuffer}.
 */
public class SlicedBitBuffer implements BitBuffer {

  /** The {@link BitBuffer} from which a slice is 'taken'. */
  private BitBuffer delegate;

  /**
   * The start position of the slice (counted in bits from the start of delegate
   * BitBuffer).
   */
  private long startPos;

  /**
   * The end position of the slice (counted in bits from the start of the
   * delegate BitBuffer).
   */
  private long endPos;

  /**
   * Constructs a new slice.
   *
   * @param delegate
   *          The {@link BitBuffer} to slice.
   * @param length
   *          The lengthof the slize, in bits.
   */
  public SlicedBitBuffer(BitBuffer delegate, long length) {
    this.delegate = delegate;
    this.startPos = delegate.getBitPos();
    this.endPos = startPos + length;
  }

  /**
   * Instantiates a new sliced bit buffer.
   *
   * @param delegate
   *          the delegate
   * @param length
   *          the length
   * @param startPos
   *          the start pos
   */
  public SlicedBitBuffer(BitBuffer delegate, long length, long startPos) {
    this.delegate = delegate;
    this.startPos = startPos;
    this.endPos = startPos + length;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#getBitBufBitSize()
   */
  @Override
  public long getBitBufBitSize() {
    return endPos - startPos;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#getBitPos()
   */
  @Override
  public long getBitPos() {
    return delegate.getBitPos() - startPos;
  }

  /**
   * Validates if it is possible to read the <code>nrBits</code> passed in.
   *
   * @param nrBits
   *          The number of bits to read.
   */
  private void assureValidRead(int nrBits) {
    assureValidRead(delegate.getBitPos(), nrBits);
  }

  /**
   * Validates if it is possible to read the <code>nrBits</code>.
   *
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   */
  private void assureValidRead(long bitPos, int nrBits) {
    if (bitPos > endPos - nrBits) {
      throw new BitBufferUnderflowException(delegate.getBitPos() - startPos, nrBits);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsBoolean()
   */
  @Override
  public boolean readAsBoolean() {
    assureValidRead(1);
    return delegate.readAsBoolean();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsBoolean(long)
   */
  @Override
  public boolean readAsBoolean(long bitPos) {
    assureValidRead(bitPos + startPos, 1);
    return delegate.readAsBoolean(bitPos + startPos);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsBoolean(com.
   * zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public boolean readAsBoolean(ByteOrder endian) {
    assureValidRead(1);
    return delegate.readAsBoolean(endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsBoolean(long,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public boolean readAsBoolean(long bitPos, ByteOrder endian) {
    assureValidRead(bitPos + startPos, 1);
    return delegate.readAsBoolean(bitPos + startPos, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByte(int)
   */
  @Override
  public byte readAsByte(int nrBits) {
    assureValidRead(nrBits);
    return delegate.readAsByte(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByte(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public byte readAsByte(int nrBits, ByteOrder endian) {
    assureValidRead(nrBits);
    return delegate.readAsByte(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByte(int, long)
   */
  @Override
  public byte readAsByte(int nrBits, long bitPos) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readAsByte(nrBits, bitPos + startPos);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByte(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public byte readAsByte(long bitPos, int nrBits, ByteOrder endian) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readAsByte(bitPos + startPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsInt(int)
   */
  @Override
  public int readAsInt(int nrBits) {
    assureValidRead(nrBits);
    return delegate.readAsInt(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsInt(long, int)
   */
  @Override
  public int readAsInt(long bitPos, int nrBits) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readAsInt(bitPos + startPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsInt(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public int readAsInt(int nrBits, ByteOrder endian) {
    assureValidRead(nrBits);
    return delegate.readAsInt(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsInt(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public int readAsInt(long bitPos, int nrBits, ByteOrder endian) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readAsInt(bitPos + startPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsLong(int)
   */
  @Override
  public long readAsLong(int nrBits) {
    assureValidRead(nrBits);
    return delegate.readAsLong(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsLong(long, int)
   */
  @Override
  public long readAsLong(long bitPos, int nrBits) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readAsLong(bitPos + startPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsLong(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public long readAsLong(int nrBits, ByteOrder endian) {
    assureValidRead(nrBits);
    return delegate.readAsLong(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsLong(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public long readAsLong(long bitPos, int nrBits, ByteOrder endian) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readAsLong(bitPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsShort(int)
   */
  @Override
  public short readAsShort(int nrBits) {
    assureValidRead(nrBits);
    return delegate.readAsShort(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsShort(long, int)
   */
  @Override
  public short readAsShort(long bitPos, int nrBits) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readAsShort(bitPos + startPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsShort(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public short readAsShort(int nrBits, ByteOrder endian) {
    assureValidRead(nrBits);
    return delegate.readAsShort(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsShort(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public short readAsShort(long bitPos, int nrBits, ByteOrder endian) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readAsShort(bitPos + startPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readBits(int)
   */
  @Override
  public long readBits(int nrBits) {
    assureValidRead(nrBits);
    return delegate.readBits(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readBits(long, int)
   */
  @Override
  public long readBits(long bitPos, int nrBits) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readBits(bitPos + startPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readBits(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public long readBits(int nrBits, ByteOrder endian) {
    assureValidRead(nrBits);
    return delegate.readBits(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readBits(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public long readBits(long bitPos, int nrBits, ByteOrder endian) {
    assureValidRead(bitPos + startPos, nrBits);
    return delegate.readBits(bitPos + startPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#setBitPos(long)
   */
  @Override
  public void setBitPos(long bitPos) {
    if (bitPos > endPos - startPos) {
      throw new BitBufferException("Moving pointer outside of BitBuffer boundaries.");
    } else {
      delegate.setBitPos(bitPos + startPos);
    }

  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#slice(long)
   */
  @Override
  public BitBuffer slice(long length) {
    return delegate.slice(length);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#duplicate()
   */
  @Override
  public BitBuffer duplicate() {
    return new SlicedBitBuffer(delegate.duplicate(), endPos - startPos, startPos);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByteBuffer(int)
   */
  @Override
  public ByteBuffer readAsByteBuffer(int length) {
    if (delegate.getBitPos() + length * 8 > endPos) {
      throw new BitBufferUnderflowException(delegate.getBitPos(), length * 8);
    } else {
      return delegate.readAsByteBuffer(length);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByteBuffer()
   */
  @Override
  public ByteBuffer readAsByteBuffer() {
    return delegate.readAsByteBuffer();
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#getActualBitPos()
   */
  @Override
  public long getActualBitPos() {
    return delegate.getActualBitPos();
  }

}
