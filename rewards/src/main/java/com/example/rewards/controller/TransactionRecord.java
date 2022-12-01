package com.example.rewards.controller;

import java.util.List;

import lombok.Data;

@Data
public class TransactionRecord {

    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
