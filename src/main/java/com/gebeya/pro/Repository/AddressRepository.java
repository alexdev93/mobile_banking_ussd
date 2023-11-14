package com.gebeya.pro.Repository;
import com.gebeya.pro.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // You can add custom query methods here if needed
}
