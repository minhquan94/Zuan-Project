/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell.utils;

import static io.netty.util.internal.MathUtil.isOutOfBounds;
import static io.netty.util.internal.StringUtil.NEWLINE;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

import com.zuan.parser.utils.BinaryUtils;
import com.zuan.parser.utils.MathUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

/**
 * The Class DumpUltils.
 */
public final class DumpUltils {

  /**
   * Instantiates a new dump ultils.
   */
  private DumpUltils() {
    super();
  }

  /**
   * Pretty hex dump.
   *
   * @param byteArr
   *          the byte arr
   * @param mapValue
   *          the map value
   * @return the string
   */
  public static String prettyHexDump(byte[] byteArr, Map<Integer, Integer> mapValue) {
    ByteBuf buffer = Unpooled.copiedBuffer(byteArr);
    return prettyHexDump(buffer, buffer.readerIndex(), buffer.readableBytes(), mapValue);
  }

  /**
   * Copies the content of {@code src} to a {@link ByteBuf} using
   * {@link ByteBuf#writeBytes(byte[], int, int)}.
   *
   * @param buffer
   *          the buffer
   * @param mapValue
   *          the map value
   * @return the string
   */
  public static String prettyHexDump(ByteBuf buffer, Map<Integer, Integer> mapValue) {
    return prettyHexDump(buffer, buffer.readerIndex(), buffer.readableBytes(), mapValue);
  }

  /**
   * Returns a multi-line hexadecimal dump of the specified {@link ByteBuf} that
   * is easy to read by humans, starting at the given {@code offset} using the
   * given {@code length}.
   *
   * @param buffer
   *          the buffer
   * @param offset
   *          the offset
   * @param length
   *          the length
   * @param mapValue
   *          the map value
   * @return the string
   */
  public static String prettyHexDump(ByteBuf buffer, int offset, int length,
      Map<Integer, Integer> mapValue) {
    return HexUtil.prettyHexDump(buffer, offset, length, mapValue, 32);
  }

  /**
   * The Class HexUtil.
   */
  static final class HexUtil {

    /** The Constant BYTE2CHAR. */
    private static final char[] BYTE2CHAR = new char[256];

    /** The Constant HEXDUMP_TABLE. */
    private static final char[] HEXDUMP_TABLE = new char[256 * 4];

    /** The Constant HEXPADDING. */
    private static final String[] HEXPADDING = new String[16];

    /** The Constant HEXDUMP_ROWPREFIXES. */
    private static final String[] HEXDUMP_ROWPREFIXES = new String[65536 >>> 4];

    /** The Constant BYTE2HEX. */
    private static final String[] BYTE2HEX = new String[256];

    /** The Constant BYTEPADDING. */
    private static final String[] BYTEPADDING = new String[16];

    static {
      final char[] digits = "0123456789abcdef".toCharArray();
      for (int i = 0; i < 256; i++) {
        HEXDUMP_TABLE[i << 1] = digits[i >>> 4 & 0x0F];
        HEXDUMP_TABLE[(i << 1) + 1] = digits[i & 0x0F];
      }

      int i;

      // Generate the lookup table for hex dump paddings
      for (i = 0; i < HEXPADDING.length; i++) {
        int padding = HEXPADDING.length - i;
        StringBuilder buf = new StringBuilder(padding * 3);
        for (int j = 0; j < padding; j++) {
          buf.append("   ");
        }
        HEXPADDING[i] = buf.toString();
      }

      // Generate the lookup table for the start-offset header in each row (up
      // to 64KiB).
      for (i = 0; i < HEXDUMP_ROWPREFIXES.length; i++) {
        StringBuilder buf = new StringBuilder(12);
        buf.append(NEWLINE);
        buf.append(Long.toHexString(i << 4 & 0xFFFFFFFFL | 0x100000000L));
        buf.setCharAt(buf.length() - 9, '|');
        buf.append('|');
        HEXDUMP_ROWPREFIXES[i] = buf.toString();
      }

      // Generate the lookup table for byte-to-hex-dump conversion
      for (i = 0; i < BYTE2HEX.length; i++) {
        BYTE2HEX[i] = ' ' + StringUtil.byteToHexStringPadded(i);
      }

      // Generate the lookup table for byte dump paddings
      for (i = 0; i < BYTEPADDING.length; i++) {
        int padding = BYTEPADDING.length - i;
        StringBuilder buf = new StringBuilder(padding);
        for (int j = 0; j < padding; j++) {
          buf.append(' ');
        }
        BYTEPADDING[i] = buf.toString();
      }

      // Generate the lookup table for byte-to-char conversion
      for (i = 0; i < BYTE2CHAR.length; i++) {
        if (i <= 0x1f || i >= 0x7f) {
          BYTE2CHAR[i] = '.';
        } else {
          BYTE2CHAR[i] = (char) i;
        }
      }
    }

    /**
     * Instantiates a new hex util.
     */
    private HexUtil() {
      super();
    }

