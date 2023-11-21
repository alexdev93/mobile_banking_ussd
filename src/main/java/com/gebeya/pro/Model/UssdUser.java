package com.gebeya.pro.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "mobile_user")
public class UssdUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToOne
    @JoinColumn(name = "cif", referencedColumnName = "cif", insertable = true, updatable = false)
    private Customer customer;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "pin", nullable = false)
    private int pin;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "balance")
    private Double balance;

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPIN() {
        return pin;
    }

    public void setPIN(int PIN) {
        this.pin = pin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCif() {
        return customer.getCif();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
