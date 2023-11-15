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
    public ResponseEntity<String> deposit(@RequestBody DepositRequest depositRequest) {

        Integer accounNumber = depositRequest.getAccountNumber();
        double amount = depositRequest.getAmount();

        boolean depositSuccessful = transactionService.deposit(accounNumber, amount);

        if (depositSuccessful) {
            return ResponseEntity.ok("Deposit successful!");
        } else {
            return ResponseEntity.badRequest().body("Failed to deposit. Account not found.");
        }

    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawalRequest withdrawalRequest) {

        Integer accountNumber = withdrawalRequest.getAccountNumber();
        double amount = withdrawalRequest.getAmount();

        boolean withdrawalSuccess = transactionService.withdraw(accountNumber, amount);

        if (withdrawalSuccess) {
            return ResponseEntity.ok("Withdrawal successful!");
        } else {
            return ResponseEntity.badRequest().body("Failed to withdraw. Insufficient balance or account not found.");
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<Map<String, Account>> processTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.transfer(transactionRequest));
    }


}
