/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

/**
 * The Class JwtTokenService.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Component
public class JwtTokenService implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant CLAIM_KEY_USERNAME. */
  static final String CLAIM_KEY_USERNAME = "sub";

  /** The Constant CLAIM_KEY_AUDIENCE. */
  static final String CLAIM_KEY_AUDIENCE = "aud";

  /** The Constant CLAIM_KEY_CREATED. */
  static final String CLAIM_KEY_CREATED = "iat";

  /** The Constant AUDIENCE_UNKNOWN. */
  static final String AUDIENCE_UNKNOWN = "unknown";

  /** The Constant AUDIENCE_WEB. */
  static final String AUDIENCE_WEB = "web";

  /** The Constant AUDIENCE_MOBILE. */
  static final String AUDIENCE_MOBILE = "mobile";

  /** The Constant AUDIENCE_TABLET. */
  static final String AUDIENCE_TABLET = "tablet";

  /** The clock. */
  private static final transient Clock clock = DefaultClock.INSTANCE;

  /** The secret. */
  @Value("${jwt.secret}")
  private final String secret;

  /** The expiration. */
  @Value("${jwt.expiration}")
  private final Long expiration;

  /**
   * Instantiates a new jwt token util.
   *
   * @param secret
   *          the secret
   * @param expiration
   *          the expiration
   */
  public JwtTokenService(@Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") Long expiration) {
    this.secret = secret;
    this.expiration = expiration;
  }

  /**
   * Gets the username from token.
   *
   * @param token
   *          the token
   * @return the username from token
   */
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * Gets the issued at date from token.
   *
   * @param token
   *          the token
   * @return the issued at date from token
   */
  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  /**
   * Gets the expiration date from token.
   *
   * @param token
   *          the token
   * @return the expiration date from token
   */
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * Gets the audience from token.
   *
   * @param token
   *          the token
   * @return the audience from token
   */
  public String getAudienceFromToken(String token) {
    return getClaimFromToken(token, Claims::getAudience);
  }

  /**
   * Gets the claim from token.
   *
   * @param <T>
   *          the generic type
   * @param token
   *          the token
   * @param claimsResolver
   *          the claims resolver
   * @return the claim from token
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Gets the all claims from token.
   *
   * @param token
   *          the token
   * @return the all claims from token
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  /**
   * Checks if is token expired.
   *
   * @param token
   *          the token
   * @return the boolean
   */
  private Boolean isTokenExpired(String token) {
    return getExpirationDateFromToken(token).before(clock.now());
  }

  /**
   * Checks if is created before last password reset.
   *
   * @param created
   *          the created
   * @param lastPasswordReset
   *          the last password reset
   * @return the boolean
   */
  private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
    return lastPasswordReset != null && created.before(lastPasswordReset);
  }

  /**
   * Generate audience.
   *
   * @return the string
   */
  private String generateAudience() {
    return AUDIENCE_UNKNOWN;
  }

  /**
   * Ignore token expiration.
   *
   * @param token
   *          the token
   * @return the boolean
   */
  private Boolean ignoreTokenExpiration(String token) {
    final String audience = getAudienceFromToken(token);
    return AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience);
  }

  /**
   * Generate token.
   *
   * @param userDetails
   *          the user details
   * @return the string
   */
  public String generateToken(UserDetails userDetails) {
    final Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername(), generateAudience());
  }

  /**
   * Do generate token.
   *
   * @param claims
   *          the claims
   * @param subject
   *          the subject
   * @param audience
   *          the audience
   * @return the string
   */
  private String doGenerateToken(Map<String, Object> claims, String subject, String audience) {
    final Date createdDate = clock.now();
    final Date expirationDate = calculateExpirationDate(createdDate);

    return Jwts.builder().setClaims(claims).setSubject(subject).setAudience(audience)
        .setIssuedAt(createdDate).setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  /**
   * Can token be refreshed.
   *
   * @param token
   *          the token
   * @param lastPasswordReset
   *          the last password reset
   * @return the boolean
   */
  public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
    final Date created = getIssuedAtDateFromToken(token);
    return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
        && (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  /**
   * Refresh token.
   *
   * @param token
   *          the token
   * @return the string
   */
  public String refreshToken(String token) {
    final Date createdDate = clock.now();
    final Date expirationDate = calculateExpirationDate(createdDate);

    final Claims claims = getAllClaimsFromToken(token);
    claims.setIssuedAt(createdDate);
    claims.setExpiration(expirationDate);

    return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  /**
   * Validate token.
   *
   * @param token
   *          the token
   * @return the boolean
   */
  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  /**
   * Calculate expiration date.
   *
   * @param createdDate
   *          the created date
   * @return the date
   */
  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 1000);
  }
}
