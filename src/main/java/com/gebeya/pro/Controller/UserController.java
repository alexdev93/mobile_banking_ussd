package com.gebeya.pro.Controller;

import com.gebeya.pro.Model.*;
import com.gebeya.pro.Repository.CustomerRepository;
import com.gebeya.pro.Service.ReactiveApiService;
import com.gebeya.pro.Service.TransactionService;
import com.gebeya.pro.Service.UssdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user/{id}")
public class UserController {
    @Autowired
    private ReactiveApiService reactiveApiService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UssdUserService ussdUserService;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public Optional<UssdUser> getUser(@PathVariable Long id) {
        return ussdUserService.getUserById(id);
    }

    @DeleteMapping
    public String deleteUser(@PathVariable Long id) {
        ussdUserService.deleteUserById(id);
        return "delete successfully";
    }

    @GetMapping("/shortStmt")
    public List<Transaction> returnTransactions(@PathVariable Long id) {
        return transactionService.getRecentTransactions();
    }

    @PostMapping("/topup/{cardAmount}")
    public TopUp topUpApi(@PathVariable Long id, @PathVariable Long cardAmount) {
        return reactiveApiService.topUpFetch(cardAmount);
    }

    @PostMapping("/deposit")
    public Transaction depositFromUser(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        UssdUser user = ussdUserService.getUserById(id).orElse(null);
        Double depositAmount = transactionRequest.getAmount();
        if (user != null) {
            Customer customer = customerRepository.findByCif(user.getCif());
            Account account = ussdUserService.getByCustomer(customer);
            return transactionService.deposit(account.getAccountNumber(), depositAmount);
        }

        return null;
    }

    @PostMapping("withdraw")
    public Transaction withDrawl(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        UssdUser user = ussdUserService.getUserById(id).orElse(null);
        Double depositAmount = transactionRequest.getAmount();
        if (user != null) {
            Customer customer = customerRepository.findByCif(user.getCif());
            Account account = ussdUserService.getByCustomer(customer);
            return transactionService.withdraw(account.getAccountNumber(), depositAmount);
        }

        return null;
    }

    @PostMapping("/transfer")
    public Map<String, Transaction> transferFromUser(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        UssdUser user = ussdUserService.getUserById(id).orElse(null);
        if (user != null) {
            Customer customer = customerRepository.findByCif(user.getCif());
            Account account = ussdUserService.getByCustomer(customer);
            transactionRequest.setOwnAccountNumber(account.getAccountNumber());
            return transactionService.transfer(transactionRequest);
        }
    return null;
    }

    @PostMapping("/merchant/withdraw")
    public Transaction transactionValidatorForWithdraw(@PathVariable Long id, @RequestBody RequestMerchant requestMerchant){
        return transactionService.getSpecificTransaction(requestMerchant, id, "withdraw");
    }

    @PostMapping("/merchant/deposit")
    public Transaction transactionValidatorForDeposit(@PathVariable Long id, @RequestBody RequestMerchant requestMerchant){
        return transactionService.getSpecificTransaction(requestMerchant, id, "deposit");
    }

}