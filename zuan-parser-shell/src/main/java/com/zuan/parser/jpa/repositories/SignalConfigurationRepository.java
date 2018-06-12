/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.zuan.parser.jpa.entities.SignalConfigurationEntity;

/**
 * The Interface SignalConfigurationRepository.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Transactional
public interface SignalConfigurationRepository
    extends JpaRepository<SignalConfigurationEntity, Long> {

  /**
   * Find by train project.
   *
   * @param trainProject
   *          the train project
   * @return the list
   */
  List<SignalConfigurationEntity> findByTrainProject(String trainProject);

  /**
   * Find by train project realtime.
   *
   * @param trainProject
   *          the train project
   * @param isRealtime
   *          the is realtime
   * @return the list
   */
  @Query("SELECT e FROM SignalConfiguration e WHERE e.trainProject = ?1 AND e.includeInRealTime = ?2")
  List<SignalConfigurationEntity> findByTrainProjectRealtime(String trainProject,
      boolean isRealtime);

  /**
   * Find by train and signal code.
   *
   * @param trainProject
   *          the train project
   * @param signalCode
   *          the signal code
   * @return the signal configuration entity
   */
  @Query("SELECT e FROM SignalConfiguration e WHERE e.trainProject = ?1 AND e.signalCode = ?2")
  SignalConfigurationEntity findByTrainAndSignalCode(String trainProject, String signalCode);
}
