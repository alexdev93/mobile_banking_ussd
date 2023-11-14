package com.gebeya.pro.Model;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "account_number", unique = true)
    private Integer accountNumber;

    private Double balance;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToOne
    @JoinColumn(name = "cif", referencedColumnName = "cif", insertable = true, updatable = false)
    private Customer customer;

    public Account() {
    }

    public Account(Customer customer, Integer accountNumber, Double d) {
        this.customer = customer;
        this.accountNumber = accountNumber;
        this.balance = d;
    }

    public Long getId() {
        return accountId;
    }

    public void setBalance(Double updatedBalance) {
        this.balance = updatedBalance;
    }

    public Double getBalance() {
        return balance;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }
}
