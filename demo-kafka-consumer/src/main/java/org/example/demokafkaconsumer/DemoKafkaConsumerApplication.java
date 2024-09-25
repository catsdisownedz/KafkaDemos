package org.example.demokafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DemoKafkaConsumerApplication {

    private final JavaMailSender javaMailSender;

    public DemoKafkaConsumerApplication(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @KafkaListener(topics = "usernames-topic", groupId = "email-group")
    public void consume(String username) {
        sendEmail(username);
    }

    private void sendEmail(String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("catsdisownedz@hotmail.com");
        message.setSubject("Kafka Email");
        message.setText("Hello, " + username);
        message.setFrom("CDRapplication@hotmail.com");
        javaMailSender.send(message);
        System.out.println("Email sent to " + username);
    }
}