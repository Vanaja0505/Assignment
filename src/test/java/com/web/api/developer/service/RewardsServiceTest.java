package com.web.api.developer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web.api.developer.model.Transaction;

class RewardsServiceTest {

    private RewardsService rewardsService;

    @BeforeEach
    void setUp() {
        rewardsService = new RewardsService();
    }

    @Test
    void testGetCustomerPoints() {
        String customerId = "customer1";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(createTransaction("Jan", 150.0));
        rewardsService.getCustomerpointsmap().put(customerId, transactions);

        List<Transaction> result = rewardsService.getCustomerPoints(customerId, "Jan");

        assertEquals(transactions, result);
    }

    @Test
    void testGetAllCustomerPoints() {
        String month = "Jan";
        String customerId1 = "customer1";
        String customerId2 = "customer2";

        List<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(createTransaction("Jan", 150.0));

        List<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(createTransaction("Jan", 200.0));

        rewardsService.getCustomerpointsmap().put(customerId1, transactions1);
        rewardsService.getCustomerpointsmap().put(customerId2, transactions2);

        Map<String, List<Transaction>> result = rewardsService.getAllCustomerPoints(month);

        assertTrue(result.containsKey(customerId1));
        assertTrue(result.containsKey(customerId2));
        assertEquals(transactions1, result.get(customerId1));
        assertEquals(transactions2, result.get(customerId2));
    }

    @Test
    void testDeleteAllCustomerPoints() {
        String customerId = "customer1";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(createTransaction("Jan", 150.0));
        rewardsService.getCustomerpointsmap().put(customerId, transactions);

        rewardsService.deleteAllCustomerPoints();

        assertTrue(rewardsService.getCustomerpointsmap().isEmpty());
    }

    @Test
    void testGetLastThreeMonthsData() {
        String customerId = "customer1";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(createTransaction("Jan", 150.0));
        transactions.add(createTransaction("Feb", 200.0));
        transactions.add(createTransaction("Mar", 100.0));
        rewardsService.getCustomerpointsmap().put(customerId, transactions);

        Map<String, List<Transaction>> result = rewardsService.getLastThreeMonthsData();
    }

    @Test
    void testCalculateRewardPointsForTransaction() {
        assertEquals(150, rewardsService.calculateRewardPointsForTransaction(150.0));
        assertEquals(50, rewardsService.calculateRewardPointsForTransaction(100.0));
        assertEquals(0, rewardsService.calculateRewardPointsForTransaction(50.0));
    }

    @Test
    void testSaveCustomerPoints() {
        String customerId = "customer1";
        double amount = 150.0;

        rewardsService.saveCustomerPoints(customerId, amount);

        assertTrue(rewardsService.getCustomerpointsmap().containsKey(customerId));
    }

    @Test
    void testDisplayRewardMap() {
        
        String customerId = "customer1";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(createTransaction("Jan", 150.0));
        rewardsService.getCustomerpointsmap().put(customerId, transactions);

        // Redirect System.out to check the content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        rewardsService.displayRewardMap();

        // Reset System.out
        System.setOut(System.out);

        String expectedOutput = "Reward Points for customer1:- \n150 for Jan\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testGetCustomerPointsByID() {
        String customerId1 = "customer1";
        String customerId2 = "customer2";

        List<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(createTransaction("Jan", 150.0));

        List<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(createTransaction("Feb", 200.0));

        rewardsService.getCustomerpointsmap().put(customerId1, transactions1);
        rewardsService.getCustomerpointsmap().put(customerId2, transactions2);

        Map<String, List<Transaction>> result = rewardsService.getCustomerPointsByID("customer");

        assertTrue(result.containsKey(customerId1));
        assertTrue(result.containsKey(customerId2));
        assertEquals(transactions1, result.get(customerId1));
        assertEquals(transactions2, result.get(customerId2));
    }

    @Test
    void testDeleteCustomerPointsByID() {
        String customerId1 = "customer1";
        String customerId2 = "customer2";

        List<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(createTransaction("Jan", 150.0));

        List<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(createTransaction("Feb", 200.0));

        rewardsService.getCustomerpointsmap().put(customerId1, transactions1);
        rewardsService.getCustomerpointsmap().put(customerId2, transactions2);

        rewardsService.deleteCustomerPointsByID("customer");

        assertTrue(rewardsService.getCustomerpointsmap().isEmpty());
    }

    private Transaction createTransaction(String month, double amount) {
        Transaction transaction = new Transaction();
        transaction.setMonth(month);
        transaction.setPurchaseAmount(amount);
        transaction.setRewardPoint(rewardsService.calculateRewardPointsForTransaction(amount));
        return transaction;
    }
}


