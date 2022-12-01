package com.example.rewards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RewardControllerTest {

    @Autowired
    private RewardController controller;

    @Test
    void testGetRewards() {
        TransactionRecord record = new TransactionRecord();
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(generateTransaction(1, 100, 2020, 1, 1));
        record.setTransactions(transactionList);
        List<RewardResults> result = controller.getRewards(record);
        assertEquals(1, result.size());
        assertEquals(50, result.get(0).getTotalRewardPoints());

        transactionList.add(generateTransaction(1, 100, 2020, 1, 1));      
        result = controller.getRewards(record);
        assertEquals(1, result.size());        
        assertEquals(100, result.get(0).getTotalRewardPoints());


        transactionList.add(generateTransaction(1, 200, 2020, 2, 1));      
        result = controller.getRewards(record);
        assertEquals(1, result.size());

        transactionList.add(generateTransaction(1, 200, 2020, 3, 1));      
        result = controller.getRewards(record);

        assertEquals(1, result.size());
        assertEquals(600, result.get(0).getTotalRewardPoints());

        transactionList.add(generateTransaction(2, 100, 2020, 2, 1));      
        result = controller.getRewards(record);
        assertEquals(2, result.size());
        assertEquals(600, result.get(0).getTotalRewardPoints());
        assertEquals(50, result.get(1).getTotalRewardPoints());
        transactionList.add(generateTransaction(3, 100, 2020, 2, 1));      
        result = controller.getRewards(record);
        assertEquals(3, result.size());
        assertEquals(600, result.get(0).getTotalRewardPoints());
        assertEquals(50, result.get(1).getTotalRewardPoints());
        assertEquals(50, result.get(2).getTotalRewardPoints());

    }

    private Transaction generateTransaction(Integer customerId, Integer amount, int year, int month, int day){
        return Transaction.builder().customerId(customerId).transactionAmount(amount).transactionDate(LocalDate.of(year, month, day)).build();
    }
}
