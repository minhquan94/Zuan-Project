/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.annotation.BoundString;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.el.Expression;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A {@link org.codehaus.preon.Codec} that reads null-terminated Strings.
 * Basically, it will read bytes until it encounters a '\0' character, in which
 * case it considers itself to be done, and construct a String from the bytes
 * read.
 */
public class NullTerminatedStringCodec implements Codec<String> {

  /** The buffer size. */
  private static int BUFFER_SIZE = 32; // 32 Bytes is probably overkill, but
                                       // these days it hardly matters

  /** The encoding. */
  private Charset encoding;

  /** The match. */
  private String match;

  /** The byte converter. */
  private BoundString.ByteConverter byteConverter;

  /**
   * Instantiates a new null terminated string codec.
   *
   * @param encoding
   *          the encoding
   * @param match
   *          the match
   * @param byteConverter
   *          the byte converter
   */
  public NullTerminatedStringCodec(Charset encoding, String match,
      BoundString.ByteConverter byteConverter) {
    this.encoding = encoding;
    this.match = match;
    this.byteConverter = byteConverter;
  }

  /**
   * Decode.
   *
   * @param buffer
   *          the buffer
   * @param resolver
   *          the resolver
   * @param builder
   *          the builder
   * @return the string
   * @throws DecodingException
   *           the decoding exception
   */
  @Override
  public String decode(BitBuffer buffer, Resolver resolver, Builder builder)
      throws DecodingException {
    /*
     * This has been gutted, and now uses Charsets to do decoding. It opens the
     * bitbuffer as a bytebuffer (taking care to note and preserve positions),
     * creates a CharBuffer with space for one character, and decodes the
     * ByteBuffer one character at a time. If the character decoded is NULL, it
     * finishes up (it has to use the decoded character, not the byte, as
     * multibyte encodings can include null bytes in non-null characters). I
     * used a StringWriter for the string, as it's more memory efficient, for
     * what it's worth. I wasn't able to find a way to include the byteConverter
     * in the decoding process. I'm guessing the main use for byteConverter was
     * encoding conversion anyway, but if it's needed, it might be possible to
     * subclass ByteBuffer.
     */
    CharsetDecoder decoder = encoding.newDecoder();
    ByteBuffer bytebuffer = ByteBuffer.allocate(BUFFER_SIZE); // Allocate a
                                                              // bytebuffer.
                                                              // We'll need this
                                                              // for
    // multibyte encodings
    CharBuffer charbuffer = CharBuffer.allocate(1); // Decode one character at a
                                                    // time
    StringWriter sw = new StringWriter(); // This will eventually hold our
                                          // string
    byte bytevalue;
    char charvalue;
    boolean readOK = true;
    do {
      bytevalue = byteConverter.convert(buffer.readAsByte(8)); // Convert our
                                                               // byte
      bytebuffer.put(bytevalue); // and add it to the bytebuffer
      bytebuffer.flip(); // Flip the buffer, so we can read it
      decoder.decode(bytebuffer, charbuffer, false); // Decode up to one char
                                                     // from bytebuffer
      if (charbuffer.position() == 1) {
        charbuffer.rewind();
        charvalue = charbuffer.get();
        charbuffer.rewind();
        if (charvalue == 0) { // If character is null, we're finished
          readOK = false;
        } else {
          sw.append(charvalue); // Write character to StringWriter
        }
      }
      bytebuffer.compact(); // Compact the buffer, so we can write to it
    } while (readOK);
    return sw.toString();
  }

  /**
   * Encode.
   *
   * @param value
   *          the value
   * @param channel
   *          the channel
   * @param resolver
   *          the resolver
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Override
  public void encode(String value, BitChannel channel, Resolver resolver) throws IOException {
    /*
     * This is a crude first attempt
     */
    ByteBuffer bytebuffer = encoding.encode(value + "\u0000");
    byte[] bytes = bytebuffer.array();
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = byteConverter.revert(bytes[i]);
    }
    channel.write(bytes, 0, bytes.length);
  }

  /**
   * Gets the types.
   *
   * @return the types
   */
  @Override
  public Class< ? >[] getTypes() {
    return new Class[]{String.class };
  }

  /**
   * Gets the size.
   *
   * @return the size
   */
  @Override
  public Expression<Integer, Resolver> getSize() {
    return null;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public Class< ? > getType() {
    return String.class;
  }

  /**
   * Gets the codec descriptor.
   *
   * @return the codec descriptor
   */
  @Override
  public CodecDescriptor getCodecDescriptor() {
    return new CodecDescriptor() {

      @Override
      public <C extends SimpleContents< ? >> Documenter<C> details(String bufferReference) {
        return target -> {
          if (match != null && match.length() > 0) {
            target.para().text("The string is expected to match \"").text(match).text("\".")
                .end();
          }
        };
      }

      @Override
      public String getTitle() {
        return null;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(final Adjective adjective,
          boolean startWithCapital) {
        return target -> target.text(adjective.asTextPreferA(false))
            .text("string of characters");
      }

      @Override
      public boolean requiresDedicatedSection() {
        return false;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> summary() {
        return target -> target
            .text("A null-terminated sequence of characters, encoded in " + encoding + ".");
      }

    };
  }
}
