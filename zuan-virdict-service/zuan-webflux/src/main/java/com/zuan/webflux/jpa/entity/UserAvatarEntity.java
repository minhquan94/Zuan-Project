/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.webflux.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The Class UserAvatarEntity.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">phongtnguyen.jk</a>
 */
@Entity(name = "UserAvatar")
public class UserAvatarEntity implements BaseIdEntity {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6199167460640489953L;

  /** The id. */
  @Id
  @GeneratedValue(generator = "UserAvatarGenerator")
  @GenericGenerator(name = "UserAvatarGenerator", strategy = "foreign", parameters = @Parameter(value = "user", name = "property"))
  @Column(name = "UserId")
  private Long userId;

  /** The image binary. */
  @Column(name = "ImageBinary")
  private byte[] imageBinary;

  /** The user. */
  @OneToOne(mappedBy = "userAvatar", fetch = FetchType.LAZY)
  private UserEntity user;

  /**
   * Gets the image binary.
   *
   * @return the image binary
   */
  public byte[] getImageBinary() {
    return imageBinary;
  }

  /**
   * Gets the user.
   *
   * @return the user
   */
  public UserEntity getUser() {
    return user;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.data.jpa.entity.BaseIdEntity#getId()
   */
  @Override
  public Long getId() {
    return userId;
  }

  /**
   * Sets the image binary.
   *
   * @param imageBinary
   *          the new image binary
   */
  public void setImageBinary(final byte[] imageBinary) {
    this.imageBinary = imageBinary;
  }

  /**
   * Sets the user.
   *
   * @param user
   *          the new user
   */
  public void setUser(final UserEntity user) {
    this.user = user;
  }

  /**
   * Sets the user id.
   *
   * @param userId
   *          the new user id
   */
  public void setUserId(final Long userId) {
    this.userId = userId;
  }

}
