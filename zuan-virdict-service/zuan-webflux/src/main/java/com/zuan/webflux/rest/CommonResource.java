package com.zuan.webflux.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuan.webflux.config.security.jwt.JwtAuthenticationResponse;
import com.zuan.webflux.config.security.jwt.JwtPreAuthenticationToken;
import com.zuan.webflux.service.SecurityService;

import reactor.core.publisher.Mono;

/**
 * The Class CommonResource.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@RestController
@RequestMapping(path = "/rest/common", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
public class CommonResource {

  /** The security service. */
  @Autowired
  private SecurityService securityService;

  /**
   * Gets the user.
   *
   * @return the user
   */
  @PostMapping
  @CrossOrigin("http://localhost:4200")
  public Mono<ResponseEntity<JwtAuthenticationResponse>> getUser() {
    JwtPreAuthenticationToken authentication =
        (JwtPreAuthenticationToken) securityService.getAuthentication();
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(new JwtAuthenticationResponse(authentication.getAuthToken(),
            authentication.getUsername())));
  }
}
