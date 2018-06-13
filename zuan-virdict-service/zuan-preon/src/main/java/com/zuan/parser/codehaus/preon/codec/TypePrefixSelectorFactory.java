/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecConstructionException;
import com.zuan.parser.codehaus.preon.CodecDescriptor.Adjective;
import com.zuan.parser.codehaus.preon.CodecSelector;
import com.zuan.parser.codehaus.preon.CodecSelectorFactory;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.TypePrefix;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.el.Document;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;

import nl.flotsam.pecia.ParaContents;

/**
 * A {@link CodecSelectorFactory} that will create a {@link CodecSelector} that
 * will look for leading bits, matching a certain value expressed with an
 * {@link TypePrefix} annotation on the Codecs classes.
 */
public class TypePrefixSelectorFactory implements CodecSelectorFactory {

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.CodecSelectorFactory#create(org.codehaus.preon.
   * ResolverContext , java.util.List)
   */
  @Override
  public CodecSelector create(ResolverContext context, List<Codec< ? >> allCodecs) {
    int size = -1;
    List<Expression<Integer, Resolver>> expressions = new ArrayList<>();
    List<Codec< ? >> codecs = new ArrayList<>();
    ByteOrder byteOrder = null;
    for (Codec< ? > codec : allCodecs) {
      for (Class< ? > valueType : codec.getTypes()) {
        TypePrefix prefix = valueType.getAnnotation(TypePrefix.class);
        if (prefix == null) {
          throw new CodecConstructionException("To little context to decide between codecs.");
        } else {
          if (byteOrder == null) {
            byteOrder = prefix.byteOrder();
          } else {
            if (byteOrder != prefix.byteOrder()) {
              throw new CodecConstructionException(
                  "Two distinct types of byte orders are not supported: " + "expected "
                      + byteOrder.asText() + ", got " + prefix.byteOrder().asText() + " for "
                      + codec);
            }
          }
          if (size != -1) {
            if (size != prefix.size()) {
              throw new CodecConstructionException(
                  "Two distinct prefix sizes are not supported: " + "expected " + size
                      + ", got " + prefix.size() + " for " + codec);
            } else {
              size = prefix.size();
            }
          }
          if (size == -1) {
            size = prefix.size();
          }
          Expression<Integer, Resolver> value =
              Expressions.createInteger(context, prefix.value());
          expressions.add(value);
          codecs.add(codec);
        }
      }
    }
    return new TypePrefixSelector(expressions, codecs, size, byteOrder);
  }

  /**
   * A {@link CodecSelector} that determines its choice on a couple of leading
   * bits. The correspondence between {@link Codec} and leading bits is based on
   * the {@link TypePrefix} annotation.
   */
  private static class TypePrefixSelector implements CodecSelector {

    /** The codecs. */
    private List<Codec< ? >> codecs;

    /** The unique codecs. */
    private Set<Codec< ? >> uniqueCodecs;

    /** The expressions. */
    private List<Expression<Integer, Resolver>> expressions;

    /** The byte order. */
    private final ByteOrder byteOrder;

    /** The size. */
    private int size;

    /**
     * Instantiates a new type prefix selector.
     *
     * @param expressions
     *          the expressions
     * @param codecs
     *          the codecs
     * @param size
     *          the size
     * @param byteOrder
     *          the byte order
     */
    public TypePrefixSelector(List<Expression<Integer, Resolver>> expressions,
        List<Codec< ? >> codecs, int size, ByteOrder byteOrder) {
      this.uniqueCodecs = new HashSet<>();
      this.codecs = codecs;
      this.expressions = expressions;
      this.size = size;
      this.byteOrder = byteOrder;
      this.uniqueCodecs.addAll(codecs);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.CodecSelector#getChoices()
     */
    @Override
    public Collection<Codec< ? >> getChoices() {
      return uniqueCodecs;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.CodecSelector#select(com.zuan.
     * parser.codehaus.preon.buffer.BitBuffer,
     * com.zuan.parser.codehaus.preon.Resolver)
     */
    @Override
    public Codec< ? > select(BitBuffer buffer, Resolver resolver) throws DecodingException {
      long index = buffer.readAsLong(size, byteOrder);
      for (int i = 0; i < codecs.size(); i++) {
        if (index == expressions.get(i).eval(resolver)) {
          return codecs.get(i);
        }
      }
      throw new DecodingException("No matching Codec found for value " + index);
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.CodecSelector#select(java.lang.Class,
     * com.zuan.parser.codehaus.preon.channel.BitChannel,
     * com.zuan.parser.codehaus.preon.Resolver)
     */
    @Override
    public <T> Codec< ? > select(Class<T> type, BitChannel channel, Resolver resolver)
        throws IOException {
      for (int i = 0; i < codecs.size(); i++) {
        Codec< ? > codec = codecs.get(i);
        if (type.isAssignableFrom(codec.getType())) {
          // So we found the Codec. Now to make sure that same Codec is picked
          // up again
          // while decoding:
          channel.write(size, expressions.get(i).eval(resolver), ByteOrder.BIG_ENDIAN);
          return codec;
        }
      }
      return null;
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.CodecSelector#document(nl.flotsam.
     * pecia.ParaContents)
     */
    @Override
    public void document(final ParaContents< ? > para) {
      para.text(" The particular choice is based on a " + size + "-bit ");
      para.text(" value preceeding the actual encoded value.");
      for (int i = 0; i < codecs.size(); i++) {
        Codec< ? > codec = codecs.get(i);
        Expression<Integer, Resolver> expression = expressions.get(i);
        para.text(" If ");
        expression.document(new Document() {

          @Override
          public Document detail(String text) {
            para.text(text);
            return this;
          }

          @Override
          public void link(Object object, String text) {
            para.text(text);
          }

          @Override
          public void text(String text) {
            para.text(text);
          }

        });
        para.text(", then ")
            .document(codec.getCodecDescriptor().reference(Adjective.THE, false));
        para.text(" will be choosen.");
      }
    }

    /*
     * (non-Javadoc)
     * @see com.zuan.parser.codehaus.preon.CodecSelector#getSize()
     */
    @Override
    public Expression<Integer, Resolver> getSize() {
      return Expressions.createInteger(size, Resolver.class);
    }

  }

}
