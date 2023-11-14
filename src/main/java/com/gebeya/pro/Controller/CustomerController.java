package com.gebeya.pro.Controller;

import com.gebeya.pro.Model.Customer;
import com.gebeya.pro.Service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable Long customer_id) {
        Optional<Customer> existingCustomer = customerService.getCustomer(customer_id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        throw new RuntimeException("this customer doesn't exist");
    }

    @PostMapping("/add")
    public Customer createCustomer(@RequestBody Customer customer) {
        try {
            return customerService.createCustomerAndAccount(customer);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PatchMapping("/{customer_id}")
    public ResponseEntity<Customer> updateCustomerPartially(@PathVariable Long customer_id,
            @RequestBody Customer updateCustomer) {
        return new ResponseEntity<>(customerService.updateCustomer(customer_id, updateCustomer), HttpStatus.OK);
    }

    @DeleteMapping("/{customer_id}")
    public void deleteOneCustomer(@PathVariable Long customer_id) {
        customerService.deleteColumn(customer_id);
    }
}
