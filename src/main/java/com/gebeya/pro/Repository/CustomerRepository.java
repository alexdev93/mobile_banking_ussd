package com.gebeya.pro.Repository;

import com.gebeya.pro.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT MAX(c.cif) FROM Customer c")
    Integer getLastCif();
}
