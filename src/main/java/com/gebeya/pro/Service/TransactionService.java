package com.gebeya.pro.Service;

import com.gebeya.pro.Controller.CustomerException;
import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.Transaction;
import com.gebeya.pro.Model.TransactionRequest;
import com.gebeya.pro.Repository.AccountRepository;
import com.gebeya.pro.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;

    public Account deposit(Integer accountNumber, Double amount) {
        Account optionalAccount = accountService.getAccountByAccountNumber(accountNumber);
        if (optionalAccount != null) {
            try{
                Account account = optionalAccount;
                Double currentBalance = account.getBalance();
                Double updatedBalance = currentBalance + amount;
                account.setBalance(updatedBalance);
                accountRepository.save(account);
                return account;
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new CustomerException("customer might not exist");
    }

    public Account withdraw(Integer accountNumber, Double amount) {
       Account optionalAccount = accountService.getAccountByAccountNumber(accountNumber);
        if (optionalAccount != null) {
            Account account = optionalAccount;
            Double currentBalance = account.getBalance();

            if (currentBalance > 0 && currentBalance > amount) {
                Double updatedBalance = currentBalance - amount;
                account.setBalance(updatedBalance);
                accountRepository.save(account);
                return account;
            }
        }
        return null;
    }

    public Map<String, Account> transfer(TransactionRequest transactionRequest){
        Account sender = accountService.getAccountByAccountNumber(transactionRequest.getSenderAccountNumber());
        Account receiver =  accountService.getAccountByAccountNumber(transactionRequest.getRecipientAccountNumber());
        Double amount = transactionRequest.getAmount();
        if (isValidTransfer(sender, receiver, amount)) {
            updateBalances(sender, receiver, amount);
            Map<String, Account> result = new HashMap<>();
            result.put("Sender Account: ", sender);
            result.put("receiver Account: ", receiver);
            return result;
        }
        throw new CustomerException("it is not exists");
    }

    private boolean isValidTransfer(Account sender, Account receiver, Double amount) {
        return sender != null && receiver != null && sender.getBalance() >= amount;
    }

    private void updateBalances(Account sender, Account receiver, Double amount) {
        Double senderBalance = sender.getBalance();
        Double receiverBalance = receiver.getBalance();

        sender.setBalance(senderBalance - amount);
        receiver.setBalance(receiverBalance + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }
}
