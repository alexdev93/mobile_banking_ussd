package com.gebeya.pro.Controller;

import com.gebeya.pro.Model.TopUp;
import com.gebeya.pro.Model.Transaction;
import com.gebeya.pro.Model.UssdUser;
import com.gebeya.pro.Service.ReactiveApiService;
import com.gebeya.pro.Service.TransactionService;
import com.gebeya.pro.Service.UssdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping
    public Optional<UssdUser> getUser(@PathVariable Long id){
        return ussdUserService.getUserById(id);
    }

    @DeleteMapping
    public String deleteUser(@PathVariable Long id){
        ussdUserService.deleteUserById(id);
        return "delete successfully";
    }

    @GetMapping("/shortStmt")
    public List<Transaction> returnTransactions(@PathVariable Long id){
        return transactionService.getRecentTransactions();
    }

    @PostMapping("/topup/{cardAmount}")
    public TopUp topUpApi(@PathVariable Long id, @PathVariable Long cardAmount){
        return reactiveApiService.topUpFetch(cardAmount);
    }

}
