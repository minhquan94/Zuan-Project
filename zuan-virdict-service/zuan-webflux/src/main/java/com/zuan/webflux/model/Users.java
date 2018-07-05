/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The Class Users.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Entity
@Table(name = "user")
public class Users implements UserDetails {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 5284613699926137621L;

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private int id;

  /** The user name. */
  @Column(name = "user_name")
  private String userName;

  /** The password. */
  @Column(name = "password")
  private String password;

  /** The name. */
  @Column(name = "name")
  private String name;

  /** The last name. */
  @Column(name = "last_name")
  private String lastName;

  /** The active. */
  @Column(name = "active")
  private int active;

  /** The roles. */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  /**
   * Instantiates a new users.
   */
  public Users() {
    // do nothing
  }

  /**
   * Instantiates a new users.
   *
   * @param users
   *          the users
   */
  public Users(Users users) {
    this.active = users.getActive();
    this.userName = users.getUsername();
    this.roles = users.getRoles();
    this.name = users.getName();
    this.lastName = users.getLastName();
    this.id = users.getId();
    this.password = users.getPassword();
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id
   *          the new id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Sets the user name.
   *
   * @param userName
   *          the new user name
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password
   *          the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name
   *          the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name.
   *
   * @param lastName
   *          the new last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the active.
   *
   * @return the active
   */
  public int getActive() {
    return active;
  }

  /**
   * Sets the active.
   *
   * @param active
   *          the new active
   */
  public void setActive(int active) {
    this.active = active;
  }

  /**
   * Gets the roles.
   *
   * @return the roles
   */
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * Sets the roles.
   *
   * @param roles
   *          the new roles
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
   */
  @Override
  public Collection< ? extends GrantedAuthority> getAuthorities() {
    final Set<GrantedAuthority> authorities = new HashSet<>(this.roles.size());
    for (final Role role : this.roles) {
      authorities.add(new SimpleGrantedAuthority(role.getRole()));
    }
    return authorities;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
   */
  @Override
  public String getUsername() {
    return userName;
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
