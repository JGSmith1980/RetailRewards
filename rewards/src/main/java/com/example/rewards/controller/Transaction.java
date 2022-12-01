package com.example.rewards.controller;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {

    private LocalDate transactionDate;
    private int transactionAmount;
    private Integer customerId;
}
