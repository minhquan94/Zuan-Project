/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon;

import java.util.List;

/**
 * A factory for creating {@link CodecSelector CodecSelectors}.
 */
@FunctionalInterface
public interface CodecSelectorFactory {

  /**
   * Creates a new {@link CodecSelector}. The {@link CodecSelector} constructed
   * will <em>only</em> be able to resolve references constructed from the
   * {@link ResolverContext} passed in + the <code>BitBuffer</code>.
   *
   * @param context
   *          The context for creating {@link Reference} instances.
   * @param codecs
   *          The {@link Codec Codecs} from which the {@link CodecSelector}
   *          needs to select.
   * @return The {@link CodecSelector} capable of selecting the right
   *         {@link Codec}.
   */
  CodecSelector create(ResolverContext context, List<Codec< ? >> codecs)
      throws CodecConstructionException;

}
