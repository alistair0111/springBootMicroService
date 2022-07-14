package com.example.transaction.repository;

import com.example.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    Transaction findByTransactionId(String transactionId);
}