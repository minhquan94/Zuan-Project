/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.imp;

import java.util.ArrayList;
import java.util.List;

import com.zuan.parser.imp.exception.ParserException;

/**
 * The Class ParserConfigurationImpl.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class ParserConfigurationImpl implements ParserConfiguration {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The signals. */
  private final ArrayList<SignalConfiguration> signals;

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
   * @see com.ParserConfig.virdict.parser.ParserConfiguration#addSignal(com.gcs.virdict.parser.SignalConfiguration)
   */
  @Override
  public void addSignal(final SignalConfiguration signal) throws ParserException {
    signal.validateConfiguration();
    this.signals.add(signal);

  }

  /**
   * {@inheritDoc}
   * 
   * @see com.ParserConfig.virdict.parser.ParserConfiguration#getSignals()
   */
  @Override
  public List<SignalConfiguration> getSignals() {
    return this.signals;
  }
}
