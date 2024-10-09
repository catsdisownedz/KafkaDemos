package org.example.paymentconsumer.service;

import org.example.paymentconsumer.entity.CustomerProfile;
import org.example.paymentconsumer.repository.CustomerProfileRepository;
import org.example.paymentproducer.entity.PaymentDetails;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;
    private final KafkaTemplate<String, PaymentDetails> kafkaTemplate;

    private static final String ADD_TRANSACTION_TOPIC = "add-transaction";
    private static final String CREATE_PROFILE_TOPIC = "create-profile";

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository, KafkaTemplate<String, PaymentDetails> kafkaTemplate) {
        this.customerProfileRepository = customerProfileRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Kafka listener for creating profile
    @KafkaListener(topics = CREATE_PROFILE_TOPIC, groupId = "create-profile-group")
    public void createProfileIfNotExists(PaymentDetails paymentDetails) {
        CustomerProfile profile = customerProfileRepository.findByEmail(paymentDetails.getEmail());

        if (profile == null) {
            profile = new CustomerProfile(paymentDetails.getEmail(), paymentDetails.getPassword());
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            profile.addTransaction(paymentDetails.getUsage(), paymentDetails.getCost(), currentDate);
            customerProfileRepository.save(profile);
        }
    }

    // Kafka listener for adding transactions
    @KafkaListener(topics = ADD_TRANSACTION_TOPIC, groupId = "add-transaction-group")
    public void addTransactionToProfile(PaymentDetails paymentDetails) {
        CustomerProfile profile = customerProfileRepository.findByEmail(paymentDetails.getEmail());

        if (profile != null) {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            profile.addTransaction(paymentDetails.getUsage(), paymentDetails.getCost(), currentDate);
            customerProfileRepository.save(profile);
        }
    }

    // Send transaction to Kafka instead of directly adding it
    public void sendTransactionToKafka(PaymentDetails paymentDetails) {
        kafkaTemplate.send(ADD_TRANSACTION_TOPIC, paymentDetails);
    }

    // Send profile creation to Kafka instead of direct database interaction
    public void sendProfileCreationToKafka(PaymentDetails paymentDetails) {
        kafkaTemplate.send(CREATE_PROFILE_TOPIC, paymentDetails);
    }
}
