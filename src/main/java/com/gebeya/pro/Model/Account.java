package com.gebeya.pro.Model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number")
    private Integer accountNumber;

    private Double balance;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "cif", referencedColumnName = "cif")
    private Customer customer;

    public Account() {
    }

    public Account(Customer customer) {
        this.customer = customer;
        this.balance = 50.00;
    }

//    public void setBalance(Double updatedBalance) {
//        this.balance = updatedBalance;
//    }
//
//    public Double getBalance() {
//        return balance;
//    }
//
//    public Integer getAccountNumber() {
//        return accountNumber;
//    }

//    public int getCustomer() {
//        return customer.getCif();
//    }
}
