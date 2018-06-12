package com.zuan.stock.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.eureka.ConditionalOnRibbonAndEurekaEnabled;
import org.springframework.cloud.netflix.ribbon.eureka.EurekaRibbonClientConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * The Class ServiceAutoConfiguration.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnRibbonAndEurekaEnabled
@AutoConfigureAfter({RibbonAutoConfiguration.class, StockServiceConfiguration.class })
@RibbonClients(value = {
    @RibbonClient(name = "db-service", configuration = EurekaRibbonClientConfiguration.class) }, defaultConfiguration = EurekaRibbonClientConfiguration.class)
public class ServiceAutoConfiguration {

}
