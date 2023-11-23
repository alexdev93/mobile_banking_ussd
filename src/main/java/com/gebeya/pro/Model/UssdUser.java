package com.gebeya.pro.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "mobile_user")
public class UssdUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "profile")
    private String Profile;

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

    public int getCif() {
        return customer.getCif();
    }

}
