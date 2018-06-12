/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

/**
 * The Class DefaultBitBuffer.
 *
 * @author zuan_
 */
public class DefaultBitBuffer implements BitBuffer {

  /** The byte buffer. */
  private ByteBuffer byteBuffer;

  /** The bit pos. */
  private long bitPos;

  /** The bit buf bit size. */
  private long bitBufBitSize;

  /**
   * Constructs a new instance.
   *
   * @param inputByteBuffer
   *          input buffered byte stream
   */
  public DefaultBitBuffer(ByteBuffer inputByteBuffer) {
    this(inputByteBuffer, ((long) (inputByteBuffer.capacity())) << 3, 0L);
  }

  /**
   * Constructs a new instance.
   *
   * @param inputByteBuffer
   *          the input byte buffer
   * @param bitBufBitSize
   *          the bit buf bit size
   * @param bitPos
   *          the bit pos
   */
  private DefaultBitBuffer(ByteBuffer inputByteBuffer, long bitBufBitSize, long bitPos) {
    this.byteBuffer = inputByteBuffer;
    this.bitBufBitSize = bitBufBitSize;
    this.bitPos = bitPos;
  }

  /**
   * Read byte buffer containing binary stream and set the bit pointer position
   * to 0.
   *
   * @param fileName
   *          the file name
   */
  public DefaultBitBuffer(String fileName) {

    try (FileInputStream fis = new FileInputStream(Paths.get(fileName).toFile());
        FileChannel fc = fis.getChannel()) {

      // Get the file's size and then map it into memory
      int fileSize = (int) fc.size();
      ByteBuffer inputByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);

      this.byteBuffer = inputByteBuffer;
      bitBufBitSize = ((long) (inputByteBuffer.capacity())) << 3;
      bitPos = 0;

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#setBitPos(long)
   */
  @Override
  public void setBitPos(long bitPos) {
    this.bitPos = bitPos;
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#getBitPos()
   */
  @Override
  public long getBitPos() {
    return this.bitPos;
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#getBitBufBitSize()
   */
  @Override
  public long getBitBufBitSize() {
    return bitBufBitSize;
  }

  // readBits

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readBits(int)
   */
  @Override
  public long readBits(int nrBits) {
    return readAsLong(nrBits);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readBits(long, int)
   */
  @Override
  public long readBits(long bitPos, int nrBits) {
    return readAsLong(bitPos, nrBits);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readBits(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public long readBits(int nrBits, ByteOrder byteOrder) {
    return readAsLong(nrBits, byteOrder);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readBits(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public long readBits(long bitPos, int nrBits, ByteOrder byteOrder) {

    if (nrBits <= 8) {
      return readAsByte(bitPos, nrBits, byteOrder);
    } else if (nrBits <= 16) {
      return readAsShort(bitPos, nrBits, byteOrder);
    } else if (nrBits <= 32) {
      return readAsInt(bitPos, nrBits, byteOrder);
    } else if (nrBits <= 64) {
      return readAsLong(bitPos, nrBits, byteOrder);
    } else {
      throw new BitBufferException("Wrong number of bits to read (" + nrBits + ").");
    }
  }

  // boolean

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsBoolean()
   */
  @Override
  public boolean readAsBoolean() {
    return readAsBoolean(bitPos, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsBoolean(long)
   */
  @Override
  public boolean readAsBoolean(long bitPos) {
    return readAsBoolean(bitPos, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsBoolean(com.
   * zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public boolean readAsBoolean(ByteOrder byteOrder) {
    return readAsBoolean(bitPos, byteOrder);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsBoolean(long,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public boolean readAsBoolean(long bitPos, ByteOrder byteOrder) {
    return getResultAsInt(bitPos, 1, byteOrder, 1) == 1;
  }

  // signed byte

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByte(int)
   */
  @Override
  public byte readAsByte(int nrBits) {
    return readAsByte(bitPos, nrBits, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByte(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public byte readAsByte(int nrBits, ByteOrder byteOrder) {
    return readAsByte(bitPos, nrBits, byteOrder);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByte(int, long)
   */
  @Override
  public byte readAsByte(int nrBits, long bitPos) {
    return readAsByte(bitPos, nrBits, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByte(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public byte readAsByte(long bitPos, int nrBits, ByteOrder byteOrder) {
    return (byte) getResultAsInt(bitPos, nrBits, byteOrder, 8);
  }

  // signed short
  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsShort(int)
   */
  @Override
  public short readAsShort(int nrBits) {
    return readAsShort(bitPos, nrBits, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsShort(long, int)
   */
  @Override
  public short readAsShort(long bitPos, int nrBits) {
    return readAsShort(bitPos, nrBits, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsShort(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public short readAsShort(int nrBits, ByteOrder byteOrder) {
    return readAsShort(bitPos, nrBits, byteOrder);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsShort(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public short readAsShort(long bitPos, int nrBits, ByteOrder byteOrder) {
    return (short) getResultAsInt(bitPos, nrBits, byteOrder, 16);
  }

  // signed int

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsInt(int)
   */
  @Override
  public int readAsInt(int nrBits) {
    return readAsInt(bitPos, nrBits, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsInt(long, int)
   */
  @Override
  public int readAsInt(long bitPos, int nrBits) {
    return readAsInt(bitPos, nrBits, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsInt(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public int readAsInt(int nrBits, ByteOrder byteOrder) {
    return readAsInt(bitPos, nrBits, byteOrder);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsInt(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public int readAsInt(long bitPos, int nrBits, ByteOrder byteOrder) {
    if (getNrNecessaryBytes(bitPos, nrBits) > 4) {
      return (int) getResultAsLong(bitPos, nrBits, byteOrder, 32);
    } else {
      return getResultAsInt(bitPos, nrBits, byteOrder, 32);
    }
  }

  // signed long

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsLong(int)
   */
  @Override
  public long readAsLong(int nrBits) {
    return readAsLong(bitPos, nrBits, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsLong(long, int)
   */
  @Override
  public long readAsLong(long bitPos, int nrBits) {
    return readAsLong(bitPos, nrBits, ByteOrder.BIG_ENDIAN);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsLong(int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public long readAsLong(int nrBits, ByteOrder byteOrder) {
    return readAsLong(bitPos, nrBits, byteOrder);
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsLong(long, int,
   * com.zuan.parser.codehaus.preon.buffer.ByteOrder)
   */
  @Override
  public long readAsLong(long bitPos, int nrBits, ByteOrder byteOrder) {
    return getResultAsLong(bitPos, nrBits, byteOrder, 64);
  }

  // private methods

  /**
   * Return the minimum number of bytes that are necessary to be read in order
   * to read specified bits.
   *
   * @param bitPos
   *          position of the first bit to read in the bit buffer
   * @param nrBits
   *          number of bits to read
   * @return number of bytes to read
   */
  private static int getNrNecessaryBytes(long bitPos, int nrBits) {
    return (int) (((bitPos % 8) + nrBits + 7) / 8);
  }

  /**
   * This method shifts to the right the integer buffer containing the specified
   * bits, so that the last specified bit is the last bit in the buffer.
   *
   * @param numberBuf
   *          integer buffer containing specified bits
   * @param bitPos
   *          position of the first specified bit
   * @param nrBits
   *          number of bits to read
   * @param byteOrder
   *          the byte order
   * @return shifted integer buffer
   */
  private static int getRightShiftedNumberBufAsInt(int numberBuf, long bitPos, int nrBits,
      ByteOrder byteOrder) {
    // number of bits integer buffer needs to be shifted to the right in
    // order to reach the last bit in last byte
    long shiftBits;
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      shiftBits = 7 - ((nrBits + bitPos + 7) % 8);
    } else {
      shiftBits = bitPos % 8;
    }
    return numberBuf >> shiftBits;
  }

  /**
   * This method shifts to the right the long buffer containing the specified
   * bits, so that the last specified bit is the last bit in the buffer.
   *
   * @param numberBuf
   *          long buffer containing specified bits
   * @param bitPos
   *          position of the first specified bit
   * @param nrBits
   *          number of bits to read
   * @param byteOrder
   *          the byte order
   * @return shifted integer buffer
   */
  private static long getRightShiftedNumberBufAsLong(long numberBuf, long bitPos, int nrBits,
      ByteOrder byteOrder) {

    // number of bits integer buffer needs to be shifted to the right in
    // order to reach the last bit in last byte
    long shiftBits;
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      shiftBits = 7 - ((nrBits + bitPos + 7) % 8);
    } else {
      shiftBits = bitPos % 8;
    }
    return numberBuf >> shiftBits;
  }

  /**
   * Return a value of integer type representing the buffer storing specified
   * bits.
   *
   * @param byteOrder
   *          Endian.Little of Endian.Big order of storing bytes
   * @param nrReadBytes
   *          number of bytes that are necessary to be read in order to read
   *          specific bits
   * @param firstBytePos
   *          position of the first byte that is necessary to be read
   * @return value of all read bytes, containing specified bits
   */
  private int getNumberBufAsInt(ByteOrder byteOrder, int nrReadBytes, int firstBytePos) {

    int result = 0;
    int bytePortion;
    for (int i = 0; i < nrReadBytes; i++) {
      bytePortion = 0xFF & (byteBuffer.get(firstBytePos++));

      if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
        // reshift bytes
        result = result | bytePortion << (i << 3);
      } else {
        result = bytePortion << ((nrReadBytes - i - 1) << 3) | result;
      }
    }

    return result;
  }

  /**
   * Return a value of long type representing the buffer storing specified bits.
   *
   * @param byteOrder
   *          Endian.Little of Endian.Big order of storing bytes
   * @param nrReadBytes
   *          number of bytes that are necessary to be read in order to read
   *          specific bits
   * @param firstBytePos
   *          position of the first byte that is necessary to be read
   * @return value of all read bytes, containing specified bits
   */
  private long getNumberBufAsLong(ByteOrder byteOrder, int nrReadBytes, int firstBytePos) {
    long result = 0L;
    long bytePortion;
    for (int i = 0; i < nrReadBytes; i++) {
      bytePortion = 0xFF & (byteBuffer.get(firstBytePos++));

      if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
        // reshift bytes
        result = result | bytePortion << (i << 3);
      } else {
        result = bytePortion << ((nrReadBytes - i - 1) << 3) | result;
      }
    }

    return result;
  }

  /**
   * Check if all input parameters are correct, otherwise throw
   * BitBufferException.
   *
   * @param bitPos
   *          position of the first bit to read in the bit buffer
   * @param nrBits
   *          number of bits to read
   * @param maxNrBitsRead
   *          maximum number of bits allowed to read, based on the method return
   *          type
   */
  private void validateInputParams(long bitPos, int nrBits, int maxNrBitsRead) {

    if (nrBits < 1) {
      throw new BitBufferException(
          "Number of bits to read (" + nrBits + ") should greater than zero.");
    }

    if (bitPos < 0) {
      throw new BitBufferException("Bit position (" + bitPos + ") should be positive.");
    }

    if (maxNrBitsRead != 1 && maxNrBitsRead != 8 && maxNrBitsRead != 16 && maxNrBitsRead != 32
        && maxNrBitsRead != 64) {
      throw new BitBufferException("Max number of bits to read (" + maxNrBitsRead
          + ") should be either 1, 8, 16, 32 or 64.");
    }

    if (nrBits > maxNrBitsRead) {
      throw new BitBufferException("Cannot read " + nrBits + " bits using " + maxNrBitsRead
          + " bit long numberBuf (bitPos=" + bitPos + ").");
    }

    if (bitPos + nrBits > bitBufBitSize) {
      throw new BitBufferUnderflowException(bitPos, nrBits);
    }
  }

  /**
   * Return an integral mask with a given number of 1's at least significant bit
   * positions.
   *
   * @param nrBits
   *          number of bits to read
   * @return integer value of the mask
   */
  private static int getMaskAsInt(int nrBits) {
    return 0xFFFFFFFF >>> (32 - nrBits);
  }

  /**
   * Return an integral mask with a given number of 1's at least significant bit
   * positions.
   *
   * @param nrBits
   *          number of bits to read
   * @return long value of the mask
   */
  private static long getMaskAsLong(int nrBits) {
    return 0xFFFFFFFFFFFFFFFFL >>> (64 - nrBits);
  }

  /**
   * Calculates the value represented by the given bits.
   *
   * @param bitPos
   *          position of the first bit to read in the bit buffer
   * @param nrBits
   *          number of bits to read
   * @param byteOrder
   *          order of reading bytes (either Endian.Big or Endian.Little)
   * @param maxNrBitsRead
   *          maximum number of bits allowed to read, based on the method return
   *          type
   * @return the integer value represented by the given bits
   */
  private int getResultAsInt(long bitPos, int nrBits, ByteOrder byteOrder, int maxNrBitsRead) {

    // check if input params are correct otherwise throw BitBufferException
    validateInputParams(bitPos, nrBits, maxNrBitsRead);

    // min number of bytes covering specified bits
    int nrReadBytes = getNrNecessaryBytes(bitPos, nrBits);

    // buffer containing specified bits
    int numberBuf = getNumberBufAsInt(byteOrder, nrReadBytes, (int) (bitPos >> 3));

    // mask leaving only specified bits
    int mask = getMaskAsInt(nrBits);

    // apply the mask for to the right shifted number buffer with the
    // specific bits to the most right
    int result = mask & getRightShiftedNumberBufAsInt(numberBuf, bitPos, nrBits, byteOrder);

    // increase bit pointer position by the number of read bits
    this.bitPos = bitPos + nrBits;

    return result;
  }

  /**
   * Calculates the value represented by the given bits.
   *
   * @param bitPos
   *          position of the first bit to read in the bit buffer
   * @param nrBits
   *          number of bits to read
   * @param byteOrder
   *          order of reading bytes (either Endian.Big or Endian.Little)
   * @param maxNrBitsRead
   *          maximum number of bits allowed to read, based on the method return
   *          type
   * @return the long value represented by the given bits
   */
  private long getResultAsLong(long bitPos, int nrBits, ByteOrder byteOrder,
      int maxNrBitsRead) {

    // check if input params are correct otherwise throw BitBufferException
    validateInputParams(bitPos, nrBits, maxNrBitsRead);

    // min number of bytes covering specified bits
    int nrReadBytes = getNrNecessaryBytes(bitPos, nrBits);

    // buffer containing specified bits
    long numberBuf = getNumberBufAsLong(byteOrder, nrReadBytes, (int) (bitPos >> 3));

    // mask leaving only specified bits
    long mask = getMaskAsLong(nrBits);

    // apply the mask for to the right shifted number buffer with the
    // specific bits to the most right
    long result = mask & getRightShiftedNumberBufAsLong(numberBuf, bitPos, nrBits, byteOrder);

    // increase bit pointer position by the number of read bits
    this.bitPos = bitPos + nrBits;

    return result;
  }

  /**
   * Getter for inputByteBuf.
   *
   * @return Returns the inputByteBuf.
   */
  protected ByteBuffer getInputByteBuf() {
    return byteBuffer;
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#slice(long)
   */
  @Override
  public BitBuffer slice(long length) {
    BitBuffer result = new SlicedBitBuffer(duplicate(), length);
    setBitPos(getBitPos() + length);
    return result;
  }

  // JavaDoc inherited

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#duplicate()
   */
  @Override
  public BitBuffer duplicate() {
    return new DefaultBitBuffer(byteBuffer.duplicate(), bitBufBitSize, bitPos);
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByteBuffer(int)
   */
  @Override
  public ByteBuffer readAsByteBuffer(int length) throws BitBufferUnderflowException {

    if ((this.bitPos % 8) != 0) {
      throw new BitBufferException(
          "8-bit alignment exception. Bit position (" + bitPos + ") should be 8-bit aligned");
    }

    int bitsToRead = length << 3; // == (length * 8)

    if (getBitPos() + bitsToRead > getBitBufBitSize()) {
      throw new BitBufferUnderflowException(getBitPos(), bitsToRead);
    }

    int sliceStartPosition = (int) (this.bitPos >>> 3);// == (bitPos / 8)

    ByteBuffer slicedByteBuffer = this.slice(sliceStartPosition, length);

    // ByteBuffer byteBuffer = ByteBuffer.wrap(this.byteBuffer.array(),

    this.bitPos = bitPos + bitsToRead;

    return slicedByteBuffer;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#readAsByteBuffer()
   */
  @Override
  public ByteBuffer readAsByteBuffer() {
    ByteBuffer buffer = byteBuffer.duplicate();
    buffer.rewind();
    return buffer;
  }

  /**
   * Work around that allows creation of a sub-view of a larger byte buffer.
   * <p/>
   * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5071718
   *
   * @param slicePosition
   *          - Start position of the slice (e.g. sub-view) in the byte buffer
   * @param length
   *          - Length of the slice (e.g. sub-view) in bytes, measured from the
   *          positions
   * @return Returns the sliced {@link ByteBuffer}. Original buffer is left in
   *         its original state;
   */
  private ByteBuffer slice(int slicePosition, int length) {

    int currentBufferPosition = this.byteBuffer.position();
    int currentBufferLimit = this.byteBuffer.limit();

    this.byteBuffer.position(slicePosition).limit(slicePosition + length);

    ByteBuffer slicedByteBuffer = this.byteBuffer.slice();

    // Revert the buffer in its original state
    this.byteBuffer.position(currentBufferPosition).limit(currentBufferLimit);

    return slicedByteBuffer;
  }

  /*
   * (non-Javadoc)
   * @see com.zuan.parser.codehaus.preon.buffer.BitBuffer#getActualBitPos()
   */
  @Override
  public long getActualBitPos() {
    return bitPos;
  }

}
