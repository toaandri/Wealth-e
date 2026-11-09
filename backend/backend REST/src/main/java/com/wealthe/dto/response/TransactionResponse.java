package com.wealthe.dto.response;

import com.wealthe.enumeration.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        Long accountId,
        BigDecimal amount,
        TransactionType type,
        String category,
        String description,
        LocalDate transactionDate
) {
}
