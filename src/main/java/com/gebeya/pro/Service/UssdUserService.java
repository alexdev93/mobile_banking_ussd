package com.gebeya.pro.Service;

import com.gebeya.pro.Controller.CustomerException;
import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.Customer;
import com.gebeya.pro.Model.UssdUser;
import com.gebeya.pro.Repository.UssdUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UssdUserService {
    @Autowired
       private CustomerService customerService;
    @Autowired
        private AccountService accountService;
    @Autowired
       private UssdUserRepository ussdUserRepository;

    @Transactional
    public UssdUser registerUser(UssdUser ussdUser){
        String phoneNUmber = ussdUser.getPhoneNumber();
        Customer existCustomer = customerService.getCustomerByPhoneNumber(phoneNUmber);
        Account existAccount = accountService.getByCustomer(existCustomer);
        String poNumber = existCustomer.getPhoneNumber();
        if (poNumber.equals(phoneNUmber) && ussdUserRepository.findByPhoneNumber(phoneNUmber) != null){
            ussdUser.setCustomer(existCustomer);
            ussdUser.setBalance(existAccount.getBalance());
            return ussdUserRepository.save(ussdUser);
        }
        throw new CustomerException("account not found");
    }

    @Transactional
    public List<UssdUser> findAllUser(){
        return ussdUserRepository.findAll();
    }

    @Transactional
    public void deleteUserById(Long id){
        ussdUserRepository.deleteById(id);
    }

    @Transactional
    public Optional<UssdUser> getUserById(Long id){
        return ussdUserRepository.findById(id);
    }


}
