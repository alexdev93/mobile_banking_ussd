package com.gebeya.pro.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class TransactionRequest {
    @JsonProperty("owner")
    private int ownAccountNumber;

    public int getOwnAccountNumber() {
        return ownAccountNumber;
    }

    @JsonProperty("receiver")
    private int recipientAccountNumber;

    public int getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    @JsonProperty("amount")
    private double amount;

    public double getAmount() {
        return amount;
    }

}
