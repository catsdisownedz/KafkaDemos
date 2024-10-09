package org.example.paymentconsumer.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double usage;

    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_profile_id", nullable = false)
    private CustomerProfile customerProfile;

    public Transaction() {}

    public Transaction(double usage, double cost, String date) {
        this.usage = usage;
        this.cost = cost;
        this.date = date;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }
}
