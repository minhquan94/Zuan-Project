/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.dto;

import com.zuan.webflux.model.Users;

/**
 * The Class UserDto.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
public class UserDto {

  /** The user name. */
  private String username;

  /** The password. */
  private String password;

  /** The name. */
  private String name;

  /** The last name. */
  private String lastName;

  /**
   * Instantiates a new user dto.
   */
  public UserDto() {
    super();
  }

  /**
   * Instantiates a new user dto.
   *
   * @param user
   *          the user
   */
  public UserDto(final Users user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.name = user.getName();
    this.lastName = user.getLastName();
  }

  /**
   * Gets the user name.
   *
   * @return the user name
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the user name.
   *
   * @param userName
   *          the new user name
   */
  public void setUsername(String userName) {
    this.username = userName;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
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

}
