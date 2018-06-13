/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.zuan.shell.common.WidthTable;

/**
 * The Class TrainProjectConverter.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
// @Component
public class WidthTableConverter implements Converter<String, WidthTable> {

  /** The pattern. */
  private final Pattern pattern = Pattern.compile("([^\\s]+)");

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
   */
  @Override
  public WidthTable convert(String source) {
    final Matcher matcher = this.pattern.matcher(source);
    if (matcher.find() && StringUtils.isNotBlank(matcher.group())) {
      return WidthTable.valueOf(matcher.group());
    }
    return null;
  }

}
