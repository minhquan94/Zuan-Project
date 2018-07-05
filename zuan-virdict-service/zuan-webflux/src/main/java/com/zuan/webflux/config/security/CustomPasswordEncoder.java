/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.config.security;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The Class CustomPasswordEncoder.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
public class CustomPasswordEncoder implements PasswordEncoder {

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.crypto.password.PasswordEncoder#encode(java.lang.CharSequence)
   */
  @Override
  public String encode(CharSequence rawPassword) {
    final SecureRandom random = new SecureRandom();
    final byte bytes[] = new byte[20];
    random.nextBytes(bytes);

    final String hashed = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(14, random));
    return hashed;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.crypto.password.PasswordEncoder#matches(java.lang.CharSequence,
   *      java.lang.String)
   */
  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    final String decodedString = new String(Base64.getDecoder().decode(rawPassword.toString()));
    return BCrypt.checkpw(decodedString, encodedPassword);
  }
}
