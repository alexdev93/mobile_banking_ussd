package com.gebeya.pro.Repository;

import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByPhoneNumber(String phoneNumber);
}
