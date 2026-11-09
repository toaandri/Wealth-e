package com.wealthe.service;

import com.wealthe.dto.request.CreateTransactionRequest;
import com.wealthe.dto.response.TransactionResponse;
import com.wealthe.entity.Account;
import com.wealthe.entity.Transaction;
import com.wealthe.exception.ResourceNotFoundException;
import com.wealthe.mapper.TransactionMapper;
import com.wealthe.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public List<TransactionResponse> findByUser(Long userId) {
        return transactionRepository.findByAccountUserIdOrderByTransactionDateDesc(userId).stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public List<TransactionResponse> findByAccount(Long accountId) {
        return transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId).stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public TransactionResponse create(CreateTransactionRequest request) {
        Account account = accountService.getById(request.accountId());
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(request.amount());
        transaction.setType(request.type());
        transaction.setCategory(request.category());
        transaction.setDescription(request.description());
        transaction.setTransactionDate(
                request.transactionDate() == null ? LocalDate.now() : request.transactionDate()
        );
        return TransactionMapper.toResponse(transactionRepository.save(transaction));
    }

    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }
}
