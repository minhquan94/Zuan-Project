/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.reflect.AnnotatedElement;
import java.nio.charset.Charset;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecConstructionException;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.Codecs;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.BoundString;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;

/**
 * A {@link CodecFactory} generating {@link Codecs} capable of generating String
 * from {@link BitBuffer} content.
 */
public class StringCodecFactory implements CodecFactory {

  /**
   * Creates the.
   *
   * @param <T>
   *          the generic type
   * @param metadata
   *          the metadata
   * @param type
   *          the type
   * @param context
   *          the context
   * @return the codec
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    if (metadata == null) {
      return null;
    }
    BoundString settings = metadata.getAnnotation(BoundString.class);
    if (String.class.equals(type) && settings != null) {
      try {
        Charset charset; // Encodings are now given as strings, and turned into
                         // Charsets
        charset = Charset.availableCharsets().get(settings.encoding());
        // This throws a NullPointerException if the Charset can't be found
        if (settings.size().length() > 0) {
          Expression<Integer, Resolver> expr;
          expr = Expressions.createInteger(context, settings.size());
          return (Codec<T>) new FixedLengthStringCodec(charset, // Note that
                                                                // this is a
                                                                // Charset, not
                                                                // an
              // Encoding
              expr, settings.match(), settings.converter().newInstance());
        } else {
          return (Codec<T>) new NullTerminatedStringCodec(charset, // Note that
                                                                   // this is a
                                                                   // Charset,
                                                                   // not an
              // Encoding
              settings.match(), settings.converter().newInstance());
        }
      } catch (InstantiationException e) {
        throw new CodecConstructionException(e.getMessage());
      } catch (IllegalAccessException e) {
        throw new CodecConstructionException(e.getMessage());
      } catch (NullPointerException e) {
        throw new CodecConstructionException("Unsupported encoding: " + e.getMessage());
      }
    } else {
      return null;
    }
  }

}
