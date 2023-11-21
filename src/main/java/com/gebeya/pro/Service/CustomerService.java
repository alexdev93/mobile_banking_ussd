package com.gebeya.pro.Service;

import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.Address;
import com.gebeya.pro.Model.Customer;
import com.gebeya.pro.Repository.AddressRepository;
import com.gebeya.pro.Repository.CustomerRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountService accountService;

    @Transactional
    public Customer getCustomerByPhoneNumber(String phoneNumber){
        return customerRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Transactional
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        Address address = customer.getAddress();
        customer.setAddress(addressRepository.save(address));
        return customerRepository.save(customer);
    }

    public Customer createCustomerAndAccount(Customer customer) {
        try {
            Customer savedCustomer = createCustomer(customer);
            accountService.createAccount(savedCustomer);
            return savedCustomer;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create customer and account: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteColumn(Long id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer update) {
        Optional<Customer> existingCustomer = getCustomer(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(update, customer);
            return customerRepository.save(customer);
        }

        throw new RuntimeException("Failed to update customer and account: ");
    }

}
