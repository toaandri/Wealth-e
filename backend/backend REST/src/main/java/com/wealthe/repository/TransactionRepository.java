package com.wealthe.repository;

import com.wealthe.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountUserIdOrderByTransactionDateDesc(Long userId);

    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);
}
