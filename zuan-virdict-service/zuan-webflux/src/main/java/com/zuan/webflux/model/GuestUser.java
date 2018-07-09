package com.zuan.webflux.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The Class GuestUser.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class GuestUser implements UserDetails {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 8117594720015117686L;

  /** The user name. */
  private String username;

  /**
   * Instantiates a new guest user.
   *
   * @param username
   *          the username
   */
  public GuestUser(String username) {
    this.username = username;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
   */
  @Override
  public Collection< ? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority(UserRoleEnum.GUEST.name()));
    return authorities;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
   */
  @Override
  public String getPassword() {
    return null;
  }

  /**
   * Sets the username.
   *
   * @param username
   *          the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
   */
  @Override
  public String getUsername() {
    return username;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
   */
  @Override
  public boolean isEnabled() {
    return true;
  }

}
