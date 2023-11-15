package com.gebeya.pro.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class TransactionRequest {
    @JsonProperty("sender_account_number")
    private int senderAccountNumber;

    public int getSenderAccountNumber() {
        return senderAccountNumber;
    }

    @JsonProperty("recipient_account_number")
    private int recipientAccountNumber;

    public int getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    private double amount;

    public double getAmount() {
        return amount;
    }

}
