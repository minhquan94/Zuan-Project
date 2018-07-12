/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.jpa.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The Class Users.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Entity
@Table(name = "User")
public class UserEntity implements UserDetails, BaseIdEntity {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 5284613699926137621L;

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "UserId")
  private Long id;

  /** The user type. */
  @Column(name = "UserType")
  private short userType;

  /** The created time. */
  @Column(name = "CreatedTime")
  private Timestamp createdTime;

  /** The delete flag. */
  @Column(name = "DeleteFlag")
  private boolean deleteFlag;

  /** The delete time. */
  @Column(name = "DeleteTime")
  private Timestamp deleteTime;

  /** The description. */
  @Column(name = "Description")
  private String description;

  /** The email. */
  @Column(name = "Email")
  private String email;

  /** The full name. */
  @Column(name = "FullName")
  private String fullName;

  /** The phone number. */
  @Column(name = "PhoneNumber")
  private String phoneNumber;

  /** The updated time. */
  @Column(name = "UpdatedTime")
  private Timestamp updatedTime;

  /** The user domain. */
  @Column(name = "UserDomain")
  private String userDomain;

  /** The user name. */
  @Column(name = "UserName")
  private String userName;

  /** The password. */
  @Column(name = "Password")
  private String password;

  /** The enable. */
  @Column(name = "UserStatus")
  private boolean userStatus;

  /** The version. */
  @Version
  @Column(name = "Version")
  private int version;

  /** The expiration time. */
  @Column(name = "UserExpirationTime")
  private Long userExpirationTime;

  /** The password expiration time. */
  @Column(name = "PasswordExpirationTime")
  private Long passwordExpirationTime;

  /** The user avatar entity. */
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @PrimaryKeyJoinColumn
  private UserAvatarEntity userAvatar;

  /** The roles1. */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "RoleUser", joinColumns = {
      @JoinColumn(name = "UserId") }, inverseJoinColumns = {@JoinColumn(name = "RoleId") })
  private List<RoleEntity> roles;

  /** The roles2. */
  @OneToMany(mappedBy = "createdUser", fetch = FetchType.LAZY)
  private List<RoleEntity> rolesCreatedByUser;

  /** The roles3. */
  @OneToMany(mappedBy = "updatedUser", fetch = FetchType.LAZY)
  private List<RoleEntity> rolesUpdatedByUser;

  /**
   * Instantiates a new users.
   */
  public UserEntity() {
    // do nothing
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Gets the user type.
   *
   * @return the user type
   */
  public short getUserType() {
    return userType;
  }

  /**
   * Gets the created time.
   *
   * @return the created time
   */
  public Timestamp getCreatedTime() {
    return createdTime;
  }

  /**
   * Checks if is delete flag.
   *
   * @return true, if is delete flag
   */
  public boolean isDeleteFlag() {
    return deleteFlag;
  }

  /**
   * Gets the delete time.
   *
   * @return the delete time
   */
  public Timestamp getDeleteTime() {
    return deleteTime;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the full name.
   *
   * @return the full name
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Gets the phone number.
   *
   * @return the phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Gets the updated time.
   *
   * @return the updated time
   */
  public Timestamp getUpdatedTime() {
    return updatedTime;
  }

  /**
   * Gets the user domain.
   *
   * @return the user domain
   */
  public String getUserDomain() {
    return userDomain;
  }

  /**
   * Gets the user name.
   *
   * @return the user name
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Checks if is user status.
   *
   * @return true, if is user status
   */
  public boolean isUserStatus() {
    return userStatus;
  }

  /**
   * Gets the version.
   *
   * @return the version
   */
  public int getVersion() {
    return version;
  }

  /**
   * Gets the user expiration time.
   *
   * @return the user expiration time
   */
  public Long getUserExpirationTime() {
    return userExpirationTime;
  }

  /**
   * Gets the password expiration time.
   *
   * @return the password expiration time
   */
  public Long getPasswordExpirationTime() {
    return passwordExpirationTime;
  }

  /**
   * Gets the user avatar.
   *
   * @return the user avatar
   */
  public UserAvatarEntity getUserAvatar() {
    return userAvatar;
  }

  /**
   * Gets the roles.
   *
   * @return the roles
   */
  public List<RoleEntity> getRoles() {
    return roles;
  }

  /**
   * Gets the roles created by user.
   *
   * @return the roles created by user
   */
  public List<RoleEntity> getRolesCreatedByUser() {
    return rolesCreatedByUser;
  }

  /**
   * Gets the roles updated by user.
   *
   * @return the roles updated by user
   */
  public List<RoleEntity> getRolesUpdatedByUser() {
    return rolesUpdatedByUser;
  }

  /**
   * Sets the user type.
   *
   * @param userType
   *          the new user type
   */
  public void setUserType(short userType) {
    this.userType = userType;
  }

  /**
   * Sets the created time.
   *
   * @param createdTime
   *          the new created time
   */
  public void setCreatedTime(Timestamp createdTime) {
    this.createdTime = createdTime;
  }

  /**
   * Sets the delete flag.
   *
   * @param deleteFlag
   *          the new delete flag
   */
  public void setDeleteFlag(boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  /**
   * Sets the delete time.
   *
   * @param deleteTime
   *          the new delete time
   */
  public void setDeleteTime(Timestamp deleteTime) {
    this.deleteTime = deleteTime;
  }

  /**
   * Sets the description.
   *
   * @param description
   *          the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets the email.
   *
   * @param email
   *          the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Sets the full name.
   *
   * @param fullName
   *          the new full name
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Sets the phone number.
   *
   * @param phoneNumber
   *          the new phone number
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Sets the updated time.
   *
   * @param updatedTime
   *          the new updated time
   */
  public void setUpdatedTime(Timestamp updatedTime) {
    this.updatedTime = updatedTime;
  }

  /**
   * Sets the user domain.
   *
   * @param userDomain
   *          the new user domain
   */
  public void setUserDomain(String userDomain) {
    this.userDomain = userDomain;
  }

  /**
   * Sets the user status.
   *
   * @param userStatus
   *          the new user status
   */
  public void setUserStatus(boolean userStatus) {
    this.userStatus = userStatus;
  }

  /**
   * Sets the version.
   *
   * @param version
   *          the new version
   */
  public void setVersion(int version) {
    this.version = version;
  }

  /**
   * Sets the user expiration time.
   *
   * @param userExpirationTime
   *          the new user expiration time
   */
  public void setUserExpirationTime(Long userExpirationTime) {
    this.userExpirationTime = userExpirationTime;
  }

  /**
   * Sets the password expiration time.
   *
   * @param passwordExpirationTime
   *          the new password expiration time
   */
  public void setPasswordExpirationTime(Long passwordExpirationTime) {
    this.passwordExpirationTime = passwordExpirationTime;
  }

  /**
   * Sets the user avatar.
   *
   * @param userAvatar
   *          the new user avatar
   */
  public void setUserAvatar(UserAvatarEntity userAvatar) {
    this.userAvatar = userAvatar;
  }

  /**
   * Sets the roles.
   *
   * @param roles
   *          the new roles
   */
  public void setRoles(List<RoleEntity> roles) {
    this.roles = roles;
  }

  /**
   * Sets the roles created by user.
   *
   * @param rolesCreatedByUser
   *          the new roles created by user
   */
  public void setRolesCreatedByUser(List<RoleEntity> rolesCreatedByUser) {
    this.rolesCreatedByUser = rolesCreatedByUser;
  }

  /**
   * Sets the roles updated by user.
   *
   * @param rolesUpdatedByUser
   *          the new roles updated by user
   */
  public void setRolesUpdatedByUser(List<RoleEntity> rolesUpdatedByUser) {
    this.rolesUpdatedByUser = rolesUpdatedByUser;
  }

  /**
   * Sets the id.
   *
   * @param id
   *          the new id
   */
  public void setId(Long id) {
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
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
   */
  @Override
  public Collection< ? extends GrantedAuthority> getAuthorities() {
    final Set<GrantedAuthority> authorities = new HashSet<>(this.roles.size());
    for (final RoleEntity role : this.roles) {
      authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
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
