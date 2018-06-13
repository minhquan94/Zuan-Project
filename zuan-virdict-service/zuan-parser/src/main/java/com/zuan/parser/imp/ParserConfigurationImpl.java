/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.util.ArrayList;
import java.util.List;

import com.zuan.parser.ParserConfiguration;
import com.zuan.parser.imp.exception.ParserException;
import com.zuan.parser.signal.SignalInfomation;

/**
 * The Class ParserConfigurationImpl.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class ParserConfigurationImpl implements ParserConfiguration {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The signals. */
  private final ArrayList<SignalInfomation> signals;

  /**
   * Instantiates a new parser config.
   */
  public ParserConfigurationImpl() {
    this.signals = new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   * 
   * @throws ParserException
   * @see com.zuan.parser.ParserConfig.virdict.parser.ParserConfiguration#addSignal(com.zuan.parser.signal.SdSignalConfiguration.virdict.parser.SignalConfiguration)
   */
  @Override
  public void addSignal(final SignalInfomation signal) throws ParserException {
    signal.initialize();
    this.signals.add(signal);

  }

  /**
   * {@inheritDoc}
   * 
   * @see com.zuan.parser.ParserConfig.virdict.parser.ParserConfiguration#getSignals()
   */
  @Override
  public List<SignalInfomation> getSignals() {
    return this.signals;
  }
}
