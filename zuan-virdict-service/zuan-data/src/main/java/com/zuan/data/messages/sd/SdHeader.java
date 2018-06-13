/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.data.messages.sd;

import java.util.Calendar;
import java.util.Date;

import com.zuan.parser.codehaus.preon.annotation.BoundNumber;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;

/**
 * The Class SdHeaderInformation.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SdHeader {

  /** The destination device id. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private short destinationDeviceId;

  /** The reserved. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte reserved;

  /** The data length. */
  @BoundNumber(size = "16", byteOrder = ByteOrder.BIG_ENDIAN)
  private int dataLength;

  /** The year h. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte yearH;

  /** The year l. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte yearL;

  /** The month h. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte monthH;

  /** The month l. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte monthL;

  /** The day h. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte dayH;

  /** The day l. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte dayL;

  /** The hour h. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte hourH;

  /** The hour l. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte hourL;

  /** The minute h. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte minuteH;

  /** The minute l. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte minuteL;

  /** The second h. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte secondH;

  /** The second l. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte secondL;

  /** The milisec h. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte milisecH;

  /** The milisec l. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte milisecL;

  /** The spare byte1. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte spareByte1;

  /** The sd number. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private short sdNumber;

  /** The network unit i d1 vehicle i d12. */
  @BoundNumber(size = "2", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte networkUnitID1VehicleID12;

  /** The network unit i d1 vehicle i d1. */
  @BoundNumber(size = "2", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte networkUnitID1VehicleID1;

  /** The network unit i d0 vehicle i d12. */
  @BoundNumber(size = "2", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte networkUnitID0VehicleID12;

  /** The network unit i d0 vehicle i d1. */
  @BoundNumber(size = "2", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte networkUnitID0VehicleID1;

  /** The network unit i d3 vehicle i d12. */
  @BoundNumber(size = "2", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte networkUnitID3VehicleID12;

  /** The network unit i d3 vehicle i d1. */
  @BoundNumber(size = "2", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte networkUnitID3VehicleID1;

  /** The network unit i d2 vehicle i d12. */
  @BoundNumber(size = "2", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte networkUnitID2VehicleID12;

  /** The network unit i d2 vehicle i d1. */
  @BoundNumber(size = "2", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte networkUnitID2VehicleID1;

  /** The coupling status. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte couplingStatus;

  /** The self unit network unit id. */
  @BoundNumber(size = "4", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte selfUnitNetworkUnitID;

  /** The sequential counter. */
  @BoundNumber(size = "32", byteOrder = ByteOrder.BIG_ENDIAN)
  private long sequentialCounter;

  /** The unit id. */
  @BoundNumber(size = "24", byteOrder = ByteOrder.BIG_ENDIAN)
  private int unitId;

  /**
   * Gets the raw destination device id.
   *
   * @return the raw destination device id
   */
  public short getRawDestinationDeviceId() {
    return this.destinationDeviceId;
  }

  /**
   * Gets the destination device id.
   *
   * @return the destination device id
   */
  public short getDestinationDeviceId() {
    return this.destinationDeviceId;
  }

  /**
   * Gets the raw reserved.
   *
   * @return the raw reserved
   */
  public byte getRawReserved() {
    return this.reserved;
  }

  /**
   * Gets the reserved.
   *
   * @return the reserved
   */
  public byte getReserved() {
    return this.reserved;
  }

  /**
   * Gets the raw data length.
   *
   * @return the raw data length
   */
  public int getRawDataLength() {
    return this.dataLength;
  }

  /**
   * Gets the data length.
   *
   * @return the data length
   */
  public int getDataLength() {
    return this.dataLength;
  }

  /**
   * Gets the raw year h.
   *
   * @return the raw year h
   */
  public byte getRawYearH() {
    return this.yearH;
  }

  /**
   * Gets the year h.
   *
   * @return the year h
   */
  public byte getYearH() {
    return this.yearH;
  }

  /**
   * Gets the raw year l.
   *
   * @return the raw year l
   */
  public byte getRawYearL() {
    return this.yearL;
  }

  /**
   * Gets the year l.
   *
   * @return the year l
   */
  public byte getYearL() {
    return this.yearL;
  }

  /**
   * Gets the raw month h.
   *
   * @return the raw month h
   */
  public byte getRawMonthH() {
    return this.monthH;
  }

  /**
   * Gets the month h.
   *
   * @return the month h
   */
  public byte getMonthH() {
    return this.monthH;
  }

  /**
   * Gets the raw month l.
   *
   * @return the raw month l
   */
  public byte getRawMonthL() {
    return this.monthL;
  }

  /**
   * Gets the month l.
   *
   * @return the month l
   */
  public byte getMonthL() {
    return this.monthL;
  }

  /**
   * Gets the raw day h.
   *
   * @return the raw day h
   */
  public byte getRawDayH() {
    return this.dayH;
  }

  /**
   * Gets the day h.
   *
   * @return the day h
   */
  public byte getDayH() {
    return this.dayH;
  }

  /**
   * Gets the raw day l.
   *
   * @return the raw day l
   */
  public byte getRawDayL() {
    return this.dayL;
  }

  /**
   * Gets the day l.
   *
   * @return the day l
   */
  public byte getDayL() {
    return this.dayL;
  }

  /**
   * Gets the raw hour h.
   *
   * @return the raw hour h
   */
  public byte getRawHourH() {
    return this.hourH;
  }

  /**
   * Gets the hour h.
   *
   * @return the hour h
   */
  public byte getHourH() {
    return this.hourH;
  }

  /**
   * Gets the raw hour l.
   *
   * @return the raw hour l
   */
  public byte getRawHourL() {
    return this.hourL;
  }

  /**
   * Gets the hour l.
   *
   * @return the hour l
   */
  public byte getHourL() {
    return this.hourL;
  }

  /**
   * Gets the raw minute h.
   *
   * @return the raw minute h
   */
  public byte getRawMinuteH() {
    return this.minuteH;
  }

  /**
   * Gets the minute h.
   *
   * @return the minute h
   */
  public byte getMinuteH() {
    return this.minuteH;
  }

  /**
   * Gets the raw minute l.
   *
   * @return the raw minute l
   */
  public byte getRawMinuteL() {
    return this.minuteL;
  }

  /**
   * Gets the minute l.
   *
   * @return the minute l
   */
  public byte getMinuteL() {
    return this.minuteL;
  }

  /**
   * Gets the raw second h.
   *
   * @return the raw second h
   */
  public byte getRawSecondH() {
    return this.secondH;
  }

  /**
   * Gets the second h.
   *
   * @return the second h
   */
  public byte getSecondH() {
    return this.secondH;
  }

  /**
   * Gets the raw second l.
   *
   * @return the raw second l
   */
  public byte getRawSecondL() {
    return this.secondL;
  }

  /**
   * Gets the second l.
   *
   * @return the second l
   */
  public byte getSecondL() {
    return this.secondL;
  }

  /**
   * Gets the raw milisec h.
   *
   * @return the raw milisec h
   */
  public byte getRawMilisecH() {
    return this.milisecH;
  }

  /**
   * Gets the milisec h.
   *
   * @return the milisec h
   */
  public byte getMilisecH() {
    return this.milisecH;
  }

  /**
   * Gets the raw milisec l.
   *
   * @return the raw milisec l
   */
  public byte getRawMilisecL() {
    return this.milisecL;
  }

  /**
   * Gets the milisec l.
   *
   * @return the milisec l
   */
  public byte getMilisecL() {
    return this.milisecL;
  }

  /**
   * Gets the raw spare byte1.
   *
   * @return the raw spare byte1
   */
  public byte getRawSpareByte1() {
    return this.spareByte1;
  }

  /**
   * Gets the spare byte1.
   *
   * @return the spare byte1
   */
  public byte getSpareByte1() {
    return this.spareByte1;
  }

  /**
   * Gets the raw sd number.
   *
   * @return the raw sd number
   */
  public short getRawSdNumber() {
    return this.sdNumber;
  }

  /**
   * Gets the sd number.
   *
   * @return the sd number
   */
  public short getSdNumber() {
    return this.sdNumber;
  }

  /**
   * Gets the raw network unit i d1 vehicle i d12.
   *
   * @return the raw network unit i d1 vehicle i d12
   */
  public byte getRawNetworkUnitID1VehicleID12() {
    return this.networkUnitID1VehicleID12;
  }

  /**
   * Gets the network unit i d1 vehicle i d12.
   *
   * @return the network unit i d1 vehicle i d12
   */
  public byte getNetworkUnitID1VehicleID12() {
    return this.networkUnitID1VehicleID12;
  }

  /**
   * Gets the raw network unit i d1 vehicle i d1.
   *
   * @return the raw network unit i d1 vehicle i d1
   */
  public byte getRawNetworkUnitID1VehicleID1() {
    return this.networkUnitID1VehicleID1;
  }

  /**
   * Gets the network unit i d1 vehicle i d1.
   *
   * @return the network unit i d1 vehicle i d1
   */
  public byte getNetworkUnitID1VehicleID1() {
    return this.networkUnitID1VehicleID1;
  }

  /**
   * Gets the raw network unit i d0 vehicle i d12.
   *
   * @return the raw network unit i d0 vehicle i d12
   */
  public byte getRawNetworkUnitID0VehicleID12() {
    return this.networkUnitID0VehicleID12;
  }

  /**
   * Gets the network unit i d0 vehicle i d12.
   *
   * @return the network unit i d0 vehicle i d12
   */
  public byte getNetworkUnitID0VehicleID12() {
    return this.networkUnitID0VehicleID12;
  }

  /**
   * Gets the raw network unit i d0 vehicle i d1.
   *
   * @return the raw network unit i d0 vehicle i d1
   */
  public byte getRawNetworkUnitID0VehicleID1() {
    return this.networkUnitID0VehicleID1;
  }

  /**
   * Gets the network unit i d0 vehicle i d1.
   *
   * @return the network unit i d0 vehicle i d1
   */
  public byte getNetworkUnitID0VehicleID1() {
    return this.networkUnitID0VehicleID1;
  }

  /**
   * Gets the raw network unit i d3 vehicle i d12.
   *
   * @return the raw network unit i d3 vehicle i d12
   */
  public byte getRawNetworkUnitID3VehicleID12() {
    return this.networkUnitID3VehicleID12;
  }

  /**
   * Gets the network unit i d3 vehicle i d12.
   *
   * @return the network unit i d3 vehicle i d12
   */
  public byte getNetworkUnitID3VehicleID12() {
    return this.networkUnitID3VehicleID12;
  }

  /**
   * Gets the raw network unit i d3 vehicle i d1.
   *
   * @return the raw network unit i d3 vehicle i d1
   */
  public byte getRawNetworkUnitID3VehicleID1() {
    return this.networkUnitID3VehicleID1;
  }

  /**
   * Gets the network unit i d3 vehicle i d1.
   *
   * @return the network unit i d3 vehicle i d1
   */
  public byte getNetworkUnitID3VehicleID1() {
    return this.networkUnitID3VehicleID1;
  }

  /**
   * Gets the raw network unit i d2 vehicle i d12.
   *
   * @return the raw network unit i d2 vehicle i d12
   */
  public byte getRawNetworkUnitID2VehicleID12() {
    return this.networkUnitID2VehicleID12;
  }

  /**
   * Gets the network unit i d2 vehicle i d12.
   *
   * @return the network unit i d2 vehicle i d12
   */
  public byte getNetworkUnitID2VehicleID12() {
    return this.networkUnitID2VehicleID12;
  }

  /**
   * Gets the raw network unit i d2 vehicle i d1.
   *
   * @return the raw network unit i d2 vehicle i d1
   */
  public byte getRawNetworkUnitID2VehicleID1() {
    return this.networkUnitID2VehicleID1;
  }

  /**
   * Gets the network unit i d2 vehicle i d1.
   *
   * @return the network unit i d2 vehicle i d1
   */
  public byte getNetworkUnitID2VehicleID1() {
    return this.networkUnitID2VehicleID1;
  }

  /**
   * Gets the raw coupling status.
   *
   * @return the raw coupling status
   */
  public byte getRawCouplingStatus() {
    return this.couplingStatus;
  }

  /**
   * Gets the coupling status.
   *
   * @return the coupling status
   */
  public byte getCouplingStatus() {
    return this.couplingStatus;
  }

  /**
   * Gets the raw self unit network unit id.
   *
   * @return the raw self unit network unit id
   */
  public byte getRawSelfUnitNetworkUnitID() {
    return this.selfUnitNetworkUnitID;
  }

  /**
   * Gets the self unit network unit id.
   *
   * @return the self unit network unit id
   */
  public byte getSelfUnitNetworkUnitID() {
    return this.selfUnitNetworkUnitID;
  }

  /**
   * Gets the raw sequential counter.
   *
   * @return the raw sequential counter
   */
  public long getRawSequentialCounter() {
    return this.sequentialCounter;
  }

  /**
   * Gets the sequential counter.
   *
   * @return the sequential counter
   */
  public long getSequentialCounter() {
    return this.sequentialCounter;
  }

  /**
   * Gets the raw unit id.
   *
   * @return the raw unit id
   */
  public int getRawUnitId() {
    return this.unitId;
  }

  /**
   * Gets the unit id.
   *
   * @return the unit id
   */
  public int getUnitId() {
    return this.unitId;
  }

  /**
   * Get a JSON string, which is built up of getting value of all properties of
   * this class definition.
   *
   * @return The JSON string
   */
  @Override
  public String toString() {
    final StringBuilder jsonBuilder = new StringBuilder(4);
    jsonBuilder.append('{').append("\"destinationDeviceId\":").append(this.destinationDeviceId)
        .append(',').append("\"reserved\":").append(this.reserved).append(',')
        .append("\"dataLength\":").append(this.dataLength).append(',').append("\"yearH\":")
        .append(this.yearH).append(',').append("\"yearL\":").append(this.yearL).append(',')
        .append("\"monthH\":").append(this.monthH).append(',').append("\"monthL\":")
        .append(this.monthL).append(',').append("\"dayH\":").append(this.dayH).append(',')
        .append("\"dayL\":").append(this.dayL).append(',').append("\"hourH\":")
        .append(this.hourH).append(',').append("\"hourL\":").append(this.hourL).append(',')
        .append("\"minuteH\":").append(this.minuteH).append(',').append("\"minuteL\":")
        .append(this.minuteL).append(',').append("\"secondH\":").append(this.secondH)
        .append(',').append("\"secondL\":").append(this.secondL).append(',')
        .append("\"milisecH\":").append(this.milisecH).append(',').append("\"milisecL\":")
        .append(this.milisecL).append(',').append("\"spareByte1\":").append(this.spareByte1)
        .append(',').append("\"sdNumber\":").append(this.sdNumber).append(',')
        .append("\"networkUnitID1VehicleID12\":").append(this.networkUnitID1VehicleID12)
        .append(',').append("\"networkUnitID1VehicleID1\":")
        .append(this.networkUnitID1VehicleID1).append(',')
        .append("\"networkUnitID0VehicleID12\":").append(this.networkUnitID0VehicleID12)
        .append(',').append("\"networkUnitID0VehicleID1\":")
        .append(this.networkUnitID0VehicleID1).append(',')
        .append("\"networkUnitID3VehicleID12\":").append(this.networkUnitID3VehicleID12)
        .append(',').append("\"networkUnitID3VehicleID1\":")
        .append(this.networkUnitID3VehicleID1).append(',')
        .append("\"networkUnitID2VehicleID12\":").append(this.networkUnitID2VehicleID12)
        .append(',').append("\"networkUnitID2VehicleID1\":")
        .append(this.networkUnitID2VehicleID1).append(',').append("\"couplingStatus\":")
        .append(this.couplingStatus).append(',').append("\"selfUnitNetworkUnitID\":")
        .append(this.selfUnitNetworkUnitID).append(',').append("\"sequentialCounter\":")
        .append(this.sequentialCounter).append(',').append("\"unitId\":").append(this.unitId)
        .append('}');
    return jsonBuilder.toString();
  }

  /**
   * Gets the sd time.
   *
   * @return the sd time
   */
  public String getSdTime() {
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    calendar.setTime(date);
    int century = (calendar.get(Calendar.YEAR) / 100);
    int year = century * 100 + getYearH() * 10 + getYearL();
    int month = getMonthH() * 10 + getMonthL();
    int day = getDayH() * 10 + getDayL();
    int hour = getHourH() * 10 + getHourL();
    int minute = getMinuteH() * 10 + getMinuteL();
    int second = getSecondH() * 10 + getSecondL();
    int miliSecond = getMilisecH() * 100 + getMilisecL() * 10;
    StringBuilder time = new StringBuilder(256);
    time.append(year).append("-").append(month).append("-").append(day).append(" ")
        .append(hour).append(":").append(minute).append(":").append(second).append(".");
    if (getMilisecH() == 0) {
      time.append("0");
    }
    time.append(miliSecond);
    return time.toString();
  }
}
