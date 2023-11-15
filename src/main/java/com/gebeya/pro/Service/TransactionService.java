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
    private TransactionRepository transactionReository;
    @Autowired
    private AccountService accountService;

    public boolean deposit(Integer accountNumber, Double amount) {
        Account optionalAccount = accountService.getAccountByAccountNumber(accountNumber);
        if (optionalAccount != null) {
            try{
                Account account = optionalAccount;
        Transaction transaction = new Transaction(123456789, 52L, account, "deposit", amount);
                LocalTime time = LocalTime.now();
                Double currentBalance = account.getBalance();
                Double updatedBalance = currentBalance + amount;
                account.setBalance(updatedBalance);
                transaction.setTransactionDate(time);
                transaction.setTransactionDate(time);
                accountRepository.save(account);
                transaction.setResponseCode("accept");
                transactionReository.save(transaction);
                return true;
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
       return false;
    }

    public boolean withdraw(Integer accountNumber, Double amount) {
       Account optionalAccount = accountService.getAccountByAccountNumber(accountNumber);
        if (optionalAccount != null) {
            Account account = optionalAccount;
            Double currentBalance = account.getBalance();

            if (currentBalance > 0 && currentBalance > amount) {
                Double updatedBalance = currentBalance - amount;
                account.setBalance(updatedBalance);
                accountRepository.save(account);
                return true;
            }
        }
        return false;
    }

    public Map<String, Account> transfer(TransactionRequest transactionRequest){
        Account sender = accountService.getAccountByAccountNumber(transactionRequest.getSenderAccountNumber());
        Account receiver =  accountService.getAccountByAccountNumber(transactionRequest.getRecipientAccountNumber());
        Double amount = transactionRequest.getAmount();
        if (isValidTransfer(sender, receiver, amount)) {
            updateBalances(sender, receiver, amount);
            Map<String, Account> result = new HashMap<>();
            result.put("Sender Account: ", sender);
            result.put("reciever Account: ", receiver);
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
