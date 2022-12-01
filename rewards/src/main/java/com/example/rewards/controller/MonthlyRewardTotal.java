package com.example.rewards.controller;

import java.time.Month;

import lombok.Data;

@Data
public class MonthlyRewardTotal {

    private Month month;
    private Integer rewardsPoints;
}
