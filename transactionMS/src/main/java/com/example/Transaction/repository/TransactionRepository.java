package com.example.Transaction.repository;

import com.example.Transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    Transaction findByTransactionId(String transactionId);
}