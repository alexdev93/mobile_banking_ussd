package com.gebeya.pro.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class RequestMerchant {

    @JsonProperty("accountNumber")
    private Integer accountNumber;

    @JsonProperty("otp")
    private int otp;

    @JsonProperty("amount")
    private Double amount;

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
