/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.obs.verticle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zuan.obs.configuration.ObsConfiguration;

import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;

/**
 * The Class UdpVerticle.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Verticle("UdpReceiver")
public class UdpReceiverVerticle extends VerticleBase {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(UdpReceiverVerticle.class);

  /** The socket. */
  private DatagramSocket socket;

  /** The depot config. */
  @Autowired
  private ObsConfiguration obsConfiguration;

  /**
   * {@inheritDoc}
   *
   * @see io.vertx.core.AbstractVerticle#start(io.vertx.core.Future)
   */
  @Override
  public void start(Future<Void> startFuture) throws Exception {
    super.start(startFuture);
    socket = vertx.createDatagramSocket(new DatagramSocketOptions());
    registerConsummer();
  }

  /**
   * Register consummer.
   */
  public void registerConsummer() {
    socket.listen(obsConfiguration.getPort(), obsConfiguration.getHost(), asyncResult -> {
      if (asyncResult.succeeded()) {
        socket.handler(packet -> {
          LOG.info("Received a message: [{}]", packet.data());
        });
      } else {
        LOG.error("{}", asyncResult.cause());
      }
    });
  }

  /**
   * Send message.
   *
   * @param buffer
   *          the buffer
   */
  private void sendMessage(final Buffer buffer) {
    // TODO
  }
}
