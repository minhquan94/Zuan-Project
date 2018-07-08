/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;

/**
 * The Class WebConfiguration.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
/**
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Configuration
public class WebConfiguration implements WebFluxConfigurer {

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.web.reactive.config.WebFluxConfigurer#addResourceHandlers(org.springframework.web.reactive.config.ResourceHandlerRegistry)
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources",
        "classpath:/templates/");
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.web.reactive.config.WebFluxConfigurer#addCorsMappings(org.springframework.web.reactive.config.CorsRegistry)
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/auth/token");
  }

  /**
   * Free marker configurer.
   *
   * @return the free marker configurer
   */
  @Bean
  public FreeMarkerConfigurer freeMarkerConfigurer() {
    return new FreeMarkerConfigurer();
  }

  /**
   * Static resource router.
   *
   * @return the router function
   */
  @Bean
  RouterFunction<ServerResponse> staticResourceRouter() {
    return RouterFunctions.resources("/**", new ClassPathResource("templates/"));
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.web.reactive.config.WebFluxConfigurer#configureViewResolvers(org.springframework.web.reactive.config.ViewResolverRegistry)
   */
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.freeMarker();
  }
}
