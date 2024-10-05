package org.example.paymentconsumer.service;

import org.example.paymentconsumer.entity.CustomerProfile;
import org.example.paymentconsumer.repository.CustomerProfileRepository;
import org.example.paymentproducer.entity.PaymentDetails;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }


    @KafkaListener(topics = "payments-topic", groupId = "create-profile")
    public void createProfileIfNotExists(PaymentDetails paymentDetails) {
        CustomerProfile profile = customerProfileRepository.findByEmail(paymentDetails.getEmail());

        if (profile == null) {
            profile = new CustomerProfile(paymentDetails.getEmail(), paymentDetails.getPassword());
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            profile.addTransaction(paymentDetails.getUsage(), paymentDetails.getCost(), currentDate);
            customerProfileRepository.save(profile);
        }
    }
    
    @KafkaListener(topics = "payments-topic", groupId = "add-transaction")
    public void addTransactionToProfile(PaymentDetails paymentDetails) {
        CustomerProfile profile = customerProfileRepository.findByEmail(paymentDetails.getEmail());

        if (profile != null) {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            profile.addTransaction(paymentDetails.getUsage(), paymentDetails.getCost(), currentDate);
            customerProfileRepository.save(profile);
        }
    }
}
