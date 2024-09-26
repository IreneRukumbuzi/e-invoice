package com.qt.e_invoice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.qt.e_invoice.config.RabbitMQConfig;

@Service
public class RabbitMQConsumer {

  @RabbitListener(queues = RabbitMQConfig.INVOICE_QUEUE)
  public void receiveInvoiceNotification(String message) {
    System.out.println("Received RabbitMQ message: " + message);
  }
}
