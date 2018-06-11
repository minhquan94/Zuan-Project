/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.buffer;

/**
 * An abstract base class for classes wrapping {@link BitBuffer}s to support
 * additional behavior. Implementations need to implement <em>at least</em>
 * {@link #getDelegate()}.
 */
public abstract class AbstractBitBufferDecorator implements BitBuffer {

  /**
   * Returns the {@link BitBuffer} to which all requests will be delegated.
   *
   * @return The {@link BitBuffer} to which all requests will be delegated.
   */
  public abstract BitBuffer getDelegate();

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#getBitBufBitSize()
   */

  @Override
  public long getBitBufBitSize() {
    return getDelegate().getBitBufBitSize();
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#getBitPos()
   */

  @Override
  public long getBitPos() {
    return getDelegate().getBitPos();
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsBoolean()
   */

  @Override
  public boolean readAsBoolean() {
    return getDelegate().readAsBoolean();
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsBoolean(long)
   */

  @Override
  public boolean readAsBoolean(long bitPos) {
    return getDelegate().readAsBoolean(bitPos);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsBoolean(org.codehaus.preon.
   * buffer. ByteOrder)
   */

  @Override
  public boolean readAsBoolean(ByteOrder endian) {
    return getDelegate().readAsBoolean(endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsBoolean(long,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public boolean readAsBoolean(long bitPos, ByteOrder endian) {
    return getDelegate().readAsBoolean(bitPos, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsByte(int)
   */

  @Override
  public byte readAsByte(int nrBits) {
    return getDelegate().readAsByte(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsByte(int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public byte readAsByte(int nrBits, ByteOrder endian) {
    return getDelegate().readAsByte(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsByte(int, long)
   */

  @Override
  public byte readAsByte(int nrBits, long bitPos) {
    return getDelegate().readAsByte(nrBits, bitPos);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsByte(long, int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public byte readAsByte(long bitPos, int nrBits, ByteOrder endian) {
    return getDelegate().readAsByte(bitPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsInt(int)
   */

  @Override
  public int readAsInt(int nrBits) {
    return getDelegate().readAsInt(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsInt(long, int)
   */

  @Override
  public int readAsInt(long bitPos, int nrBits) {
    return getDelegate().readAsInt(bitPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsInt(int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public int readAsInt(int nrBits, ByteOrder endian) {
    return getDelegate().readAsInt(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsInt(long, int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public int readAsInt(long bitPos, int nrBits, ByteOrder endian) {
    return getDelegate().readAsInt(bitPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsLong(int)
   */

  @Override
  public long readAsLong(int nrBits) {
    return getDelegate().readAsLong(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsLong(long, int)
   */

  @Override
  public long readAsLong(long bitPos, int nrBits) {
    return getDelegate().readAsLong(bitPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsLong(int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public long readAsLong(int nrBits, ByteOrder endian) {
    return getDelegate().readAsLong(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsLong(long, int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public long readAsLong(long bitPos, int nrBits, ByteOrder endian) {
    return getDelegate().readAsLong(bitPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsShort(int)
   */

  @Override
  public short readAsShort(int nrBits) {
    return getDelegate().readAsShort(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsShort(long, int)
   */

  @Override
  public short readAsShort(long bitPos, int nrBits) {
    return getDelegate().readAsShort(bitPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsShort(int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public short readAsShort(int nrBits, ByteOrder endian) {
    return getDelegate().readAsShort(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readAsShort(long, int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public short readAsShort(long bitPos, int nrBits, ByteOrder endian) {
    return getDelegate().readAsShort(bitPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readBits(int)
   */

  @Override
  public long readBits(int nrBits) {
    return getDelegate().readBits(nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readBits(long, int)
   */

  @Override
  public long readBits(long bitPos, int nrBits) {
    return getDelegate().readBits(bitPos, nrBits);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readBits(int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public long readBits(int nrBits, ByteOrder endian) {
    return getDelegate().readBits(nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#readBits(long, int,
   * org.codehaus.preon.buffer.ByteOrder)
   */

  @Override
  public long readBits(long bitPos, int nrBits, ByteOrder endian) {
    return getDelegate().readBits(bitPos, nrBits, endian);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#setBitPos(long)
   */

  @Override
  public void setBitPos(long bitPos) {
    getDelegate().setBitPos(bitPos);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#slice(long)
   */

  @Override
  public BitBuffer slice(long length) {
    return getDelegate().slice(length);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.buffer.BitBuffer#duplicate()
   */

  @Override
  public BitBuffer duplicate() {
    return getDelegate().duplicate();
  }

}
