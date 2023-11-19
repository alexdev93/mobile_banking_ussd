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

    private Long generateRrn(){
            Long lastRrn = transactionRepository.getLastRrn();
            Long nextRrn = (lastRrn == null) ? 1110001110 : lastRrn + 1;
            return nextRrn;
        }

    private Transaction createTransaction(Integer accountNumber, Double amount){
        Transaction transaction = new Transaction();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String callerMethod = stackTraceElements[2].getMethodName();
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        transaction.setAccount(account);
        transaction.setRrn(generateRrn());
        transaction.setAmount(amount);
        transaction.setTransactionType(callerMethod);
        transaction.setResponseCode("SUCCESS");
        return transactionRepository.save(transaction);
    }

    public Transaction deposit(Integer accountNumber, Double amount) {
        Account optionalAccount = accountService.getAccountByAccountNumber(accountNumber);
        if (optionalAccount != null) {
            try{
                Account account = optionalAccount;
                Double currentBalance = account.getBalance();
                Double updatedBalance = currentBalance + amount;
                account.setBalance(updatedBalance);
                accountRepository.save(account);
                return createTransaction(accountNumber, amount);
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new CustomerException("customer might not exist");
    }

    public Transaction withdraw(Integer accountNumber, Double amount) {
       Account optionalAccount = accountService.getAccountByAccountNumber(accountNumber);
        if (optionalAccount != null) {
            Account account = optionalAccount;
            Double currentBalance = account.getBalance();

            if (currentBalance > 0 && currentBalance > amount) {
                Double updatedBalance = currentBalance - amount;
                account.setBalance(updatedBalance);
                accountRepository.save(account);
                return createTransaction(accountNumber, amount);
            }
        }
        return null;
    }

    public Map<String, Transaction> transfer(TransactionRequest transactionRequest){
        Account sender = accountService.getAccountByAccountNumber(transactionRequest.getOwnAccountNumber());
        Account receiver =  accountService.getAccountByAccountNumber(transactionRequest.getRecipientAccountNumber());
        Double amount = transactionRequest.getAmount();
        if (isValidTransfer(sender, receiver, amount)) {
            updateBalances(sender, receiver, amount);
            Map<String, Transaction> result = new HashMap<>();
           Transaction senderHistory = createTransaction(sender.getAccountNumber(), amount);
           Transaction receiverHistory = createTransaction(receiver.getAccountNumber(), amount);
            result.put("Your History: ", senderHistory);
            result.put("receiver History: ", receiverHistory);
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
