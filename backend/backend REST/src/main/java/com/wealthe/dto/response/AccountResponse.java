package com.wealthe.dto.response;

import com.wealthe.enumeration.AccountType;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String name,
        AccountType type,
        BigDecimal balance,
        String currency,
        Long userId
) {
}
