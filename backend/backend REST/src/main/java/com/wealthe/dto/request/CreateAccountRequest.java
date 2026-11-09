package com.wealthe.dto.request;

import com.wealthe.enumeration.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotNull Long userId,
        @NotBlank String name,
        @NotNull AccountType type,
        String currency,
        BigDecimal balance
) {
}
