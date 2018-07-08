/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.handler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.zuan.webflux.config.security.jwt.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

/**
 * The Class AbstractWebSocketHandler.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
abstract class AbstractWebSocketHandler implements WebSocketHandler {

  /** The authorized roles. */
  protected ArrayList<String> authorizedRoles = new ArrayList<>();

  /**
   * Instantiates a new abstract web socket handler.
   */
  public AbstractWebSocketHandler() {
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.web.reactive.socket.WebSocketHandler#handle(org.springframework.web.reactive.socket.WebSocketSession)
   */
  @Override
  public Mono<Void> handle(WebSocketSession session) {
    return session.getHandshakeInfo().getPrincipal().filter(this::isAuthorized)
        .then(doHandle(session));
  }

  /**
   * Checks if is authorized.
   *
   * @param principal
   *          the principal
   * @return true, if is authorized
   */
  private boolean isAuthorized(Principal principal) {
    JwtAuthenticationToken jwtAuthenticationToken;
    if (principal != null && principal instanceof JwtAuthenticationToken) {
      jwtAuthenticationToken = (JwtAuthenticationToken) principal;
    } else {
      throw new AccessDeniedException("Invalid Token...");
    }

    if (!jwtAuthenticationToken.isAuthenticated()) {
      throw new AccessDeniedException("Invalid Token...");
    }

    final boolean hasRoles = this.hasRoles(jwtAuthenticationToken.getAuthorities());
    if (!hasRoles) {
      throw new AccessDeniedException("Not Authorized...");
    }

    return true;
  }

  /**
   * Checks for roles.
   *
   * @param grantedAuthorities
   *          the granted authorities
   * @return true, if successful
   */
  private boolean hasRoles(Collection<GrantedAuthority> grantedAuthorities) {
    if (this.authorizedRoles == null || this.authorizedRoles.isEmpty()) {
      return true;
    }
    if (grantedAuthorities == null || grantedAuthorities.isEmpty()) {
      return false;
    }

    for (final String role : authorizedRoles) {
      for (final GrantedAuthority grantedAuthority : grantedAuthorities) {
        if (role.equalsIgnoreCase(grantedAuthority.getAuthority())) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Do handle.
   *
   * @param session
   *          the session
   * @return the mono
   */
  abstract Mono<Void> doHandle(WebSocketSession session);
}
