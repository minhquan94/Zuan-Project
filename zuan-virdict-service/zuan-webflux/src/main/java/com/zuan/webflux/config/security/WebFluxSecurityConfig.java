/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

/**
 * The Class WebSecurityConfig.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebFluxSecurityConfig {

  /** The Constant AUTH_WHITELIST. */
  private static final String[] AUTH_WHITELIST =
    {"/resources/**", "/webjars/**", "/auth/**", "/test/ws", "/favicon.ico" };

  /**
   * Securityg web filter chain.
   *
   * @param http
   *          the http
   * @param authenticationWebFilter
   *          the authentication web filter
   * @param entryPoint
   *          the entry point
   * @return the security web filter chain
   */
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http,
      final JwtAuthenticationWebFilter authenticationWebFilter,
      final UnauthorizedAuthenticationEntryPoint entryPoint) {
    // We must override AuthenticationEntryPoint because if
    // AuthenticationWebFilter didn't kicked in
    // (i.e. there are no required headers) then default behavior is to display
    // HttpBasicAuth,
    // so we just return unauthorized to override it.
    // Filter tries to authenticate each request if it contains required
    // headers.
    // Finally, we disable all default security.
    http.exceptionHandling().authenticationEntryPoint(entryPoint).and()
    .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
    .authorizeExchange().pathMatchers(AUTH_WHITELIST).permitAll().anyExchange()
    .authenticated().and().httpBasic().disable().formLogin().disable().csrf().disable()
    .logout().disable();
    return http.build();
  }

  /**
   * Security context repository.
   *
   * @return the web session server security context repository
   */
  @Bean
  public WebSessionServerSecurityContextRepository securityContextRepository() {
    return new WebSessionServerSecurityContextRepository();
  }

  /**
   * Encoder.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder encoder() {
    return new CustomPasswordEncoder();
  }

  /**
   * Jwt token util.
   *
   * @return the jwt token util
   */
  @Bean
  public JwtTokenUtil jwtTokenUtil() {
    return new JwtTokenUtil("test", 100000L);
  }
}
