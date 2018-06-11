/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.channel;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.zuan.parser.codehaus.preon.buffer.ByteOrder;

/**
 * A {@link BitChannel} wrapping around another {@link BitChannel}, preventing
 * writing more than a certain maximum number of bits. In case anything like
 * that happens, it will throw a {@link java.io.IOException}.
 */
public class BoundedBitChannel implements BitChannel {

	/** The channel. */
	private final BitChannel channel;

	/** The max bits. */
	private final long maxBits;

  /** The written. */
  private long written;

	/** The Constant OVERRUN_MESSAGE. */
	private static final String OVERRUN_MESSAGE = "Attempt to write beyond maximum number of bits allowed.";

	/**
	 * Constructs a new instance.
	 *
	 * @param channel
	 *            The {@link BitChannel} to wrap.
	 * @param maxBits
	 *            The maximum number of bits accepted.
	 */
	public BoundedBitChannel(@Nonnull BitChannel channel, @Nonnegative long maxBits) {
		assert channel != null;
		assert maxBits >= 0;
		this.channel = channel;
		this.maxBits = maxBits;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zuan.parser.codehaus.preon.channel.BitChannel#write(boolean)
	 */
	@Override
	public void write(boolean value) throws IOException {
		if (written + 1 <= maxBits) {
			channel.write(value);
			written += 1;
		} else {
			throw new IOException(OVERRUN_MESSAGE);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zuan.parser.codehaus.preon.channel.BitChannel#write(int,
	 * byte)
	 */
	@Override
	public void write(int nrbits, byte value) throws IOException {
		if (written + nrbits <= maxBits) {
			channel.write(nrbits, value);
			written += nrbits;
		} else {
			throw new IOException(OVERRUN_MESSAGE);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zuan.parser.codehaus.preon.channel.BitChannel#write(int,
	 * int, com.zuan.parser.codehaus.preon.buffer.ByteOrder)
	 */
	@Override
	public void write(int nrbits, int value, ByteOrder byteOrder) throws IOException {
		if (written + nrbits <= maxBits) {
			channel.write(nrbits, value, byteOrder);
			written += nrbits;
		} else {
			throw new IOException(OVERRUN_MESSAGE);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zuan.parser.codehaus.preon.channel.BitChannel#write(int,
	 * long, com.zuan.parser.codehaus.preon.buffer.ByteOrder)
	 */
	@Override
	public void write(int nrbits, long value, ByteOrder byteOrder) throws IOException {
		if (written + nrbits <= maxBits) {
			channel.write(nrbits, value, byteOrder);
			written += nrbits;
		} else {
			throw new IOException(OVERRUN_MESSAGE);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zuan.parser.codehaus.preon.channel.BitChannel#write(int,
	 * short, com.zuan.parser.codehaus.preon.buffer.ByteOrder)
	 */
	@Override
	public void write(int nrbits, short value, ByteOrder byteOrder) throws IOException {
		if (written + nrbits <= maxBits) {
			channel.write(nrbits, value, byteOrder);
			written += nrbits;
		} else {
			throw new IOException(OVERRUN_MESSAGE);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zuan.parser.codehaus.preon.channel.BitChannel#write(byte[],
	 * int, int)
	 */
	@Override
	public void write(byte[] src, int offset, int length) throws IOException {
		if (written + length <= maxBits) {
			channel.write(src, offset, length);
			written += length;
		} else {
			throw new IOException(OVERRUN_MESSAGE);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zuan.parser.codehaus.preon.channel.BitChannel#write(java.nio.
	 * ByteBuffer)
	 */
	@Override
	public long write(ByteBuffer buffer) throws IOException {
		long written = channel.write(buffer);
		if (written > maxBits - this.written) {
			throw new IOException(OVERRUN_MESSAGE);
		} else {
			this.written += written;
		}
		return written;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zuan.parser.codehaus.preon.channel.BitChannel#getRelativeBitPos()
	 */
	@Override
	public int getRelativeBitPos() {
		return channel.getRelativeBitPos();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zuan.parser.codehaus.preon.channel.BitChannel#close()
	 */
	@Override
	public void close() throws IOException {
		channel.close();
	}
}
