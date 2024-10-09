package org.example.paymentproducer;


import org.example.common.entity.CustomerProfile;
import org.example.common.entity.PaymentDetails;
import org.example.common.entity.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    private KafkaTemplate<String, PaymentDetails> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;
    private static final String PAYMENTS_TOPIC = "payments-topic";
    private static final String REQUEST_TRANSACTIONS_TOPIC = "request-transactions";
    private static final String RESPONSE_TRANSACTIONS_TOPIC = "response-transactions";
    private final Map<String, DeferredResult<List<?>>> pendingTransactionRequests = new HashMap<>();

    @PostMapping("/api/pay")
    public String processPayment(@RequestBody PaymentRequest request) {
        double usage = request.getUsage();
        double cost = request.getCost();
        String email = request.getEmail();
        String password = request.getPassword();

        PaymentDetails paymentDetails = new PaymentDetails(email, usage, cost, password, LocalDate.now().toString());

        kafkaTemplate.send(PAYMENTS_TOPIC, paymentDetails);

        return "Payment processed for " + email + ".";
    }


    @GetMapping("/api/transactions")
    public DeferredResult<List<?>> viewTransactions(@RequestParam("email") String email) {
        DeferredResult<List<?>> deferredResult = new DeferredResult<>();
        pendingTransactionRequests.put(email, deferredResult);
        stringKafkaTemplate.send(REQUEST_TRANSACTIONS_TOPIC, email);
        return deferredResult;
    }


    @KafkaListener(topics = RESPONSE_TRANSACTIONS_TOPIC, groupId = "report-group")
    public void handleTransactionResponse(CustomerProfile transactionResponse) {
        String email = transactionResponse.getEmail();
        if (pendingTransactionRequests.containsKey(email)) {
            DeferredResult<List<?>> deferredResult = pendingTransactionRequests.get(email);
            deferredResult.setResult(transactionResponse.getTransactions());
            pendingTransactionRequests.remove(email);
        }
    }
}
