/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.io.Serializable;
import java.util.List;

import com.zuan.parser.imp.exception.ParserException;

/**
 * The Interface ParserConfiguration.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public interface ParserConfiguration extends Serializable {

  /**
   * Adds the signal.
   *
   * @param signal
   *          the signal
   * @throws ParserException
   *           the parser exception
   */
  void addSignal(final SignalConfiguration signal) throws ParserException;

  /**
   * Gets the signals.
   *
   * @return the signals
   */
  List<SignalConfiguration> getSignals();
}
