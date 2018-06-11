/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.buffer.BitBufferUnderflowException;
import com.zuan.parser.codehaus.preon.buffer.DefaultBitBuffer;

/**
 * The Class BitBufferFacility.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class BitBufferFacility {

  /** The logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(BitBufferFacility.class);

  /** The Constant BIT_PER_BYTE. */
  public static final byte BIT_PER_BYTE = 8;

  /** The Constant HALF_OF_BYTE. */
  public static final byte HALF_OF_BYTE = 4;

  /** The Constant MAXIMUM_BIT_POSITION. */
  public static final int MAXIMUM_BIT_POSITION = 7;

  /** The Constant ELEMENT_1ST. */
  public static final int ELEMENT_1ST = 0;

  /** The Constant ELEMENT_2ND. */
  public static final int ELEMENT_2ND = 1;

  /** The Constant ELEMENT_3RD. */
  public static final int ELEMENT_3RD = 2;

  /** The Constant ELEMENT_4TH. */
  public static final int ELEMENT_4TH = 3;

  /** The Constant ELEMENT_5TH. */
  public static final int ELEMENT_5TH = 4;

  /** The Constant ELEMENT_6TH. */
  public static final int ELEMENT_6TH = 5;

  /** The Constant ELEMENT_7TH. */
  public static final int ELEMENT_7TH = 6;

  /** The Constant ELEMENT_8TH. */
  public static final int ELEMENT_8TH = 7;

  /** The Constant ELEMENT_9TH. */
  public static final int ELEMENT_9TH = 8;

  /** The Constant ELEMENT_10TH. */
  public static final int ELEMENT_10TH = 9;

  /** The Constant ELEMENT_11TH. */
  public static final int ELEMENT_11TH = 10;

  /** The Constant ELEMENT_12TH. */
  public static final int ELEMENT_12TH = 11;

  /** The Constant ELEMENT_13TH. */
  public static final int ELEMENT_13TH = 12;

  /** The Constant ELEMENT_14TH. */
  public static final int ELEMENT_14TH = 13;

  /** The Constant POSITION_D7. */
  public static final int POSITION_D7 = 0;

  /** The Constant POSITION_D3. */
  public static final int POSITION_D3 = 4;

  /** The Constant TEN. */
  public static final int TEN = 10;

  /** The Constant ONE_HUNDRED. */
  public static final int ONE_HUNDRED = 100;

  /** The Constant CHAR_A. */
  public static final int CHAR_A = 1;

  /** The Constant CHAR_Z. */
  public static final int CHAR_Z = 26;

  /** The Constant CHAR_1. */
  public static final int CHAR_1 = 27;

  /** The Constant CHAR_12. */
  public static final int CHAR_12 = 39;

  /** The Constant ASCII_CHARACTER_A. */
  public static final int ASCII_CHARACTER_A = 64;

  /** The Constant notAssigned. */
  private static final String NOT_ASSIGNED = "Not assigned";

  /** The Constant noVehicle. */
  private static final String NO_VEHICLE = "No vehicle";

  /** The Constant EMPTY_STRING. */
  private static final String EMPTY_STRING = "";

  /**
   * Instantiates a new facility.
   */
  private BitBufferFacility() {
    // singleton
  }

  /**
   * Calculate bit pos.
   *
   * @param signal
   *          the signal
   * @return the long
   */
  public static long calculateBitPos(final SignalConfiguration signal) {
    return (long) signal.getByteOffset() * BIT_PER_BYTE + signal.getBitOffset();
  }

  /**
   * Calculate bit pos ex.
   *
   * @param buffer
   *          the buffer
   * @return the long
   */
  public static long calculateBitPosEx(final BitBuffer buffer) {
    return buffer.getBitPos() + Math.abs(BIT_PER_BYTE - buffer.getBitPos()) % BIT_PER_BYTE;
  }

  /**
   * Check available data.
   *
   * @param buffer
   *          the buffer
   * @param signal
   *          the signal
   * @return true, if successful
   */
  public static boolean checkAvailableData(final BitBuffer buffer,
      final SignalConfiguration signal) {
    return (buffer.getBitBufBitSize() - signal.getByteOffset() * BIT_PER_BYTE
        - signal.getBitOffset()) >= signal.getBitLength();
  }

  /**
   * Check available data.
   *
   * @param buffer
   *          the buffer
   * @param signal
   *          the signal
   * @param bitPos
   *          the bit pos
   * @return true, if successful
   */
  public static boolean checkAvailableData(final BitBuffer buffer,
      final SignalConfiguration signal, final long bitPos) {
    return buffer.getBitBufBitSize() - (bitPos + signal.getBitLength()) >= 0;
  }

  /**
   * Read as boolean.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @return true, if successful
   */
  public static boolean readAsBoolean(final BitBuffer bitBuffer, final long bitPos) {
    return bitBuffer.readAsBoolean(bitPos);
  }

  /**
   * Read as byte.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the byte
   */
  public static byte readAsByte(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    return bitBuffer.readAsByte(nrBits, bitPos);
  }

  /**
   * Read as int.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the int
   */
  public static int readAsInt(final BitBuffer bitBuffer, final long bitPos, final int nrBits) {
    return bitBuffer.readAsInt(bitPos, nrBits);
  }

  /**
   * Read as long.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the long
   */
  public static long readAsLong(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    return bitBuffer.readAsLong(bitPos, nrBits);
  }

  /**
   * Read as float.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the double
   */
  public static double readAsFloat(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    return bitBuffer.readAsLong(bitPos, nrBits) * 1.0;
  }

  /**
   * Read as double.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the double
   */
  public static double readAsDouble(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    return bitBuffer.readAsLong(bitPos, nrBits) * 1.0;
  }

  /**
   * Read as short.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the short
   */
  public static short readAsShort(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    return bitBuffer.readAsShort(bitPos, nrBits);
  }

  /**
   * Read as buffer.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the byte[]
   */
  public static byte[] readAsBuffer(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {

    bitBuffer.setBitPos(bitPos);
    final int numberOfByte = nrBits / BIT_PER_BYTE;
    byte[] bArray = new byte[numberOfByte];
    ByteBuffer.wrap(bArray);
    for (int i = 0; i < numberOfByte; i++) {
      bArray[i] = bitBuffer.readAsByte(BIT_PER_BYTE);
    }

    return bArray;
  }

  /**
   * Read as string.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the string
   */
  public static String readAsString(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    String sResult = null;
    bitBuffer.setBitPos(bitPos);
    int numberOfByte = nrBits / BIT_PER_BYTE;
    byte[] bArray = new byte[numberOfByte];

    for (int i = 0; i < numberOfByte; i++) {
      bArray[i] = bitBuffer.readAsByte(BIT_PER_BYTE);
    }

    try {
      sResult = new String(bArray, StandardCharsets.ISO_8859_1);
    } catch (BitBufferUnderflowException e) {
      LOGGER.error("Error:", e);
    }

    return sResult;
  }

  /**
   * Read as service car number.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the string
   */
  public static String readAsServiceCarNumber(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    String sResult = EMPTY_STRING;
    short value = bitBuffer.readAsShort(bitPos, nrBits);
    if (value >= CHAR_A && value <= CHAR_Z) {
      sResult = Character.toString((char) (value + ASCII_CHARACTER_A));
    }
    if (value >= CHAR_1 && value <= CHAR_12) {
      sResult = Short.toString((short) (value - CHAR_Z));
    }
    switch (value) {
    case 0:
      sResult = NOT_ASSIGNED;
      break;
    case 255:
      sResult = NO_VEHICLE;
      break;
    default:
      break;
    }
    return sResult;
  }

  /**
   * Read as calendar.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the string
   */
  public static Date readAsCalendar(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    String hyphen = "-";
    String colon = ":";
    String numberFormat = "%1$02d";
    String dot = ".";
    String space = " ";
    String format = "yyyy-MM-dd HH:mm:ss.SSS";
    int nrLoop = nrBits / HALF_OF_BYTE;
    long bitPosition = bitPos;
    int[] calendar = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    for (int i = 0; i < nrLoop; i++) {
      calendar[i] = readAsByte(bitBuffer, bitPosition, HALF_OF_BYTE);
      bitPosition += HALF_OF_BYTE;
    }
    String year = String.format(numberFormat,
        (Calendar.getInstance().get(Calendar.YEAR) / ONE_HUNDRED) * ONE_HUNDRED
            + calendar[ELEMENT_1ST] * TEN + calendar[ELEMENT_2ND]);
    String month =
        String.format(numberFormat, calendar[ELEMENT_3RD] * TEN + calendar[ELEMENT_4TH]);
    String day =
        String.format(numberFormat, calendar[ELEMENT_5TH] * TEN + calendar[ELEMENT_6TH]);
    String hour =
        String.format(numberFormat, calendar[ELEMENT_7TH] * TEN + calendar[ELEMENT_8TH]);
    String minute =
        String.format(numberFormat, calendar[ELEMENT_9TH] * TEN + calendar[ELEMENT_10TH]);
    String second =
        String.format(numberFormat, calendar[ELEMENT_11TH] * TEN + calendar[ELEMENT_12TH]);
    String milisec = String.format("%1$03d",
        calendar[ELEMENT_13TH] * ONE_HUNDRED + calendar[ELEMENT_14TH] * TEN);
    String result = year + hyphen + month + hyphen + day + space + hour + colon + minute
        + colon + second + dot + milisec + "Z\"}";
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date date = null;
    try {
      date = sdf.parse(result);
    } catch (ParseException e) {
      LOGGER.error("Can not read as Calendar data type. " + e);
    }
    return date;
  }

  /**
   * Read as hour minutes.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @return the date
   */
  public static String readAsHourMinutes(final BitBuffer bitBuffer, final long bitPos) {
    String format = "%1$02d";
    String hour = String.format(format, (readAsByte(bitBuffer, bitPos, HALF_OF_BYTE) * TEN)
        + readAsByte(bitBuffer, bitPos + HALF_OF_BYTE, HALF_OF_BYTE));
    String minutes = String.format(format,
        (readAsByte(bitBuffer, bitPos + BIT_PER_BYTE, HALF_OF_BYTE) * TEN)
            + readAsByte(bitBuffer, bitPos + HALF_OF_BYTE + BIT_PER_BYTE, HALF_OF_BYTE));
    return hour + ":" + minutes;
  }

  /**
   * Gets the byte array from bit buffer.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param nrBytes
   *          the number bytes
   * @return the byte array from bit buffer
   */
  private static byte[] getByteArrayFromBitBuffer(final BitBuffer bitBuffer,
      final int nrBytes) {
    long nrBits = BIT_PER_BYTE * (long) nrBytes;
    BitBuffer firstBits = bitBuffer.slice(nrBits);
    byte firstValue = firstBits.readAsByte((int) nrBits);
    return BigInteger.valueOf(firstValue).toByteArray();
  }

  /**
   * Combine byte array.
   *
   * @param array1
   *          the array1
   * @param array2
   *          the array2
   * @return the byte[]
   */
  private static byte[] combineByteArray(byte[] array1, byte[] array2) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      outputStream.write(array1);
      outputStream.write(array2);
    } catch (IOException e) {
      LOGGER.error("Can not combine byte array. " + e);
    }
    return outputStream.toByteArray();
  }

  /**
   * Read as separate88 short.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the short
   */
  public static short readAsSeparate88Short(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    bitBuffer.setBitPos(bitPos);
    byte[] array1 = getByteArrayFromBitBuffer(bitBuffer, 1);

    bitBuffer.setBitPos(bitPos + nrBits - BIT_PER_BYTE);
    byte[] array2 = getByteArrayFromBitBuffer(bitBuffer, 1);

    final BitBuffer buffer =
        new DefaultBitBuffer(ByteBuffer.wrap(combineByteArray(array1, array2)));

    return readAsShort(buffer, POSITION_D7, BIT_PER_BYTE + BIT_PER_BYTE);
  }

  /**
   * Read as separate88 int.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the int
   */
  public static int readAsSeparate88Int(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    bitBuffer.setBitPos(bitPos);
    byte[] array1 = getByteArrayFromBitBuffer(bitBuffer, 1);

    bitBuffer.setBitPos(bitPos + nrBits - BIT_PER_BYTE);
    byte[] array2 = getByteArrayFromBitBuffer(bitBuffer, 1);

    final BitBuffer buffer =
        new DefaultBitBuffer(ByteBuffer.wrap(combineByteArray(array1, array2)));

    return readAsInt(buffer, POSITION_D7, BIT_PER_BYTE + BIT_PER_BYTE);
  }

  /**
   * Read as separate44 short.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the short
   */
  public static short readAsSeparate44Short(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    bitBuffer.setBitPos(bitPos);
    byte[] array1 = getByteArrayFromBitBuffer(bitBuffer, 1);

    bitBuffer.setBitPos(bitPos + nrBits - BIT_PER_BYTE);
    byte[] array2 = getByteArrayFromBitBuffer(bitBuffer, 1);

    BigInteger bigInt1 = new BigInteger(array1);
    BigInteger shiftInt1 = bigInt1.shiftRight(4);
    byte[] shifted1 = shiftInt1.toByteArray();

    BigInteger bigInt2 = new BigInteger(array2);
    BigInteger shiftInt2 = bigInt2.shiftLeft(4);
    byte[] shifted2 = Arrays.copyOfRange(shiftInt2.toByteArray(), 1, 2);

    final BitBuffer buffer =
        new DefaultBitBuffer(ByteBuffer.wrap(combineByteArray(shifted1, shifted2)));

    return readAsShort(buffer, POSITION_D3, BIT_PER_BYTE);
  }

  /**
   * Read as separate44 byte.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the byte
   */
  public static byte readAsSeparate44Byte(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    bitBuffer.setBitPos(bitPos);
    byte[] array1 = getByteArrayFromBitBuffer(bitBuffer, 1);

    bitBuffer.setBitPos(bitPos + nrBits - BIT_PER_BYTE);
    byte[] array2 = getByteArrayFromBitBuffer(bitBuffer, 1);

    BigInteger bigInt1 = new BigInteger(array1);
    BigInteger shiftInt1 = bigInt1.shiftRight(4);
    byte[] shifted1 = shiftInt1.toByteArray();

    BigInteger bigInt2 = new BigInteger(array2);
    BigInteger shiftInt2 = bigInt2.shiftLeft(4);
    byte[] shifted2 = Arrays.copyOfRange(shiftInt2.toByteArray(), 1, 2);

    final BitBuffer buffer =
        new DefaultBitBuffer(ByteBuffer.wrap(combineByteArray(shifted1, shifted2)));

    return readAsByte(buffer, POSITION_D3, BIT_PER_BYTE);
  }

  /**
   * Read as combine digits.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @return the string
   */
  public static String readAsCombineDigits(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits) {
    StringBuilder sResult = new StringBuilder();
    ArrayList<String> listDigits = new ArrayList<>();
    for (long position = bitPos; position < bitPos + nrBits; position =
        position + HALF_OF_BYTE) {
      listDigits.add(Byte.toString(readAsByte(bitBuffer, position, HALF_OF_BYTE)));
    }
    int numberBit = nrBits;
    int index = 0;
    while (numberBit > 0) {
      sResult.append(listDigits.get(index));
      index++;
      numberBit -= HALF_OF_BYTE;
    }
    return sResult.toString();
  }

  /**
   * Read as combine.
   *
   * @param bitBuffer
   *          the bit buffer
   * @param bitPos
   *          the bit pos
   * @param nrBits
   *          the nr bits
   * @param bitLengthCombine
   *          the bit length combine
   * @return the string
   */
  public static String readAsCombine(final BitBuffer bitBuffer, final long bitPos,
      final int nrBits, final int bitLengthCombine) {
    StringBuilder sResult = new StringBuilder();
    long endBit = bitPos + nrBits;
    for (long position = bitPos; position < endBit; position = position + bitLengthCombine) {
      sResult.append(Byte.toString(readAsByte(bitBuffer, position, bitLengthCombine)));
    }
    return sResult.toString();
  }
}
