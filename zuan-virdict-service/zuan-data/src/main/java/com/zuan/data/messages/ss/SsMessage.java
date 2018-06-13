/*
 * (C) Copyright Global Cybersoft (GCS) 2016. All rights reserved. Proprietary and confidential.
 */

package com.zuan.data.messages.ss;

import com.zuan.data.messages.Header;
import com.zuan.data.messages.MessageBase;
import com.zuan.parser.codehaus.preon.annotation.BoundList;
import com.zuan.parser.codehaus.preon.annotation.BoundNumber;
import com.zuan.parser.codehaus.preon.annotation.BoundObject;
import com.zuan.parser.codehaus.preon.annotation.BoundString;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;

/**
 * The Class SsMessage.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SsMessage implements MessageBase {

  /** The header info mess. */
  @BoundObject
  private Header header;

  /** The packet id. */
  @BoundNumber(size = "16", byteOrder = ByteOrder.BIG_ENDIAN)
  private short packetId;

  /** The packet version. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte packetVersion;

  /** The obs version revision1. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte obsVersionRevision1;

  /** The obs version revision2. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte obsVersionRevision2;

  /** The obs version revision3. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte obsVersionRevision3;

  /** The obs version revision4. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte obsVersionRevision4;

  /** The obs version revision5. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte obsVersionRevision5;

  /** The obs time. */
  @BoundObject
  private ObsTime obsTime;

  /** The gps time. */
  @BoundObject
  private ObsTime gpsTime;

  /** The gps loc is enable. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocIsEnable;

  /** The gps loc longitude. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocLongitude;

  /** The gps loc latitude. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocLatitude;

  /** The gps loc lat degrees. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocLatDegrees;

  /** The gps loc lat minute upper. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocLatMinuteUpper;

  /** The gps loc lat minute lower. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocLatMinuteLower;

  /** The gps loc long degrees. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocLongDegrees;

  /** The gps loc long minute upper. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocLongMinuteUpper;

  /** The gps loc long minute lower. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte gpsLocLongMinuteLower;

  /** The spare1. */
  @BoundNumber(size = "24", byteOrder = ByteOrder.BIG_ENDIAN)
  private int spare1;

  /** The status info1 disk alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo1DiskAlarm;

  /** The status info1 temp alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo1TempAlarm;

  /** The status info1 clock alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo1ClockAlarm;

  /** The status info1 spare. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo1Spare;

  /** The status info2 ntp alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo2NtpAlarm;

  /** The status info2 gps alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo2GpsAlarm;

  /** The status info2 router alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo2RouterAlarm;

  /** The status info2 spare. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo2Spare;

  /** The status info33 g c4 alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo33GC4Alarm;

  /** The status info33 g c3 alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo33GC3Alarm;

  /** The status info33 g c2 alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo33GC2Alarm;

  /** The status info33 g c1 alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo33GC1Alarm;

  /** The status info3 wifi card alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo3WifiCardAlarm;

  /** The status info3 mlan alarm. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo3MLANAlarm;

  /** The status info3 spare. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo3Spare;

  /** The status info43 g c4 connecting. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo43GC4Connecting;

  /** The status info43 g c3 connecting. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo43GC3Connecting;

  /** The status info43 g c2 connecting. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo43GC2Connecting;

  /** The status info43 g c1 connecting. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo43GC1Connecting;

  /** The status info4 w card connecting. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo4WCardConnecting;

  /** The status info4 mlan mode on. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo4MLANModeOn;

  /** The status info4 spare. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte statusInfo4Spare;

  /** The pis realtime mess up code. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte pisRealtimeMessUpCode;

  /** The pis realtime mess low code. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte pisRealtimeMessLowCode;

  /** The pre warm up signal1. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte preWarmUpSignal1;

  /** The pre warm up signal2. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte preWarmUpSignal2;

  /** The pre warm up signal3. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte preWarmUpSignal3;

  /** The pre warm up signal4. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte preWarmUpSignal4;

  /** The pre warm up signal spare. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte preWarmUpSignalSpare;

  /** The shore data info timetable. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte shoreDataInfoTimetable;

  /** The shore data info seat. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte shoreDataInfoSeat;

  /** The shore data info pis. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte shoreDataInfoPis;

  /** The shore data info tms. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte shoreDataInfoTms;

  /** The shore data info traction. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte shoreDataInfoTraction;

  /** The shore data info aps. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte shoreDataInfoAps;

  /** The shore data info spare1. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte shoreDataInfoSpare1;

  /** The shore data info spare2. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte shoreDataInfoSpare2;

  /** The response to ds contents. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte responseToDsContents;

  /** The spare2. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte spare2;

  /** The td file creation flag. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte tdFileCreationFlag;

  /** The ec data creation flag. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte ecDataCreationFlag;

  /** The journey file creation flag. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte journeyFileCreationFlag;

  /** The pirs flag. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte pirsFlag;

  /** The das manifest flag. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte dasManifestFlag;

  /** The das log flag. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte dasLogFlag;

  /** The cctv data ready. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte cctvDataReady;

  /** The cctv return code. */
  @BoundNumber(size = "8", byteOrder = ByteOrder.BIG_ENDIAN)
  private byte cctvReturnCode;

  /** The spare3. */
  @BoundList(size = "45")
  private byte[] spare3;

  /** The file name. */
  @BoundString(size = "64")
  private String fileName;

  /** The directory. */
  @BoundString(size = "64")
  private String directory;

  /**
   * Gets the header.
   *
   * @return the header
   */
  public Header getHeader() {
    return header;
  }

  /**
   * Gets the packet id.
   *
   * @return the packet id
   */
  public short getPacketId() {
    return this.packetId;
  }

  /**
   * Gets the packet version.
   *
   * @return the packet version
   */
  public byte getPacketVersion() {
    return this.packetVersion;
  }

  /**
   * Gets the obs version revision1.
   *
   * @return the obs version revision1
   */
  public byte getObsVersionRevision1() {
    return this.obsVersionRevision1;
  }

  /**
   * Gets the obs version revision2.
   *
   * @return the obs version revision2
   */
  public byte getObsVersionRevision2() {
    return this.obsVersionRevision2;
  }

  /**
   * Gets the obs version revision3.
   *
   * @return the obs version revision3
   */
  public byte getObsVersionRevision3() {
    return this.obsVersionRevision3;
  }

  /**
   * Gets the obs version revision4.
   *
   * @return the obs version revision4
   */
  public byte getObsVersionRevision4() {
    return this.obsVersionRevision4;
  }

  /**
   * Gets the obs version revision5.
   *
   * @return the obs version revision5
   */
  public byte getObsVersionRevision5() {
    return this.obsVersionRevision5;
  }

  /**
   * Gets the obs time.
   *
   * @return the obs time
   */
  public ObsTime getObsTime() {
    return this.obsTime;
  }

  /**
   * Gets the gps time.
   *
   * @return the gps time
   */
  public ObsTime getGpsTime() {
    return this.gpsTime;
  }

  /**
   * Gets the gps loc is enable.
   *
   * @return the gps loc is enable
   */
  public byte getGpsLocIsEnable() {
    return this.gpsLocIsEnable;
  }

  /**
   * Gets the gps loc longitude.
   *
   * @return the gps loc longitude
   */
  public byte getGpsLocLongitude() {
    return this.gpsLocLongitude;
  }

  /**
   * Gets the gps loc latitude.
   *
   * @return the gps loc latitude
   */
  public byte getGpsLocLatitude() {
    return this.gpsLocLatitude;
  }

  /**
   * Gets the gps loc lat degrees.
   *
   * @return the gps loc lat degrees
   */
  public byte getGpsLocLatDegrees() {
    return this.gpsLocLatDegrees;
  }

  /**
   * Gets the gps loc lat minute upper.
   *
   * @return the gps loc lat minute upper
   */
  public byte getGpsLocLatMinuteUpper() {
    return this.gpsLocLatMinuteUpper;
  }

  /**
   * Gets the gps loc lat minute lower.
   *
   * @return the gps loc lat minute lower
   */
  public byte getGpsLocLatMinuteLower() {
    return this.gpsLocLatMinuteLower;
  }

  /**
   * Gets the gps loc long degrees.
   *
   * @return the gps loc long degrees
   */
  public byte getGpsLocLongDegrees() {
    return this.gpsLocLongDegrees;
  }

  /**
   * Gets the gps loc long minute upper.
   *
   * @return the gps loc long minute upper
   */
  public byte getGpsLocLongMinuteUpper() {
    return this.gpsLocLongMinuteUpper;
  }

  /**
   * Gets the gps loc long minute lower.
   *
   * @return the gps loc long minute lower
   */
  public byte getGpsLocLongMinuteLower() {
    return this.gpsLocLongMinuteLower;
  }

  /**
   * Gets the spare1.
   *
   * @return the spare1
   */
  public int getSpare1() {
    return this.spare1;
  }

  /**
   * Gets the status info1 disk alarm.
   *
   * @return the status info1 disk alarm
   */
  public byte getStatusInfo1DiskAlarm() {
    return this.statusInfo1DiskAlarm;
  }

  /**
   * Gets the status info1 temp alarm.
   *
   * @return the status info1 temp alarm
   */
  public byte getStatusInfo1TempAlarm() {
    return this.statusInfo1TempAlarm;
  }

  /**
   * Gets the status info1 clock alarm.
   *
   * @return the status info1 clock alarm
   */
  public byte getStatusInfo1ClockAlarm() {
    return this.statusInfo1ClockAlarm;
  }

  /**
   * Gets the status info1 spare.
   *
   * @return the status info1 spare
   */
  public byte getStatusInfo1Spare() {
    return this.statusInfo1Spare;
  }

  /**
   * Gets the status info2 ntp alarm.
   *
   * @return the status info2 ntp alarm
   */
  public byte getStatusInfo2NtpAlarm() {
    return this.statusInfo2NtpAlarm;
  }

  /**
   * Gets the status info2 gps alarm.
   *
   * @return the status info2 gps alarm
   */
  public byte getStatusInfo2GpsAlarm() {
    return this.statusInfo2GpsAlarm;
  }

  /**
   * Gets the status info2 router alarm.
   *
   * @return the status info2 router alarm
   */
  public byte getStatusInfo2RouterAlarm() {
    return this.statusInfo2RouterAlarm;
  }

  /**
   * Gets the status info2 spare.
   *
   * @return the status info2 spare
   */
  public byte getStatusInfo2Spare() {
    return this.statusInfo2Spare;
  }

  /**
   * Gets the status info33 g c4 alarm.
   *
   * @return the status info33 g c4 alarm
   */
  public byte getStatusInfo33GC4Alarm() {
    return this.statusInfo33GC4Alarm;
  }

  /**
   * Gets the status info33 g c3 alarm.
   *
   * @return the status info33 g c3 alarm
   */
  public byte getStatusInfo33GC3Alarm() {
    return this.statusInfo33GC3Alarm;
  }

  /**
   * Gets the status info33 g c2 alarm.
   *
   * @return the status info33 g c2 alarm
   */
  public byte getStatusInfo33GC2Alarm() {
    return this.statusInfo33GC2Alarm;
  }

  /**
   * Gets the status info33 g c1 alarm.
   *
   * @return the status info33 g c1 alarm
   */
  public byte getStatusInfo33GC1Alarm() {
    return this.statusInfo33GC1Alarm;
  }

  /**
   * Gets the status info3 wifi card alarm.
   *
   * @return the status info3 wifi card alarm
   */
  public byte getStatusInfo3WifiCardAlarm() {
    return this.statusInfo3WifiCardAlarm;
  }

  /**
   * Gets the status info3 mlan alarm.
   *
   * @return the status info3 mlan alarm
   */
  public byte getStatusInfo3MLANAlarm() {
    return this.statusInfo3MLANAlarm;
  }

  /**
   * Gets the status info3 spare.
   *
   * @return the status info3 spare
   */
  public byte getStatusInfo3Spare() {
    return this.statusInfo3Spare;
  }

  /**
   * Gets the status info43 g c4 connecting.
   *
   * @return the status info43 g c4 connecting
   */
  public byte getStatusInfo43GC4Connecting() {
    return this.statusInfo43GC4Connecting;
  }

  /**
   * Gets the status info43 g c3 connecting.
   *
   * @return the status info43 g c3 connecting
   */
  public byte getStatusInfo43GC3Connecting() {
    return this.statusInfo43GC3Connecting;
  }

  /**
   * Gets the status info43 g c2 connecting.
   *
   * @return the status info43 g c2 connecting
   */
  public byte getStatusInfo43GC2Connecting() {
    return this.statusInfo43GC2Connecting;
  }

  /**
   * Gets the status info43 g c1 connecting.
   *
   * @return the status info43 g c1 connecting
   */
  public byte getStatusInfo43GC1Connecting() {
    return this.statusInfo43GC1Connecting;
  }

  /**
   * Gets the status info4 w card connecting.
   *
   * @return the status info4 w card connecting
   */
  public byte getStatusInfo4WCardConnecting() {
    return this.statusInfo4WCardConnecting;
  }

  /**
   * Gets the status info4 mlan mode on.
   *
   * @return the status info4 mlan mode on
   */
  public byte getStatusInfo4MLANModeOn() {
    return this.statusInfo4MLANModeOn;
  }

  /**
   * Gets the status info4 spare.
   *
   * @return the status info4 spare
   */
  public byte getStatusInfo4Spare() {
    return this.statusInfo4Spare;
  }

  /**
   * Gets the pis realtime mess up code.
   *
   * @return the pis realtime mess up code
   */
  public byte getPisRealtimeMessUpCode() {
    return this.pisRealtimeMessUpCode;
  }

  /**
   * Gets the pis realtime mess low code.
   *
   * @return the pis realtime mess low code
   */
  public byte getPisRealtimeMessLowCode() {
    return this.pisRealtimeMessLowCode;
  }

  /**
   * Gets the pre warm up signal1.
   *
   * @return the pre warm up signal1
   */
  public byte getPreWarmUpSignal1() {
    return this.preWarmUpSignal1;
  }

  /**
   * Gets the pre warm up signal2.
   *
   * @return the pre warm up signal2
   */
  public byte getPreWarmUpSignal2() {
    return this.preWarmUpSignal2;
  }

  /**
   * Gets the pre warm up signal3.
   *
   * @return the pre warm up signal3
   */
  public byte getPreWarmUpSignal3() {
    return this.preWarmUpSignal3;
  }

  /**
   * Gets the pre warm up signal4.
   *
   * @return the pre warm up signal4
   */
  public byte getPreWarmUpSignal4() {
    return this.preWarmUpSignal4;
  }

  /**
   * Gets the pre warm up signal spare.
   *
   * @return the pre warm up signal spare
   */
  public byte getPreWarmUpSignalSpare() {
    return this.preWarmUpSignalSpare;
  }

  /**
   * Gets the shore data info timetable.
   *
   * @return the shore data info timetable
   */
  public byte getShoreDataInfoTimetable() {
    return this.shoreDataInfoTimetable;
  }

  /**
   * Gets the shore data info seat.
   *
   * @return the shore data info seat
   */
  public byte getShoreDataInfoSeat() {
    return this.shoreDataInfoSeat;
  }

  /**
   * Gets the shore data info pis.
   *
   * @return the shore data info pis
   */
  public byte getShoreDataInfoPis() {
    return this.shoreDataInfoPis;
  }

  /**
   * Gets the shore data info tms.
   *
   * @return the shore data info tms
   */
  public byte getShoreDataInfoTms() {
    return this.shoreDataInfoTms;
  }

  /**
   * Gets the shore data info traction.
   *
   * @return the shore data info traction
   */
  public byte getShoreDataInfoTraction() {
    return this.shoreDataInfoTraction;
  }

  /**
   * Gets the shore data info aps.
   *
   * @return the shore data info aps
   */
  public byte getShoreDataInfoAps() {
    return this.shoreDataInfoAps;
  }

  /**
   * Gets the shore data info spare1.
   *
   * @return the shore data info spare1
   */
  public byte getShoreDataInfoSpare1() {
    return this.shoreDataInfoSpare1;
  }

  /**
   * Gets the shore data info spare2.
   *
   * @return the shore data info spare2
   */
  public byte getShoreDataInfoSpare2() {
    return this.shoreDataInfoSpare2;
  }

  /**
   * Gets the response to ds contents.
   *
   * @return the response to ds contents
   */
  public byte getResponseToDsContents() {
    return this.responseToDsContents;
  }

  /**
   * Gets the spare2.
   *
   * @return the spare2
   */
  public byte getSpare2() {
    return this.spare2;
  }

  /**
   * Gets the td file creation flag.
   *
   * @return the td file creation flag
   */
  public byte getTdFileCreationFlag() {
    return this.tdFileCreationFlag;
  }

  /**
   * Gets the ec data creation flag.
   *
   * @return the ec data creation flag
   */
  public byte getEcDataCreationFlag() {
    return this.ecDataCreationFlag;
  }

  /**
   * Gets the journey file creation flag.
   *
   * @return the journey file creation flag
   */
  public byte getJourneyFileCreationFlag() {
    return this.journeyFileCreationFlag;
  }

  /**
   * Gets the pirs flag.
   *
   * @return the pirs flag
   */
  public byte getPirsFlag() {
    return this.pirsFlag;
  }

  /**
   * Sets the header.
   *
   * @param header
   *          the new header
   */
  public void setHeader(Header header) {
    this.header = header;
  }

  /**
   * Gets the das manifest flag.
   *
   * @return the das manifest flag
   */
  public byte getDasManifestFlag() {
    return dasManifestFlag;
  }

  /**
   * Gets the das log flag.
   *
   * @return the das log flag
   */
  public byte getDasLogFlag() {
    return dasLogFlag;
  }

  /**
   * Gets the cctv return code.
   *
   * @return the cctv return code
   */
  public byte getCctvReturnCode() {
    return cctvReturnCode;
  }

  /**
   * Gets the cctv data ready.
   *
   * @return the cctv data ready
   */
  public byte getCctvDataReady() {
    return cctvDataReady;
  }

  /**
   * Gets the spare3.
   *
   * @return the spare3
   */
  public byte[] getSpare3() {
    return this.spare3;
  }

  /**
   * Gets the file name.
   *
   * @return the file name
   */
  public String getFileName() {
    return this.fileName;
  }

  /**
   * Gets the directory.
   *
   * @return the directory
   */
  public String getDirectory() {
    return this.directory;
  }
}
