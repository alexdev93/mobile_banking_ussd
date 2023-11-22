package com.gebeya.pro.Repository;

import com.gebeya.pro.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public Transaction findByOtpAndStatus(int otp, String status);
    List<Transaction> findTop5ByOrderByTransactionDateDesc();
}
