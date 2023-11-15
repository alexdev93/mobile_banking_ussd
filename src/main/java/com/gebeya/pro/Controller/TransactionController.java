package com.gebeya.pro.Controller;

import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.TransactionRequest;
import com.gebeya.pro.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gebeya.pro.Model.DepositRequest;
import com.gebeya.pro.Model.WithdrawalRequest;

import java.util.Map;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRequest transactionRequest;

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestBody DepositRequest depositRequest) {

        Integer accountNumber = depositRequest.getAccountNumber();
        double amount = depositRequest.getAmount();

        Account depositSuccessful = transactionService.deposit(accountNumber, amount);

        if (depositSuccessful != null) {
            return ResponseEntity.ok(depositSuccessful);
        }
 throw new CustomerException("Failed to deposit. Account not found.");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestBody WithdrawalRequest withdrawalRequest) {

        Integer accountNumber = withdrawalRequest.getAccountNumber();
        double amount = withdrawalRequest.getAmount();

        Account withdrawnAccounts = transactionService.withdraw(accountNumber, amount);

        if (withdrawnAccounts != null) {
            return ResponseEntity.ok(withdrawnAccounts);
        }
        throw new CustomerException("Customer might not exist");
    }

    @PostMapping("/transaction")
    public ResponseEntity<Map<String, Account>> processTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.transfer(transactionRequest));
    }


}
