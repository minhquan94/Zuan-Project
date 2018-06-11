/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;

/**
 * The Class SimpleBannerProvider.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class SimpleBannerProvider implements PromptProvider {

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.shell.jline.PromptProvider#getPrompt()
   */
  @Override
  public AttributedString getPrompt() {
    return new AttributedString("Shell > ",
        AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
  }
}
