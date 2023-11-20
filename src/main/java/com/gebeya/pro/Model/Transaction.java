package com.gebeya.pro.Model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    public Transaction(){}

    @Id
    @Column(name = "rrn")
    private Long rrn;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "side")
    private String side;

    @OneToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private Account account;


    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "amount")
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;


    public Long getRrn() {
        return rrn;
    }

    public void setRrn(Long rrn) {
        this.rrn = rrn;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
