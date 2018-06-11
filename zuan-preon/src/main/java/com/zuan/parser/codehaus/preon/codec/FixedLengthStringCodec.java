/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.annotation.BoundString;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.descriptor.Documenters;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A {@link org.codehaus.preon.Codec} decoding Strings based on a fixed number
 * of <em>bytes</em>. (Note that it says <i>bytes</i>, not <i>characters</i>.)
 */
public class FixedLengthStringCodec implements Codec<String> {

  /** The encoding. */
  private final Charset encoding;

  /** The encoder. */
  private final CharsetEncoder encoder;

  /** The size expr. */
  private final Expression<Integer, Resolver> sizeExpr;

  /** The match. */
  private final String match;

  /** The byte converter. */
  private final BoundString.ByteConverter byteConverter;

  /**
   * Instantiates a new fixed length string codec.
   *
   * @param encoding
   *          the encoding
   * @param sizeExpr
   *          the size expr
   * @param match
   *          the match
   * @param byteConverter
   *          the byte converter
   */
  public FixedLengthStringCodec(Charset encoding, Expression<Integer, Resolver> sizeExpr,
      String match, BoundString.ByteConverter byteConverter) {
    this.encoding = encoding;
    this.sizeExpr = sizeExpr;
    this.match = match;
    this.byteConverter = byteConverter;
    this.encoder = encoding.newEncoder();
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
     * This takes a slice of the BitBuffer as a ByteBuffer, and feeds it into
     * encoding.decode.
     */
    int size = sizeExpr.eval(resolver);
    ByteBuffer bytebuffer = ByteBuffer.allocate(size);
    byte readbyte;
    for (int i = 0; i < size; i++) {
      readbyte = byteConverter.convert(buffer.readAsByte(8));
      bytebuffer.put(readbyte);
    }
    bytebuffer.rewind();
    String result;
    result = encoding.decode(bytebuffer).toString();
    result = result.trim(); // remove padding characters
    if (match.length() > 0) {
      if (!match.equals(result)) {
        throw new DecodingException(new IllegalStateException(
            "Expected \"" + match + "\", but got \"" + result + "\"."));
      }
    }
    return result;
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
    int size = sizeExpr.eval(resolver);
    ByteBuffer bytebuffer = ByteBuffer.allocate(size);
    encoder.encode(CharBuffer.wrap(value), bytebuffer, true);

    if (bytebuffer.position() < size) { // pad with 0's
      bytebuffer.put(new byte[size - bytebuffer.position()]);
    }
    bytebuffer.flip(); // switch to reading

    byte[] bytes = new byte[size];
    bytebuffer.get(bytes);
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = byteConverter.revert(bytes[i]);
    }
    // assert (size <= bytes.length); //No longer needed
    channel.write(bytes, 0, size);
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
    return Expressions.multiply(Expressions.createInteger(8, Resolver.class), sizeExpr);
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
          target.para().text("The number of characters of the string is ")
              .document(Documenters.forExpression(sizeExpr)).text(".").end();
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
        return target -> target.text("A sequence of characters, encoded in " + encoding + ".");
      }

    };
  }
}
