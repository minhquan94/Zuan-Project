/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.codec;

import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Map;

import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.CodecFactory;
import com.zuan.parser.codehaus.preon.ResolverContext;
import com.zuan.parser.codehaus.preon.annotation.BoundList;
import com.zuan.parser.codehaus.preon.annotation.Choices;

/**
 * A factory for creating MapCodec objects.
 *
 * @author zuan_
 */
public class MapCodecFactory implements CodecFactory {

  /** The codec factory. */
  private final CodecFactory codecFactory;

  /**
   * Instantiates a new map codec factory.
   *
   * @param codecFactory
   *          the codec factory
   */
  public MapCodecFactory(CodecFactory codecFactory) {
    this.codecFactory = codecFactory;
  }

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
  @SuppressWarnings({"rawtypes", "unchecked" })
  @Override
  public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type,
      ResolverContext context) {
    if (Map.class.isAssignableFrom(type)) {
      BoundList boundList = metadata.getAnnotation(BoundList.class);
      if (boundList != null && typeIsGuaranteedToBeEntry(boundList)) {
        Codec<List> listCodec = codecFactory.create(metadata, List.class, context);
        if (listCodec != null) {
          return new ListBasedMapCodec(listCodec);
        } else {
          return null;
        }
      }
    }
    return null;
  }

  /**
   * Type is guaranteed to be entry.
   *
   * @param boundList
   *          the bound list
   * @return true, if successful
   */
  private boolean typeIsGuaranteedToBeEntry(BoundList boundList) {
    if (boundList.type() != null && Map.Entry.class.isAssignableFrom(boundList.type())) {
      return true;
    } else if (boundList.types() != null && boundList.types().length > 0) {
      boolean allGood = true;
      for (Class< ? > type : boundList.types()) {
        allGood &= (type != null && Map.Entry.class.isAssignableFrom(type));
      }
      return allGood;
    } else if (boundList.selectFrom() != null
        && boundList.selectFrom().alternatives().length > 0) {
      boolean allGood = true;
      for (Choices.Choice choice : boundList.selectFrom().alternatives()) {
        allGood &= (choice != null && Map.Entry.class.isAssignableFrom(choice.type()));
      }
      return allGood;
    }
    return false;
  }

}
