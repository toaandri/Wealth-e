package com.wealthe.dto.request;

import com.wealthe.enumeration.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAccountRequest(
        @NotBlank String name,
        @NotNull AccountType type,
        String currency
) {
}
