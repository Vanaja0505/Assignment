package com.web.api.developer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web.api.developer.model.Transaction;
import com.web.api.developer.service.RewardsService;

class WebControllerTest {

    private WebController webController;
    private RewardsService rewardsServiceMock;

    @BeforeEach
    void setUp() {
        rewardsServiceMock = mock(RewardsService.class);
        webController = new WebController();
        webController.rewardsService = rewardsServiceMock;
    }

    @Test
    void testSaveCustomerPoints() {
        String customerId = "customer1";
        double amount = 150.0;

        webController.saveCustomerPoints(customerId, amount);

        verify(rewardsServiceMock, times(1)).saveCustomerPoints(customerId, amount);
    }

    @Test
    void testGetCustomerPointsById() {
        String customerId = "customer1";
        Map<String, List<Transaction>> expectedMap = createMockTransactionMap();

        when(rewardsServiceMock.getCustomerPointsByID(customerId)).thenReturn(expectedMap);

        Map<String, List<Transaction>> result = webController.getCustomerPointsById(customerId);

        assertEquals(expectedMap, result);
    }

    @Test
    void testGetCustomerPoints() {
        String customerId = "customer1";
        String month = "Jan";
        List<Transaction> expectedTransactions = createMockTransactions();

        when(rewardsServiceMock.getCustomerPoints(customerId, month)).thenReturn(expectedTransactions);

        List<Transaction> result = webController.getCustomerPoints(customerId, month);

        assertEquals(expectedTransactions, result);
    }

    @Test
    void testGetAllCustomerPoints() {
        String month = "Jan";
        Map<String, List<Transaction>> expectedMap = createMockTransactionMap();

        when(rewardsServiceMock.getAllCustomerPoints(month)).thenReturn(expectedMap);

        Map<String, List<Transaction>> result = webController.getAllCustomerPoints(month);

        assertEquals(expectedMap, result);
    }

    @Test
    void testDeleteAllCustomerPoints() {
        webController.deleteAllCustomerPoints();

        verify(rewardsServiceMock, times(1)).deleteAllCustomerPoints();
    }

    @Test
    void testDeleteCustomerPointsById() {
        String customerId = "customer1";

        webController.deleteCustomerPointsById(customerId);

        verify(rewardsServiceMock, times(1)).deleteCustomerPointsByID(customerId);
    }

    @Test
    void testGetLastThreeMonthsData() {
        Map<String, List<Transaction>> expectedMap = createMockTransactionMap();

        when(rewardsServiceMock.getLastThreeMonthsData()).thenReturn(expectedMap);

        Map<String, List<Transaction>> result = webController.getLastThreeMonthsData();

        assertEquals(expectedMap, result);
    }

    private List<Transaction> createMockTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(createMockTransaction("Jan", 150.0));
        return transactions;
    }

    private Map<String, List<Transaction>> createMockTransactionMap() {
        return Map.of("customer1", createMockTransactions());
    }

    private Transaction createMockTransaction(String month, double amount) {
        Transaction transaction = new Transaction();
        transaction.setMonth(month);
        transaction.setPurchaseAmount(amount);
        transaction.setRewardPoint(50);
        return transaction;
    }
}

