package com.web.api.developer.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.web.api.developer.service.RewardsService;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RewardsService rewardsService;

    @Override
    public void run(String... args) {
        Random random = new Random();
        int numberOfTransactions = 50;

        for (int i = 0; i < numberOfTransactions; i++) {

            Transaction transaction = new Transaction();

            String customerId = "CUST" + random.nextInt(1000);
            double amount = random.nextDouble() * 200; // Random amount up to $200
            long finalAmount = Math.round((amount * 100D) / 100D);

            LocalDateTime timestamp = LocalDateTime.now().minusMonths(random.nextInt(3));

            int rewardPoints = rewardsService.calculateRewardPointsForTransaction(finalAmount);

            transaction.setMonth(timestamp.getMonth().name());
            transaction.setPurchaseAmount(finalAmount);
            transaction.setRewardPoint(rewardPoints);

            if (RewardsService.customerPointsMap.containsKey(customerId)) {
                RewardsService.customerPointsMap.get(customerId)
                        .add(transaction);
            } else {
                List<Transaction> pointsList = new ArrayList<>();
                pointsList.add(transaction);
                RewardsService.customerPointsMap.put(customerId, pointsList);
            }
        }
        rewardsService.displayRewardMap();
    }

}
