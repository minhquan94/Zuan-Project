/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * The Class AdminServerApplication.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableAdminServer
public class CloudAdminApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(CloudAdminApplication.class, args);
  }

  /**
   * The Class SecuritySecureConfig.
   */
  @Configuration
  public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

    /** The admin context path. */
    private final String adminContextPath;

    /**
     * Instantiates a new security secure config.
     *
     * @param adminServerProperties
     *          the admin server properties
     */
    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
      this.adminContextPath = adminServerProperties.getContextPath();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      // @formatter:off
      final SavedRequestAwareAuthenticationSuccessHandler successHandler =
          new SavedRequestAwareAuthenticationSuccessHandler();
      successHandler.setTargetUrlParameter("redirectTo");

      http.authorizeRequests().antMatchers(adminContextPath + "/assets/**").permitAll()
      .antMatchers(adminContextPath + "/login").permitAll().anyRequest().authenticated()
      .and().formLogin().loginPage(adminContextPath + "/login")
      .successHandler(successHandler).and().logout()
      .logoutUrl(adminContextPath + "/logout").and().httpBasic().and().csrf().disable();
      // @formatter:on
    }
  }
}
