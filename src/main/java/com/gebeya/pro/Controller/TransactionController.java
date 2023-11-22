package com.gebeya.pro.Controller;

import com.gebeya.pro.Model.*;
import com.gebeya.pro.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRequest transactionRequest;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody TransactionRequest depositRequest) {

        Integer accountNumber = depositRequest.getOwnAccountNumber();
        double amount = depositRequest.getAmount();

        try{
            Transaction transaction = transactionService.deposit(accountNumber, amount);
            return ResponseEntity.ok(transaction);
        }catch (Exception e){
            throw new CustomerException(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody TransactionRequest withdrawalRequest) {

        Integer accountNumber = withdrawalRequest.getOwnAccountNumber();
        double amount = withdrawalRequest.getAmount();
        try{
            Transaction transaction = transactionService.withdraw(accountNumber, amount);
            return ResponseEntity.ok(transaction);
        }catch (Exception e){
         throw new CustomerException(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Transaction>> processTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.transfer(transactionRequest));
    }


}
