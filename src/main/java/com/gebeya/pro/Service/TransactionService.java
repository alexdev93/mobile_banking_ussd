package com.gebeya.pro.Service;

import com.gebeya.pro.Controller.CustomerException;
import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.Transaction;
import com.gebeya.pro.Model.TransactionRequest;
import com.gebeya.pro.Repository.AccountRepository;
import com.gebeya.pro.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;


    private Transaction createTransaction(Integer accountNumber, String side, Double amount, String transactionCode) {
        Transaction transaction = new Transaction();
        String transactionType = determineTransactionType();

        if (isDepositOrWithdraw(transactionType)) {
            initializePendingTransaction(transaction);
        }

        Account account = accountService.getAccountByAccountNumber(accountNumber);
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setSide(side);
        transaction.setTransactionType(transactionType);
        transaction.setResponseCode("SUCCESS");
        transaction.setTransactionCode(transactionCode);

        return transactionRepository.save(transaction);
    }

    private String determineTransactionType() {
        StackTraceElement callingMethod = Thread.currentThread().getStackTrace()[2];
        return callingMethod.getMethodName();
    }

    private boolean isDepositOrWithdraw(String transactionType) {
        return "deposit".equals(transactionType) || "withdraw".equals(transactionType);
    }

    private void initializePendingTransaction(Transaction transaction) {
        transaction.setOtp(generateOTP());
        transaction.setStatus("PENDING");
    }

    public Transaction deposit(Integer accountNumber, Double amount) {
        Account optionalAccount = accountService.getAccountByAccountNumber(accountNumber);
        if (optionalAccount != null) {
            try{
                Account account = optionalAccount;
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
                return createTransaction(accountNumber,"DEBIT", -amount, "WDL002");
            }
        }
        return null;
    }

    public Map<String, Transaction> transfer(TransactionRequest transactionRequest) {
        Account sender = accountService.getAccountByAccountNumber(transactionRequest.getOwnAccountNumber());
        Account receiver = accountService.getAccountByAccountNumber(transactionRequest.getRecipientAccountNumber());
        Double amount = transactionRequest.getAmount();

        if (isValidTransfer(sender, receiver, amount)) {
            updateBalances(sender, receiver, amount);

            Transaction senderHistory = createTransaction(sender.getAccountNumber(), "SENDER", -amount, "TRF003");
            Transaction receiverHistory = createTransaction(receiver.getAccountNumber(), "RECEIVER", amount, "TRF003");

            Map<String, Transaction> result = new HashMap<>();
            result.put("Your History: ", senderHistory);
            result.put("Receiver History: ", receiverHistory);

            return result;
        }

        throw new CustomerException("Invalid transfer");
    }

    public List<Transaction> getRecentTransactions() {
        return transactionRepository.findTop5ByOrderByTransactionDateDesc();
    }


    private boolean isValidTransfer(Account sender, Account receiver, Double amount) {
        return sender != null && receiver != null && sender.getBalance() >= amount;
    }

    private void updateBalances(Account sender, Account receiver, Double amount) {
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.saveAll(List.of(sender, receiver));
    }

    private int generateOTP() {
        return new SecureRandom().nextInt(900000) + 100000;
    }

    private boolean isOTPValid(LocalDateTime creationDateTime) {
        return Duration.between(creationDateTime, LocalDateTime.now()).toMinutes() <= 30;
    }

}
