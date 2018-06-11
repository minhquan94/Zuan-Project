/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;

import com.zuan.parser.codehaus.preon.buffer.ByteOrder;

/**
 * A {@link Channel} type of abstraction for writing bits. Note that in Preon, a
 * {@link BitChannel} is currently for output only.
 */
public interface BitChannel {

  /**
   * Writes the boolean value as a bit to the channel. (Writes an 1 in case of
   * <code>true</code> value, and 0 otherwise.)
   *
   * @param value
   *          the value
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  void write(boolean value) throws IOException;

  /**
   * Writes <code>nrbits</code> bits of the byte to the channel.
   *
   * @param nrbits
   *          the nrbits
   * @param value
   *          the value
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  void write(int nrbits, byte value) throws IOException;

  /**
   * Writes <code>nrbits</code> bits of the int value to the channel, based on
   * the {@link ByteOrder} passed in, in case the number of bits exceeds 8.
   *
   * @param nrbits
   *          the nrbits
   * @param value
   *          the value
   * @param byteOrder
   *          the byte order
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  void write(int nrbits, int value, ByteOrder byteOrder) throws IOException;

  /**
   * Writes <code>nrbits</code> bits of the long value to the channel, based on
   * the {@link ByteOrder} passed in, in case the number of bits exceeds 8.
   *
   * @param nrbits
   *          the nrbits
   * @param value
   *          the value
   * @param byteOrder
   *          the byte order
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  void write(int nrbits, long value, ByteOrder byteOrder) throws IOException;

  /**
   * Writes <code>nrbits</code> bits of the short value to the channel, based on
   * the {@link ByteOrder} passed in, in case the number of bits exceeds 8.
   *
   * @param nrbits
   *          the nrbits
   * @param value
   *          the value
   * @param byteOrder
   *          the byte order
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  void write(int nrbits, short value, ByteOrder byteOrder) throws IOException;

  /**
   * Writes <code>length</code> bytes from the <code>src</code> array of bytes
   * to the channel, starting at the position indicated by <code>offset</code>.
   *
   * @param src
   *          the src
   * @param offset
   *          the offset
   * @param length
   *          the length
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  void write(byte[] src, int offset, int length) throws IOException;

  /**
   * Writes the contents of the {@link java.nio.ByteBuffer} to the channel.
   *
   * @param buffer
   *          the buffer
   * @return the long
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  long write(ByteBuffer buffer) throws IOException;

  /**
   * Returns the position of the bit pointer in the current byte.
   *
   * @return the relative bit pos
   */
  int getRelativeBitPos();

  /**
   * Closes the channel.
   *
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  void close() throws IOException;

}
