/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.util.Set;

import com.zuan.parser.common.SdObject;

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
  Set<SdObject> parseEntireDataToSet(final byte[] packet,
      final ParserConfiguration configuration);
}
