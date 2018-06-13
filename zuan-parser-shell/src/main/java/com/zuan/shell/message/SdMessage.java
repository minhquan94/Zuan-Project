/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell.message;

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
	private HeaderInformationMess headerInformationMess;

	/** The sd record. */
	@BoundList(size = "headerInformationMess.length")
	private byte[] sdRecord;

	/**
	 * Gets the header information mess.
	 *
	 * @return the header information mess
	 */
	public HeaderInformationMess getHeaderInformationMess() {
		return headerInformationMess;
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
	 * @param allBytes
	 *            the new sd record
	 */
	public void setSdRecord(byte[] allBytes) {
		sdRecord = allBytes;
	}

	/**
	 * Sets the header information mess.
	 *
	 * @param headerInformationMess
	 *            the headerInformationMess to set
	 */
	public void setHeaderInformationMess(HeaderInformationMess headerInformationMess) {
		this.headerInformationMess = headerInformationMess;
	}
}