    /**
     * Pretty hex dump.
     *
     * @param buffer
     *          the buffer
     * @param offset
     *          the offset
     * @param length
     *          the length
     * @param mapValue
     *          the map value
     * @param pow2
     *          the pow2
     * @return the string
     */
    private static String prettyHexDump(ByteBuf buffer, int offset, int length,
        Map<Integer, Integer> mapValue, int width) {
      if (length == 0) {
        return StringUtil.EMPTY_STRING;
      } else {
        int rows = length / width + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80);
        appendPrettyHexDump(buf, buffer, offset, length, mapValue, 32);
        return buf.toString();
      }
    }

    /**
     * Append pretty hex dump.
     *
     * @param dump
     *          the dump
     * @param buf
     *          the buf
     * @param offset
     *          the offset
     * @param length
     *          the length
     * @param mapValue
     *          the map value
     */
    private static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf, int offset,
        int length, Map<Integer, Integer> mapValue, int width) {
      if (isOutOfBounds(offset, length, buf.capacity())) {
        throw new IndexOutOfBoundsException(
            "expected: " + "0 <= offset(" + offset + ") <= offset + length(" + length + ") <= "
                + "buf.capacity(" + buf.capacity() + ')');
      }
      if (length == 0) {
        return;
      }
      int sizeBorderTable = sizeBorderTable(width);
      dump.append(
          NEWLINE + "         +" + StringUtils.repeat('-', sizeBorderTable) + '+' + NEWLINE);
      buildHeaderWidthTable(width, dump);
      dump.append("+--------+" + StringUtils.repeat('-', sizeBorderTable) + '+'
          + StringUtils.repeat('-', width) + '+');
      final int startIndex = offset;
      final int fullRows = length >>> MathUtils.log2n(width);
      final int remainder = length & 0xF;

      // Dump the rows which have 16 bytes.
      for (int row = 0; row < fullRows; row++) {
        int rowStartIndex = (row << 4) + startIndex;
        // Per-row prefix.
        appendHexDumpRowPrefix(dump, row, rowStartIndex);
        // Hex dump
        int rowEndIndex = rowStartIndex + width;
        for (int j = rowStartIndex; j < rowEndIndex; j++) {
          if (mapValue == null || mapValue.size() <= 0 || mapValue.get(j) == null) {
            dump.append(BYTE2HEX[buf.getUnsignedByte(j)]);
            continue;
          }
          Integer bitLength = mapValue.get(j);
          int byteLegth = BinaryUtils.getByteLength(bitLength);
          for (int k = 0; k < byteLegth; k++) { // NOSONAR
            dump.append(setColor(BYTE2HEX[buf.getUnsignedByte(j)], AnsiColor.BRIGHT_GREEN));
            j = j + k;
          }
        }
        dump.append(" |");

        // ASCII dump
        for (int j = rowStartIndex; j < rowEndIndex; j++) {
          dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
        }
        dump.append('|');
      }

      // Dump the last row which has less than 16 bytes.
      if (remainder != 0) {
        int rowStartIndex = (fullRows << 4) + startIndex;
        appendHexDumpRowPrefix(dump, fullRows, rowStartIndex);

        // Hex dump
        int rowEndIndex = rowStartIndex + remainder;
        for (int j = rowStartIndex; j < rowEndIndex; j++) {
          dump.append(BYTE2HEX[buf.getUnsignedByte(j)]);
        }
        dump.append(HEXPADDING[remainder]);
        dump.append(" |");

        // Ascii dump
        for (int j = rowStartIndex; j < rowEndIndex; j++) {
          dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
        }
        dump.append(BYTEPADDING[remainder]);
        dump.append('|');
      }

      dump.append(NEWLINE + "+--------+" + StringUtils.repeat('-', sizeBorderTable) + '+'
          + StringUtils.repeat('-', width) + '+');
    }

    /**
     * Size border table.
     *
     * @param width
     *          the width
     * @return the int
     */
    private static int sizeBorderTable(int width) {
      return width * 3 + 1;
    }

    /**
     * Builds the header width table.
     *
     * @param width
     *          the width
     * @param dump
     *          the dump
     */
    private static void buildHeaderWidthTable(int width, StringBuilder dump) {
      dump.append("         |");
      for (int i = 0; i < width; i++) {
        if (i < 10) {
          dump.append("  ");
        } else {
          dump.append(" ");
        }
        dump.append(i);
      }
      dump.append(" |" + NEWLINE);
    }

    /**
     * Sets the color.
     *
     * @param value
     *          the value
     * @param color
     *          the color
     * @return the string
     */
    private static String setColor(String value, AnsiColor color) {
      return AnsiOutput.toString(color, value, AnsiColor.DEFAULT);
    }

    /**
     * Append hex dump row prefix.
     *
     * @param dump
     *          the dump
     * @param row
     *          the row
     * @param rowStartIndex
     *          the row start index
     */
    private static void appendHexDumpRowPrefix(StringBuilder dump, int row,
        int rowStartIndex) {
      if (row < HEXDUMP_ROWPREFIXES.length) {
        dump.append(HEXDUMP_ROWPREFIXES[row]);
      } else {
        dump.append(NEWLINE);
        dump.append(Long.toHexString(rowStartIndex & 0xFFFFFFFFL | 0x100000000L));
        dump.setCharAt(dump.length() - 9, '|');
        dump.append('|');
      }
    }
  }
}
