package com.gebeya.pro.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.DepositRequest;
import com.gebeya.pro.Model.TransactionRequest;
import com.gebeya.pro.Model.WithdrawalRequest;
import com.gebeya.pro.Service.AccountService;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest depositRequest) {
        Long accountId = depositRequest.getAccountId();
        double amount = depositRequest.getAmount();

        boolean depositSuccessful = accountService.deposit(accountId, amount);

        if (depositSuccessful) {
            return ResponseEntity.ok("Deposit successful!");
        } else {
            return ResponseEntity.badRequest().body("Failed to deposit. Account not found.");
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawalRequest withdrawalRequest) {

        Long accountId = withdrawalRequest.getAccountId();
        double amount = withdrawalRequest.getAmount();

        boolean withdrawalSuccess = accountService.withdraw(accountId, amount);

        if (withdrawalSuccess) {
            return ResponseEntity.ok("Withdrawal successful!");
        } else {
            return ResponseEntity.badRequest().body("Failed to withdraw. Insufficient balance or account not found.");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<Account> transferAccToAcc(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(accountService.getAccountByAccountNumber(transactionRequest.getSenderAccountNumber()));
        // Account sender =
        // accountService.findById(transactionRequest.getSenderAccountNumber()).orElse(null);
        // Account recipient =
        // accountRepository.getAccountId(transactionRequest.getRecipientAccountNumber()).orElse(null);

    }
}
