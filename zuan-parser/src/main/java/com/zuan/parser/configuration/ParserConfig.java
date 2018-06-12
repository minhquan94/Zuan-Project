/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class Configutaion.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Configuration
@EnableConfigurationProperties(value = {ParserConfigurationProperties.class })
public class ParserConfig {
}
