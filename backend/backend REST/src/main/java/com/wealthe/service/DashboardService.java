package com.wealthe.service;

import com.wealthe.dto.response.DashboardResponse;
import com.wealthe.entity.Account;
import com.wealthe.entity.Transaction;
import com.wealthe.enumeration.TransactionType;
import com.wealthe.repository.AccountRepository;
import com.wealthe.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public DashboardService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public DashboardResponse getDashboard(Long userId) {
        List<Account> accounts = accountRepository.findByUserIdOrderByIdDesc(userId);
        BigDecimal total = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Transaction> transactions = transactionRepository.findByAccountUserIdOrderByTransactionDateDesc(userId);
        BigDecimal income = sumByType(transactions, TransactionType.INCOME);
        BigDecimal expenses = sumByType(transactions, TransactionType.EXPENSE);

        return new DashboardResponse(userId, total, income, expenses, accounts.size(), transactions.size());
    }

    private BigDecimal sumByType(List<Transaction> transactions, TransactionType type) {
        return transactions.stream()
                .filter(t -> t.getType() == type)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
