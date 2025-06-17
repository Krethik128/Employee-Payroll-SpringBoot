package com.gevernova.employeepayrollapp.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

@Service
public class RabbitMQConsumer {

    private static final Logger logger = Logger.getLogger(RabbitMQConsumer.class.getName());

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeEmployeeCreationMessage(Map<String, String> message) {
        logger.info("Received notification for new employee: " + message.get("name"));

        // Send welcome email to the new employee
        emailService.sendWelcomeEmail(message.get("email"), message.get("name"));

        logger.info("Welcome email sent to: " + message.get("email"));
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveEmployeeUpdateNotification(Map<String, String> message ){
        logger.info("Received employee update notification from queue: " + message);
        // Parse message to extract email and name
        emailService.sendEmailUpdatedMessage(message.get("email"), message.get("name"));// Call the new mail sending method

        logger.info("Update email sent to: " + message.get("email"));
    }
}

