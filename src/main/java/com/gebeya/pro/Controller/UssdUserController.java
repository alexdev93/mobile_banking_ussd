package com.gebeya.pro.Controller;

import com.gebeya.pro.Model.UssdUser;
import com.gebeya.pro.Service.ReactiveApiService;
import com.gebeya.pro.Service.UssdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UssdUserController {
    @Autowired
    private UssdUserService ussdUserService;

    @Autowired
    private ReactiveApiService reactiveApiService;

    @PostMapping("/register")
    public UssdUser registering(@RequestBody UssdUser ussdUser){
        return ussdUserService.registerUser(ussdUser);
    }

    @GetMapping
    public List<UssdUser> getUsers(){
        return ussdUserService.findAllUser();
    }



}
