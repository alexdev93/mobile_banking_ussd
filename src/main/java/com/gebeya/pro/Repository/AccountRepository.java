package com.gebeya.pro.Repository;

import java.util.Optional;

import com.gebeya.pro.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gebeya.pro.Model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Optional<Account> findByAccountNumber(Integer accountNumber);

    public  Account findByCustomer(Customer customer);


}
