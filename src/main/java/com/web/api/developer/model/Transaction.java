package com.web.api.developer.model;

public class Transaction {
    private double purchaseAmount;
    private String month;
    private int rewardPoint;

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    @Override
    public String toString() {
        return "Transaction [purchaseAmount=" + purchaseAmount + ", month=" + month
                + ", rewardPoint=" + rewardPoint + "]";
    }

    public Transaction(double purchaseAmount, String month, int rewardPoint) {
        super();
        this.purchaseAmount = purchaseAmount;
        this.month = month;
        this.rewardPoint = rewardPoint;
    }

    public Transaction() {
        super();
    }

}
