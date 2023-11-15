package com.gebeya.pro.Model;

import org.springframework.stereotype.Component;

@Component
public class WithdrawalRequest {
    private Integer accountNumber;

    public Integer getAccountNumber() {
        return accountNumber;
    }

    private double amount;

    public double getAmount() {
        return amount;
    }
    // private String token;
    // public String getToken() {
    // return token;
    // }

}
