package com.gebeya.pro.Controller;

import com.gebeya.pro.Model.*;
import com.gebeya.pro.Repository.CustomerRepository;
import com.gebeya.pro.Service.AccountService;
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
@RequestMapping("/api/v2/users")

public class UserController {
    @Autowired
    private ReactiveApiService reactiveApiService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UssdUserService ussdUserService;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;

    @PostMapping
    public UssdUser registering(@RequestBody UssdUser ussdUser){
        return ussdUserService.registerUser(ussdUser);
    }

    @GetMapping
    public List<UssdUser> getUsers(){
        return ussdUserService.findAllUser();
    }

    @GetMapping("/{id}")
    public Optional<UssdUser> getUser(@PathVariable Long id) {
        return ussdUserService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        ussdUserService.deleteUserById(id);
        return "delete successfully";
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> queryBalance(@PathVariable Long id) {
        Optional<UssdUser> thisAccount = ussdUserService.getUserById(id);
        if (thisAccount.isPresent()) {
            UssdUser user = thisAccount.get();
            return ResponseEntity.ok(user.getBalance());
        }
        throw new RuntimeException("this account does not exist");
    }

    @GetMapping("/{id}/shortStmt")
    public List<Transaction> returnTransactions(@PathVariable Long id) {
        return transactionService.getRecentTransactions();
    }

//    @PostMapping("{id}/topup/{cardAmount}")
//    public TopUp topUpApi(@PathVariable Long id, @PathVariable Long cardAmount) {
//        return reactiveApiService.topUpFetch(cardAmount);
//    }

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

    @PostMapping("/merchant-withdraw")
    public Transaction transactionValidatorForWithdraw(@PathVariable Long id, @RequestBody RequestMerchant requestMerchant){
        return transactionService.getSpecificTransaction(requestMerchant, id, "withdraw");
    }

    @PostMapping("/merchant-deposit")
    public Transaction transactionValidatorForDeposit(@PathVariable Long id, @RequestBody RequestMerchant requestMerchant){
        return transactionService.getSpecificTransaction(requestMerchant, id, "deposit");
    }

}