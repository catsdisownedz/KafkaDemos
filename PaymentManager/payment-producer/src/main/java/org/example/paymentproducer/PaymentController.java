package org.example.paymentproducer;

import org.example.paymentconsumer.entity.Transaction;
import org.example.paymentconsumer.service.ReportService;
import org.example.paymentproducer.entity.PaymentDetails;
import org.example.paymentproducer.entity.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    private KafkaTemplate<String, PaymentDetails> kafkaTemplate;

    private static final String TOPIC = "payments-topic";

    @PostMapping("/api/pay")
    public String processPayment(@RequestBody PaymentRequest request) {
        double usage = request.getUsage();
        double cost = request.getCost();
        String email = request.getEmail();
        String password = request.getPassword();

        PaymentDetails paymentDetails = new PaymentDetails(email, usage, cost, password, LocalDate.now().toString());

        kafkaTemplate.send(TOPIC, paymentDetails);

        return "Payment processed for " + email + ".";
    }

    @GetMapping("/api/transactions")
    public List<Transaction> viewTransactions(@RequestParam("email") String email) {
        return ReportService.getTransactionsByEmail(email);
    }
}
