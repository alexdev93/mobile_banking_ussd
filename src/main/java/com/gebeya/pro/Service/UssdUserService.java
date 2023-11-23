package com.gebeya.pro.Service;

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
public class UssdUserService extends AccountService{
    @Autowired
       private CustomerService customerService;
    @Autowired
        private AccountService accountService;
    @Autowired
       private UssdUserRepository ussdUserRepository;

    @Transactional
    public UssdUser registerUser(UssdUser ussdUser) {
        String phoneNumber = ussdUser.getPhoneNumber();

        if (ussdUserRepository.findByPhoneNumber(phoneNumber) != null) {
            throw new RuntimeException("User with this phone number already exists");
        }

        Customer existingCustomer = customerService.getCustomerByPhoneNumber(phoneNumber);
        if (existingCustomer == null) {
            throw new RuntimeException("User doesn't have a bank account");
        }

        Account existingAccount = accountService.getByCustomer(existingCustomer);
        if (existingAccount == null) {
            throw new RuntimeException("This user doesn't have a bank account");
        }

        // Check if the phone number matches the customer's phone number
        if (!existingCustomer.getPhoneNumber().equals(phoneNumber)) {
            throw new RuntimeException("Mismatch between user phone number and existing customer");
        }

        ussdUser.setCustomer(existingCustomer);
        ussdUser.setBalance(existingAccount.getBalance());

        return ussdUserRepository.save(ussdUser);
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
