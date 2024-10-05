package org.example.paymentconsumer.service;

import org.example.paymentconsumer.entity.CustomerProfile;
import org.example.paymentconsumer.entity.Transaction;
import org.example.paymentconsumer.repository.CustomerProfileRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final CustomerProfileRepository customerProfileRepository;

    public ReportService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    @KafkaListener(topics = "payments-topic", groupId = "retrieve-profile")
    public List<Transaction> getTransactionsByEmail(String email) {
        CustomerProfile profile = customerProfileRepository.findByEmail(email);
        if (profile != null) {
            return profile.getTransactions();
        }
        return List.of();
    }
}
