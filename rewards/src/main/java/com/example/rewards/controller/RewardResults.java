package com.example.rewards.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RewardResults {

    List<MonthlyRewardTotal> monthlyTotals = new ArrayList<>();
    private int customerId;

    public int getTotalRewardPoints(){
        int total = 0;
        for (MonthlyRewardTotal monthlyTotal : monthlyTotals){
            total += monthlyTotal.getRewardsPoints();
        }
        return total;
    }

}
