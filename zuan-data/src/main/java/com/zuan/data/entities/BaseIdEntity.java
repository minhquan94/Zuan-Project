/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.data.entities;

/**
 * The Interface BaseIdEntity.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@FunctionalInterface
public interface BaseIdEntity extends BaseEntity {

  /**
   * Gets the id.
   *
   * @return the id
   */
  Long getId();
}
