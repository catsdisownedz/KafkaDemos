package org.example.common.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_profile")
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customerProfile")
    private List<Transaction> transactions = new ArrayList<>();

    public CustomerProfile() {}

    public CustomerProfile(String email, String password) {
        this.email = email;
        this.password = password;
        this.transactions = new ArrayList<>();
    }
    

    public void addTransaction(double usage, double cost, String currentDate) {
        Transaction transaction = new Transaction(usage, cost, currentDate);
        transaction.setCustomerProfile(this);
        this.transactions.add(transaction);
        
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
