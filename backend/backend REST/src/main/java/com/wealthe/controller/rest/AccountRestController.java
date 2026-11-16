package com.wealthe.controller.rest;

import com.wealthe.dto.request.CreateAccountRequest;
import com.wealthe.dto.request.UpdateAccountRequest;
import com.wealthe.dto.response.AccountResponse;
import com.wealthe.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponse> all(@RequestParam Long userId, @AuthenticationPrincipal UserDetails principal) {
        Long authenticatedUserId = AuthenticatedUser.id(principal);
        AuthenticatedUser.requireClaim(userId, authenticatedUserId);
        return accountService.findAllByUser(authenticatedUserId);
    }

    @GetMapping("/{id}")
    public AccountResponse one(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
        return accountService.findById(id, AuthenticatedUser.id(principal));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @Valid @RequestBody CreateAccountRequest request,
            @AuthenticationPrincipal UserDetails principal
    ) {
        AuthenticatedUser.requireClaim(request.userId(), AuthenticatedUser.id(principal));
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(request));
    }

    @PutMapping("/{id}")
    public AccountResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAccountRequest request,
            @AuthenticationPrincipal UserDetails principal
    ) {
        return accountService.update(id, request, AuthenticatedUser.id(principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
        accountService.delete(id, AuthenticatedUser.id(principal));
        return ResponseEntity.noContent().build();
    }
}
