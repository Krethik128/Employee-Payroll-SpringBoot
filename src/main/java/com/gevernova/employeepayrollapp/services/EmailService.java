package com.gevernova.employeepayrollapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Our Company!");
        message.setText("Dear " + name + ",\n\n" +
                "Welcome to our company! We're excited to have you on board.\n\n" +
                "Best regards");

        mailSender.send(message);
    }
}

