package com.gebeya.pro.Model;

import org.springframework.stereotype.Component;

@Component
public class DepositRequest {

    private Integer accountNumber;
    public Integer getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }
    private double amount;
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
