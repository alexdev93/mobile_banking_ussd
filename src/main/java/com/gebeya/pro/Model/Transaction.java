package com.gebeya.pro.Model;

import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    public Transaction(Integer rrn, Long transactionCode, Account account, String transactionType, Double amount){
        this.rrn = rrn;
        this.transactionCode = transactionCode;
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rrn;

    @Column(name = "transaction_code")
    private Long transactionCode;

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
    private LocalTime transactionDate;


    public Integer getRrn() {
        return rrn;
    }

    public void setRrn(Integer rrn) {
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

    public LocalTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(Long transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
