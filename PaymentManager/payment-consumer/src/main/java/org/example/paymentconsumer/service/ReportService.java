package org.example.paymentconsumer.service;

import org.example.paymentconsumer.entity.CustomerProfile;
import org.example.paymentconsumer.entity.Transaction;
import org.example.paymentconsumer.repository.CustomerProfileRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final CustomerProfileRepository customerProfileRepository;
    private final KafkaTemplate<String, CustomerProfile> kafkaTemplate; // Using CustomerProfile for Avro serialization

    private static final String RESPONSE_TOPIC = "response-transactions";

    public ReportService(CustomerProfileRepository customerProfileRepository, KafkaTemplate<String, CustomerProfile> kafkaTemplate) {
        this.customerProfileRepository = customerProfileRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "request-transactions", groupId = "report-group")
    public void handleRetrieveProfileRequest(String email) {
        CustomerProfile profile = customerProfileRepository.findByEmail(email);
        if (profile == null) {
            profile = new CustomerProfile();
            profile.setEmail(email);
        }
        kafkaTemplate.send(RESPONSE_TOPIC, profile);
    }
}
