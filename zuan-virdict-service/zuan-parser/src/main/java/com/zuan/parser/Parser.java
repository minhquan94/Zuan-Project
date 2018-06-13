/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser;

import java.util.Map;
import java.util.Set;

/**
 * The Interface Parser.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public interface Parser {

  /**
   * Parses the entire data to set.
   *
   * @param packet
   *          the packet
   * @param configuration
   *          the configuration
   * @return the sets the
   */
  <T> Set<T> parseEntireDataToSet(final byte[] packet,
      final ParserConfiguration configuration);

  /**
   * Parses the entire data to map.
   *
   * @param <K>
   *          the key type
   * @param <V>
   *          the value type
   * @param packet
   *          the packet
   * @param configuration
   *          the configuration
   * @return the map
   */
  public <K, V> Map<K, V> parseEntireDataToMap(final byte[] packet,
      final ParserConfiguration configuration);
}
