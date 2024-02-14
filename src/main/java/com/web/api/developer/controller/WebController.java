package com.web.api.developer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.api.developer.model.Transaction;
import com.web.api.developer.service.RewardsService;

@RestController
public class WebController {

    @Autowired 
    RewardsService rewardsService;

    @PostMapping("save/customer/{customerId}/{amount}")
    public void saveCustomerPoints(@PathVariable String customerId, @PathVariable double amount) {
        rewardsService.saveCustomerPoints(customerId, amount);
    }

    @GetMapping("/customer/{customerId}")
    public Map<String, List<Transaction>> getCustomerPointsById(@PathVariable String customerId) {
        return rewardsService.getCustomerPointsByID(customerId);
    }

    @GetMapping("/reward/{customerId}/{month}")
    public List<Transaction> getCustomerPoints(@PathVariable String customerId, @PathVariable String month) {
        return rewardsService.getCustomerPoints(customerId, month);
    }

    @GetMapping("/customers/{month}")
    public Map<String, List<Transaction>> getAllCustomerPoints(@PathVariable String month) {
        return rewardsService.getAllCustomerPoints(month);
    }

    @DeleteMapping("delete/customers")
    public void deleteAllCustomerPoints() {
        rewardsService.deleteAllCustomerPoints();
    }

    @DeleteMapping("delete/customer/{customerId}")
    public void deleteCustomerPointsById(@PathVariable String customerId) {
        rewardsService.deleteCustomerPointsByID(customerId);
    }

    @GetMapping("/customers/lastThreeMonths")
    public Map<String, List<Transaction>> getLastThreeMonthsData() {

        return rewardsService.getLastThreeMonthsData();
    }
    
}
