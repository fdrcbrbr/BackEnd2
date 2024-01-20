package com.example.api.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public record RabbitMQMessageProducer(RabbitTemplate rabbitTemplate) {

    public void publish(Object payload,
                        String exchange,
                        String routingKey){
        log.info("Publishing to {} using routingKey {}. Payload: {}", exchange, routingKey, payload);
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Published  to {} using routingKey {}. Payload: {}", exchange, routingKey, payload);
    }
}
