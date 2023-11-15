package com.gebeya.pro.Controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Service.AccountService;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        Optional<Account> existAcc = accountService.getById(id);
        return existAcc.orElse(null);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> queryBalance(@PathVariable Long id) {
        Optional<Account> thisAccount = accountService.getById(id);
        if (thisAccount.isPresent()) {
            Account account = thisAccount.get();
            return ResponseEntity.ok(account.getBalance());
        }
        throw new RuntimeException("this account did not exist");
    }


}
