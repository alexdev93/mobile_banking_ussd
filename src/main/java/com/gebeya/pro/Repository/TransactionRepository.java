package com.gebeya.pro.Repository;

import com.gebeya.pro.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT MAX(t.rrn) FROM Transaction t")
    Long getLastRrn();
}
