package com.wealthe.service;

import com.wealthe.dto.request.CreateAccountRequest;
import com.wealthe.dto.request.UpdateAccountRequest;
import com.wealthe.dto.response.AccountResponse;
import com.wealthe.entity.Account;
import com.wealthe.entity.User;
import com.wealthe.exception.ResourceNotFoundException;
import com.wealthe.mapper.AccountMapper;
import com.wealthe.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<AccountResponse> findAllByUser(Long userId) {
        return accountRepository.findByUserIdOrderByIdDesc(userId).stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    public AccountResponse findById(Long id) {
        return AccountMapper.toResponse(getById(id));
    }

    public AccountResponse create(CreateAccountRequest request) {
        User user = userService.getById(request.userId());
        Account account = new Account();
        account.setUser(user);
        account.setName(request.name());
        account.setType(request.type());
        account.setCurrency(request.currency() == null ? user.getCurrency() : request.currency().toUpperCase());
        account.setBalance(request.balance() == null ? BigDecimal.ZERO : request.balance());
        return AccountMapper.toResponse(accountRepository.save(account));
    }

    public AccountResponse update(Long id, UpdateAccountRequest request) {
        Account account = getById(id);
        account.setName(request.name());
        account.setType(request.type());
        if (request.currency() != null) {
            account.setCurrency(request.currency().toUpperCase());
        }
        return AccountMapper.toResponse(accountRepository.save(account));
    }

    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found");
        }
        accountRepository.deleteById(id);
    }

    /** Used by other services that need the JPA entity itself. */
    public Account getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }
}
