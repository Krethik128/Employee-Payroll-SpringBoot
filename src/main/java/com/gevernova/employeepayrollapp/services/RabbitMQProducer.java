package com.gevernova.employeepayrollapp.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmployeeCreationNotification(String email, String name) {
        Map<String, String> message = new HashMap<>();
        message.put("email", email);
        message.put("name", name);

        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}

