/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.data.messages.sd;

import com.zuan.data.messages.Header;
import com.zuan.data.messages.MessageBase;
import com.zuan.parser.codehaus.preon.annotation.BoundList;
import com.zuan.parser.codehaus.preon.annotation.BoundObject;

/**
 * The Class SdMessage.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SdMessage implements MessageBase {

  /** The header information mess. */
  @BoundObject
  private Header header;

  /** The sd record. */
  @BoundList(size = "header.length")
  private byte[] sdRecord;

  /**
   * Gets the header.
   *
   * @return the header
   */
  public Header getHeader() {
    return header;
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
   * Gets the sd record.
   *
   * @return the sd record
   */
  public byte[] getSdRecord() {
    return sdRecord;
  }

  /**
   * Sets the sd record.
   *
   * @param sdRecord
   *          the new sd record
   */
  public void setSdRecord(byte[] sdRecord) {
    this.sdRecord = sdRecord;
  }
}
