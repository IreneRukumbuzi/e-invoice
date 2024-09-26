package com.qt.e_invoice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String INVOICE_QUEUE = "invoiceQueue";
  public static final String INVOICE_EXCHANGE = "invoiceExchange";
  public static final String ROUTING_KEY = "invoice.key";

  @Bean
  public Queue invoiceQueue() {
    return new Queue(INVOICE_QUEUE, true);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(INVOICE_EXCHANGE);
  }

  @Bean
  public Binding binding(Queue invoiceQueue, TopicExchange exchange) {
    return BindingBuilder.bind(invoiceQueue).to(exchange).with(ROUTING_KEY);
  }
}
