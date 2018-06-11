/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.descriptor;

import com.zuan.parser.codehaus.preon.CodecDescriptor;
import com.zuan.parser.codehaus.preon.CodecDescriptor.Adjective;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.binding.Binding;
import com.zuan.parser.codehaus.preon.buffer.ByteOrder;
import com.zuan.parser.codehaus.preon.el.Expression;
import com.zuan.parser.codehaus.preon.rendering.IdentifierRewriter;
import com.zuan.parser.codehaus.preon.util.DocumentParaContents;
import com.zuan.parser.codehaus.preon.util.TextUtils;

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * The Class Documenters.
 *
 * @author zuan_
 */
public class Documenters {

  /** The Constant HEX_SYMBOLS. */
  private static final char[] HEX_SYMBOLS = "0123456789abcdef".toCharArray();

  /**
   * Instantiates a new documenters.
   */
  private Documenters() {
    super();
  }

  /**
   * For expression.
   *
   * @param expr
   *          the expr
   * @return the documenter
   */
  public static Documenter<ParaContents< ? >> forExpression(
      final Expression< ? , Resolver> expr) {
    return target -> {
      if (expr == null) {
        target.text("(unknown)");
      } else {
        if (expr.isParameterized()) {
          Expression< ? , Resolver> simplified = expr.simplify();
          simplified.document(new DocumentParaContents(target));
        } else {
          target.text(expr.eval(null).toString());
        }
      }
    };
  }

  /**
   * For numeric value.
   *
   * @param nrBits
   *          the nr bits
   * @param byteOrder
   *          the byte order
   * @return the documenter
   */
  public static Documenter<ParaContents< ? >> forNumericValue(final int nrBits,
      final ByteOrder byteOrder) {
    return target -> {
      target.text(nrBits + "bits numeric value");
      if (nrBits > 8) {
        target.text(" (");
        switch (byteOrder) {
        case BIG_ENDIAN:
          target.text("big endian");
          break;
        case LITTLE_ENDIAN:
          target.text("little endian");
          break;
        }
        target.text(")");
      }
    };
  }

  /**
   * For byte order.
   *
   * @param byteOrder
   *          the byte order
   * @return the documenter
   */
  public static Documenter<ParaContents< ? >> forByteOrder(final ByteOrder byteOrder) {
    return target -> target.text(byteOrder.asText());
  }

  /**
   * For bits.
   *
   * @param expr
   *          the expr
   * @return the documenter
   */
  public static Documenter<ParaContents< ? >> forBits(
      final Expression<Integer, Resolver> expr) {
    if (expr == null) {
      return target -> target.text("(unknown)");
    } else {
      if (expr.isParameterized()) {
        return forExpression(expr);
      } else {
        return target -> {
          int nrBits = expr.eval(null);
          target.text(TextUtils.bitsToText(nrBits));
        };
      }
    }
  }

  /**
   * For binding name.
   *
   * @param binding
   *          the binding
   * @param rewriter
   *          the rewriter
   * @return the documenter
   */
  public static Documenter<ParaContents< ? >> forBindingName(final Binding binding,
      final IdentifierRewriter rewriter) {
    return target -> target.term(binding.getId(), rewriter.rewrite(binding.getName()));
  }

  /**
   * For binding description.
   *
   * @param binding
   *          the binding
   * @return the documenter
   */
  public static Documenter<SimpleContents< ? >> forBindingDescription(final Binding binding) {
    return target -> binding.describe(target);
  }

  /**
   * For hex sequence.
   *
   * @param sequence
   *          the sequence
   * @return the documenter
   */
  public static Documenter<ParaContents< ? >> forHexSequence(final byte[] sequence) {
    return target -> {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < sequence.length; i++) {
        if (i != 0) {
          builder.append(' ');
        }
        builder.append(HEX_SYMBOLS[((sequence[i] >> 4) & 0xf)]);
        builder.append(HEX_SYMBOLS[(sequence[i] & 0x0f)]);
      }
      target.text(builder.toString());
    };
  }

  /**
   * For descriptor.
   *
   * @param descriptor
   *          the descriptor
   * @return the documenter
   */
  public static Documenter<ParaContents< ? >> forDescriptor(final CodecDescriptor descriptor) {
    return target -> descriptor.reference(Adjective.THE, false);
  }

}
