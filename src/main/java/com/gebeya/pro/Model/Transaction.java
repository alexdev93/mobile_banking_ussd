package com.gebeya.pro.Model;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rrn;

    private Long transactionCode;

    @OneToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_number", insertable = true, updatable = false)
    private Account account;

    private String transactionType;

    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    private String responseCode;
    private Timestamp transactionDate;

}
