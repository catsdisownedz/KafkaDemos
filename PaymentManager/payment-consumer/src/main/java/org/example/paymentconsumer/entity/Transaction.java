package org.example.paymentconsumer.entity;

import jakarta.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double usage;
    private double cost;
    private String date;

    public Transaction() {}

    public Transaction(double usage, double cost, String date) {
        this.usage = usage;
        this.cost = cost;
        this.date = date;
    }

}
