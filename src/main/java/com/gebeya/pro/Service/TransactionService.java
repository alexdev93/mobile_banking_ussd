package com.gebeya.pro.Service;

import com.gebeya.pro.Controller.CustomerException;
import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.Transaction;
import com.gebeya.pro.Model.TransactionRequest;
import com.gebeya.pro.Repository.AccountRepository;
import com.gebeya.pro.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;


    private Transaction createTransaction(Integer accountNumber, String side, Double amount, String transactionCode){
        Transaction transaction = new Transaction();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement callingMethod  = stackTrace[2];
        String transactionType = callingMethod.getMethodName();
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setSide(side);
        transaction.setTransactionType(transactionType);
        transaction.setResponseCode("SUCCESS");
        transaction.setTransactionCode(transactionCode);
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
                return createTransaction(accountNumber, "CREDIT", amount, "DEP001");
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
                return createTransaction(accountNumber,"DEBIT", -amount, "WDL002");
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
           Transaction senderHistory = createTransaction(sender.getAccountNumber(), "SENDER", -amount, "TRF003");
           Transaction receiverHistory = createTransaction(sender.getAccountNumber(), "RECEIVER", amount, "TRF003");
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
