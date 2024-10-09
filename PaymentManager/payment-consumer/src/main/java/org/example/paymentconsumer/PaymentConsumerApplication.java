package org.example.paymentconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.paymentconsumer.repository")
public class PaymentConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentConsumerApplication.class, args);
    }

}
