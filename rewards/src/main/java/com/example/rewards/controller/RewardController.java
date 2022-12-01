package com.example.rewards.controller;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardController {
    
/* A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction.
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, 
calculate the reward points earned for each customer per month and total.

Solve using Spring Boot
Create a RESTful endpoint
Make up a data set to best demonstrate your solution
Check solution into GitHub
*/ 

    @PostMapping(path = "/rewards")
    public List<RewardResults> getRewards(TransactionRecord record){
        // given a record of every transaction during a three month period

        // iterate over every record in the list
        // sort by: customer first, then by month, increasing the reward points of that month
        // given we need to modify ongoing data, a lambda within a stream is probably not the best solution

        // need a map of customers - key is customer id
        // need a monthly hashmap per customer to increment - key is Month, value is ongoing rewards points total
        // generally speaking, this business logic would go in a service layer, but that is unnecessary for the purposes of this code sample
        var customerMap = new HashMap<Integer, HashMap<Month, Integer>>();

        for (Transaction transaction : record.getTransactions()){
            var currentCustomer = customerMap.get(transaction.getCustomerId());
            if (currentCustomer == null){
                currentCustomer = new HashMap<Month, Integer>();
            }
            // increment the rewards for the appropriate month
            var rewardsPoints = calculatePoints(transaction.getTransactionAmount());
            if (rewardsPoints > 0){
                // we only need to care if the rewards points are above 0, otherwise the transaction can be ignored
                var month = transaction.getTransactionDate().getMonth();
                if (currentCustomer.get(month) != null){
                    currentCustomer.put(month, currentCustomer.get(month) + rewardsPoints);
                } else {
                    currentCustomer.put(month, rewardsPoints);
                }
            }
            customerMap.put(transaction.getCustomerId(), currentCustomer);
        }
        // now that we've processed all transactions, let's create our result set
        List<RewardResults> results = new ArrayList<>();
        customerMap.forEach((k, m) -> {
            RewardResults result = new RewardResults();
            result.setCustomerId(k);
            // for each customer, sort the months in ascending order
            if (m.size() > 3){
                // this is a problem scenario - we should not have four months based upon our requirements
                return;
            }
            m.keySet().stream().sorted().forEach(e -> {
                MonthlyRewardTotal total = new MonthlyRewardTotal();
                total.setMonth(e);
                total.setRewardsPoints(m.get(e));
                result.getMonthlyTotals().add(total);
            });
            results.add(result);
        });
        return results;
    }

    private int calculatePoints(int transactionAmount){
        int doubleAmount = transactionAmount - 100;

        if (doubleAmount > 1){
            return (50 + 2*doubleAmount);
        }
        int baseAmount = transactionAmount - 50;
        if (baseAmount > 1){
            return (baseAmount);
        }
        return 0;
    }
}
