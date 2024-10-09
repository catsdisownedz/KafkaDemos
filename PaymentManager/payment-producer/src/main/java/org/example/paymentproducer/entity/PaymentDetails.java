package org.example.paymentproducer.entity;

import java.io.Serializable;

public class PaymentDetails implements Serializable {
    private String email;
    private double usage;
    private double cost;
    private String password;
    private String transactionDate;

    public PaymentDetails() {}
    public PaymentDetails(String email, double usage, double cost, String password, String transactionDate) {
        this.email = email;
        this.usage = usage;
        this.cost = cost;
        this.password = password;
        this.transactionDate = transactionDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
