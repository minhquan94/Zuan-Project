/*
 * (C) Copyright Global Cybersoft (GCS) 2016. All rights reserved. Proprietary and confidential.
 */

package com.zuan.data.messages.ss;

import com.zuan.parser.codehaus.preon.annotation.BoundNumber;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;

/**
 * The Class ObsTime.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class ObsTime {

  /** The year. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte year;

  /** The month. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte month;

  /** The day. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte day;

  /** The hour. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte hour;

  /** The minute. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte minute;

  /** The second. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte second;

  /** The mili second. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte miliSecond;

  /** The spare. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte spare;

  /**
   * Gets the year.
   *
   * @return the year
   */
  public byte getYear() {
    return this.year;
  }

  /**
   * Gets the month.
   *
   * @return the month
   */
  public byte getMonth() {
    return this.month;
  }

  /**
   * Gets the day.
   *
   * @return the day
   */
  public byte getDay() {
    return this.day;
  }

  /**
   * Gets the hour.
   *
   * @return the hour
   */
  public byte getHour() {
    return this.hour;
  }

  /**
   * Gets the minute.
   *
   * @return the minute
   */
  public byte getMinute() {
    return this.minute;
  }

  /**
   * Gets the second.
   *
   * @return the second
   */
  public byte getSecond() {
    return this.second;
  }

  /**
   * Gets the mili second.
   *
   * @return the mili second
   */
  public byte getMiliSecond() {
    return this.miliSecond;
  }

  /**
   * Gets the spare.
   *
   * @return the spare
   */
  public byte getSpare() {
    return this.spare;
  }
}
