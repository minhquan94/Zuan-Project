/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser;

import java.io.Serializable;
import java.util.List;

import com.zuan.parser.imp.exception.ParserException;
import com.zuan.parser.signal.SignalInfomation;

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
  void addSignal(final SignalInfomation signal) throws ParserException;

  /**
   * Gets the signals.
   *
   * @return the signals
   */
  List<SignalInfomation> getSignals();
}
