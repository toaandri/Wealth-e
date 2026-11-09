package com.wealthe.mapper;

import com.wealthe.dto.response.AccountResponse;
import com.wealthe.entity.Account;

public final class AccountMapper {

    private AccountMapper() {
    }

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getType(),
                account.getBalance(),
                account.getCurrency(),
                account.getUser().getId()
        );
    }
}
