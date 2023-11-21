package com.gebeya.pro.Repository;

import com.gebeya.pro.Model.Account;
import com.gebeya.pro.Model.Customer;
import com.gebeya.pro.Model.UssdUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UssdUserRepository extends JpaRepository<UssdUser, Long> {
    public Optional<UssdUser> findByPhoneNumber(String phoneNumber);

}
