package com.gebeya.pro.Controller;

import com.gebeya.pro.Model.UssdUser;
import com.gebeya.pro.Repository.UssdUserRepository;
import com.gebeya.pro.Service.AccountService;
import com.gebeya.pro.Service.CustomerService;
import com.gebeya.pro.Service.UssdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UssdUserController {
    @Autowired
    private UssdUserService ussdUserService;

    @PostMapping("/register")
    public UssdUser registering(@RequestBody UssdUser ussdUser){
        return ussdUserService.registerUser(ussdUser);
    }

    @GetMapping
    public List<UssdUser> getUser(){
        return ussdUserService.findAllUser();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        ussdUserService.deleteUserById(id);
        return "delete successfully";
    }

}
