package com.qt.e_invoice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qt.e_invoice.config.RabbitMQConfig;

@Service
public class RabbitMQProducer {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendInvoiceNotification(String message) {
    rabbitTemplate.convertAndSend(RabbitMQConfig.INVOICE_EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);
  }
}
