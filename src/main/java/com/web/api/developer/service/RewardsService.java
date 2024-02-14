package com.web.api.developer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.web.api.developer.model.Transaction;

@Service
public class RewardsService {

    public static Map<String, List<Transaction>> customerPointsMap = new HashMap<>();

    public List<Transaction> getCustomerPoints(String customerId, String month) {
        return customerPointsMap.get(customerId);
    }

    public Map<String, List<Transaction>> getAllCustomerPoints(String month) {
        Map<String, List<Transaction>> result = new HashMap<>();
        Set<Entry<String, List<Transaction>>> entrySet = customerPointsMap.entrySet();

        for (Entry<String, List<Transaction>> entry : entrySet) {
            List<Transaction> list = new ArrayList<>();
            for (Transaction transaction : entry.getValue()) {
                if (transaction.getMonth().equalsIgnoreCase(month)) {
                    list.add(transaction);
                }
            }
            if (!list.isEmpty())
                result.put(entry.getKey(), list);
        }

        return result;
    }

    public void deleteAllCustomerPoints() {
        customerPointsMap.clear();
    }

    public Map<String, List<Transaction>> getLastThreeMonthsData() {
        LocalDateTime now = LocalDateTime.now();
        String currentMonth = now.getMonth().name();
        String lastMonth = now.minusMonths(1).getMonth().name();
        String twoMonthsAgo = now.minusMonths(2).getMonth().name();

        Map<String, List<Transaction>> result = new HashMap<>();
        Set<Entry<String, List<Transaction>>> entrySet = customerPointsMap.entrySet();

        for (Entry<String, List<Transaction>> entry : entrySet) {
            List<Transaction> list = new ArrayList<>();
            for (Transaction transaction : entry.getValue()) {
                if (transaction.getMonth().equalsIgnoreCase(currentMonth)
                        || transaction.getMonth().equalsIgnoreCase(lastMonth)
                        || transaction.getMonth().equalsIgnoreCase(twoMonthsAgo)) {
                    list.add(transaction);
                }
            }
            if (!list.isEmpty())
                result.put(entry.getKey(), list);
        }

        return result;

    }

    public int calculateRewardPointsForTransaction(double amount) {
        int points = 0;
        if (amount > 100) {
            points += 2 * (int) (amount - 100);
        }
        if (amount > 50 && amount >100) {
            points += (int) (amount - 50 - (amount - 100));
        }
        if (amount > 50 && amount <=100) {
            points += (int) (amount - 50);
        }

        return points;
    }

    public void saveCustomerPoints(String customerId, double amount) {
        LocalDateTime timestamp = LocalDateTime.now();
        long finalAmount = Math.round((amount * 100D) / 100D);
        int rewardPoint = calculateRewardPointsForTransaction(finalAmount);

        List<Transaction> list = new ArrayList<>();
        Transaction transaction = new Transaction();

        transaction.setMonth(timestamp.getMonth().name());
        transaction.setPurchaseAmount(finalAmount);
        transaction.setRewardPoint(rewardPoint);

        if (customerPointsMap.containsKey(customerId)) {
            list = customerPointsMap.get(customerId);
        }

        list.add(transaction);
        customerPointsMap.put(customerId, list);

        displayRewardMap();

    }

    public void displayRewardMap() {
        for (Map.Entry<String, List<Transaction>> entry : customerPointsMap.entrySet()) {
            String key = entry.getKey();
            List<Transaction> rewardPoints = entry.getValue();

            System.out.println("Reward Points for " + key + ":- ");

            for (Transaction transaction : rewardPoints) {
                System.out.println(transaction.getRewardPoint() + " for " + transaction.getMonth());
            }
        }
    }

    public static Map<String, List<Transaction>> getCustomerpointsmap() {
        return customerPointsMap;
    }

    public Map<String, List<Transaction>> getCustomerPointsByID(String customerId) {

        return customerPointsMap.entrySet().stream().filter(entry -> entry.getKey().startsWith(customerId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public void deleteCustomerPointsByID(String customerId) {
        customerPointsMap.entrySet().removeIf(entry -> entry.getKey().startsWith(customerId));
    }

}
