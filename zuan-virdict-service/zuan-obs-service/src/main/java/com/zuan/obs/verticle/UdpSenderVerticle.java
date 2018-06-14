/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.obs.verticle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zuan.obs.configuration.DepotConfiguration;

import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.eventbus.Message;

/**
 * The Class UdpVerticle.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">zuan_</a>
 */
@Verticle("UdpSender")
public class UdpSenderVerticle extends VerticleBase {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(UdpSenderVerticle.class);

  /** The socket. */
  private DatagramSocket socket;

  /** The obs config. */
  @Autowired
  private DepotConfiguration depotConfiguration;

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
    final Buffer buffer = new BufferImpl();
    buffer.appendString("OBS Hello");
    vertx.setPeriodic(100,
        handler -> vertx.eventBus().send(EventbusAddress.UDP_SENDER, buffer));
  }

  /**
   * Register consummer.
   */
  public void registerConsummer() {
    vertx.eventBus().consumer(EventbusAddress.UDP_SENDER, (Message<Buffer> message) -> {
      sendMessage(message.body());
    });
  }

  /**
   * Send message.
   *
   * @param buffer
   *          the buffer
   */
  private void sendMessage(final Buffer buffer) {
    socket.send(buffer, depotConfiguration.getPort(), depotConfiguration.getHost(),
        handler -> {
          if (handler.failed()) {
            LOG.error("Couldn't send a message to {}:{}", depotConfiguration.getHost(),
                depotConfiguration.getPort());
          } else {
            LOG.info("Send a message to OBS successfully, [{}]", buffer);
          }
        });
  }
}
