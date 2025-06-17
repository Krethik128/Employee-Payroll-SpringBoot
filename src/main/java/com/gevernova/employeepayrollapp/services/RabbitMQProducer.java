package com.gevernova.employeepayrollapp.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = Logger.getLogger(RabbitMQProducer.class.getName()); // Initialize logger

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmployeeCreationNotification(String email, String name) {
        Map<String, String> message = new HashMap<>();
        message.put("email", email);
        message.put("name", name);
        logger.info("Sending employee creation notification to queue: " + exchangeName);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    public void sendEmployeeUpdateNotification(String email, String name) {
        Map<String, String> message = new HashMap<>();
        message.put("email", email);
        message.put("name", name);
        logger.info("Sending employee update notification to queue: " + routingKey);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}

