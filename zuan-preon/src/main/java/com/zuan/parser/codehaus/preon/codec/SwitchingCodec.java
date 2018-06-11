/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.CodecSelector;
import com.zuan.parser.codehaus.preon.DecodingException;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;
import com.zuan.parser.codehaus.preon.channel.BitChannel;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.el.Expressions;
import com.zuan.parser.codehaus.preon.el.util.ClassUtils;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.Para;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A {@link Codec} that is able to dynamically choose between different types of
 * objects to decode, based on a couple of leading bits.
 *
 * @see CodecSelector
 */
public class SwitchingCodec implements Codec<Object> {

  /** The object responsible for picking the right {@link Codec}. */
  private CodecSelector selector;

  /**
   * Constructs a new instance.
   *
   * @param selector
   *          The object responsible for picking the right {@link Codec}.
   */
  public SwitchingCodec(CodecSelector selector) {
    this.selector = selector;
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Codec#decode(org.codehaus.preon.buffer.BitBuffer,
   * org.codehaus.preon.Resolver, org.codehaus.preon.Builder)
   */

  /**
   * Decode.
   *
   * @param buffer
   *          the buffer
   * @param resolver
   *          the resolver
   * @param builder
   *          the builder
   * @return the object
   * @throws DecodingException
   *           the decoding exception
   */
  @Override
  public Object decode(BitBuffer buffer, Resolver resolver, Builder builder)
      throws DecodingException {
    Codec< ? > codec = selector.select(buffer, resolver);
    return codec.decode(buffer, resolver, builder);
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
  @SuppressWarnings({"unchecked", "rawtypes" })
  @Override
  public void encode(Object value, BitChannel channel, Resolver resolver) throws IOException {
    Codec codec = selector.select(value.getClass(), channel, resolver);
    codec.encode(value, channel, resolver);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Codec#getTypes()
   */

  /**
   * Gets the types.
   *
   * @return the types
   */
  @Override
  public Class< ? >[] getTypes() {
    Set<Class< ? >> types = new HashSet<>();
    for (Codec< ? > codec : selector.getChoices()) {
      types.addAll(Arrays.asList(codec.getTypes()));
    }
    return new ArrayList<>(types).toArray(new Class[0]);
  }

  /*
   * (non-Javadoc)
   * @see org.codehaus.preon.Codec#getSize()
   */

  /**
   * Gets the size.
   *
   * @return the size
   */
  @Override
  public Expression<Integer, Resolver> getSize() {
    Collection<Codec< ? >> choices = selector.getChoices();
    if (choices.size() == 0) {
      return null;
    } else if (choices.size() == 1) {
      return choices.iterator().next().getSize();
    } else {
      Integer size = null;
      Expression<Integer, Resolver> sizeExpr = null;
      for (Codec< ? > codec : choices) {
        sizeExpr = codec.getSize();
        if (sizeExpr == null) {
          return null;
        } else if (!sizeExpr.isParameterized()) {
          if (size == null) {
            size = sizeExpr.eval(null);
          } else {
            if (!size.equals(sizeExpr.eval(null))) {
              return null;
            }
          }
        }
      }
      if (size != null) {
        return Expressions.add(Expressions.createInteger(size, Resolver.class),
            selector.getSize());
      } else {
        return null;
      }
    }
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public Class< ? > getType() {
    Set<Class< ? >> types = new HashSet<>();
    for (Codec< ? > codec : selector.getChoices()) {
      types.add(codec.getType());
    }
    Class< ? >[] result = new Class< ? >[0];
    result = new ArrayList<>(types).toArray(result);
    return ClassUtils.calculateCommonSuperType(result);
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
          Para< ? > para = target.para();
          selector.document(para);
          para.end();
        };
      }

      @Override
      public String getTitle() {
        return null;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> reference(final Adjective adjective,
          boolean startWithCapital) {
        return target -> {
          if (selector.getChoices().size() <= 3) {
            target.text(adjective.asTextPreferA(false)).text("either ");
            List<Codec< ? >> codecs =
                Arrays.asList(selector.getChoices().toArray(new Codec< ? >[0]));
            for (int i = 0; i < codecs.size(); i++) {
              target.document(
                  codecs.get(i).getCodecDescriptor().reference(Adjective.NONE, false));
              if (i > codecs.size() - 2) {
                // Do nothing
              } else if (i == codecs.size() - 2) {
                target.text(" or ");
              } else if (i < codecs.size() - 2) {
                target.text(", ");
              }
            }
            target.text(" elements");
          } else {
            target.text(adjective.asTextPreferA(false)).text("list of elements");
          }
        };
      }

      @Override
      public boolean requiresDedicatedSection() {
        return false;
      }

      @Override
      public <C extends ParaContents< ? >> Documenter<C> summary() {
        return target -> {
          target.text("A list of ");
          List<Codec< ? >> codecs =
              Arrays.asList(selector.getChoices().toArray(new Codec< ? >[0]));
          for (int i = 0; i < codecs.size(); i++) {
            target
                .document(codecs.get(i).getCodecDescriptor().reference(Adjective.NONE, false));
            if (i > codecs.size() - 2) {
              // Do nothing
            } else if (i == codecs.size() - 2) {
              target.text(" or ");
            } else if (i < codecs.size() - 2) {
              target.text(", ");
            }
          }
          target.text(" elements.");
        };
      }

    };
  }

}
