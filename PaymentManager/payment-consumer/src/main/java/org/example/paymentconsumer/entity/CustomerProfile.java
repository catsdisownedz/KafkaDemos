package org.example.paymentconsumer.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    public CustomerProfile() {}

    public CustomerProfile(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addTransaction(double usage, double cost, String date) {
        this.transactions.add(new Transaction(usage, cost, date));
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
