package com.gebeya.pro.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    public Transaction(){
        this.transactionDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rrn;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "side")
    private String side;

    @ManyToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private Account account;


    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "otp")
    private int otp;

    @Column(name = "status")
    private String status;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;


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

    public Integer getAccount() {
        return account.getAccountNumber();
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

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
