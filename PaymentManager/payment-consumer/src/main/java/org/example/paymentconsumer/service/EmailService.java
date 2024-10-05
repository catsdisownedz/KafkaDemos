package org.example.paymentconsumer.service;

import org.example.paymentproducer.entity.PaymentDetails;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "payments-topic", groupId = "email-group")
    public void sendReceiptEmail(PaymentDetails paymentDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(paymentDetails.getEmail());
        message.setSubject("Payment Receipt");
        message.setText("Here is your receipt for the transaction:\n" +
                "Usage: " + paymentDetails.getUsage() + " MB\n" +
                "Cost: " + paymentDetails.getCost() + " L.E\n" +
                "Date of transaction: " + paymentDetails.getTransactionDate() + "\n\n" +
                "Paid using Delusional Visa ;D\n\n" +
                "Banque du Zeina,\nYour fellow intern.");

        mailSender.send(message);

        // Log the success of email sent
        System.out.println("Receipt email sent to: " + paymentDetails.getEmail());
    }
}
