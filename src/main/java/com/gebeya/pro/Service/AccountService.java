package com.gebeya.pro.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.Customer;
import com.gebeya.pro.Repository.AccountRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    @Transactional
    public void createAccount(Customer customer) {
        try {
            Account account = new Account(customer);
            accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create account: " + e.getMessage());
        }
    }

    public Account getAccountByAccountNumber(Integer accountNumber){
        return accountRepository.findByAccountNumber(accountNumber).orElse(null);
    }

    public Account getByCustomer(Customer customer){
        return accountRepository.findByCustomer(customer).orElse(null);
    }

}
