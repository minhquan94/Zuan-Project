/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.obs.configuration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.zuan.obs.verticle.Verticle;
import com.zuan.obs.verticle.VerticleBase;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * The listener interface for receiving startup events. The class that is
 * interested in processing a startup event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addStartupListener<code> method. When the startup event
 * occurs, that object's appropriate method is invoked.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Component
public class StartupListener {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(StartupListener.class);

  /** The depot config. */
  @Autowired
  private ObsConfiguration obsConfiguration;

  /** The context. */
  @Autowired
  private ConfigurableApplicationContext context;

  /**
   * Start verticles.
   */
  @EventListener(ContextRefreshedEvent.class)
  public void startVerticles() {
    LOG.info("Deploying Depot Verticles...");
    final Map<String, VerticleBase> verticlesDeploying = new HashMap<>();
    final Map<String, Object> verticles = context.getBeansWithAnnotation(Verticle.class);
    verticles.forEach((k, v) -> {
      if (obsConfiguration.getVerticles().contains(k) && v instanceof VerticleBase) {
        verticlesDeploying.put(k, (VerticleBase) v);
      } else {
        LOG.warn("{} bean isn't a Verticle", k);
        context.getBeanFactory().destroyBean(k);
      }
    });
    deployVerticle(verticlesDeploying);
  }

  /**
   * Deploy verticle.
   *
   * @param verticlesDeploying
   *          the verticles deploying
   */
  private void deployVerticle(final Map<String, VerticleBase> verticlesDeploying) {
    final VertxOptions options = new VertxOptions();
    final Vertx vertx = Vertx.vertx(options);
    verticlesDeploying.forEach((k, v) -> {
      final DeploymentOptions deploymentOptions = new DeploymentOptions();
      deploymentOptions.setWorker(true);
      deploymentOptions.setWorkerPoolName(k);
      vertx.deployVerticle(v, deploymentOptions, result -> {
        if (result.failed()) {
          LOG.error("Failed to deploy {} , cause: {}", k, result.cause());
        }
      });
    });
  }
}
