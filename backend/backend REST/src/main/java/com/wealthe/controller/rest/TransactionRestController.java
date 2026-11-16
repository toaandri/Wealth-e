package com.wealthe.controller.rest;

import java.util.List;

import com.wealthe.dto.request.CreateTransactionRequest;
import com.wealthe.dto.request.UpdateTransactionRequest;
import com.wealthe.dto.response.TransactionResponse;
import com.wealthe.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    private final TransactionService service;

    public TransactionRestController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<TransactionResponse> byUser(
            @RequestParam Long userId,
            @AuthenticationPrincipal UserDetails principal
    ) {
        Long authenticatedUserId = AuthenticatedUser.id(principal);
        AuthenticatedUser.requireClaim(userId, authenticatedUserId);
        return service.findByUser(authenticatedUserId);
    }

    @GetMapping("/account/{accountId}")
    public List<TransactionResponse> byAccount(
            @PathVariable Long accountId,
            @AuthenticationPrincipal UserDetails principal
    ) {
        return service.findByAccount(accountId, AuthenticatedUser.id(principal));
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @Valid @RequestBody CreateTransactionRequest request,
            @AuthenticationPrincipal UserDetails principal
    ) {
        TransactionResponse response = service.create(AuthenticatedUser.id(principal), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public TransactionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionRequest request,
            @AuthenticationPrincipal UserDetails principal
    ) {
        return service.update(id, request, AuthenticatedUser.id(principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails principal
    ) {
        service.delete(id, AuthenticatedUser.id(principal));
        return ResponseEntity.noContent().build();
    }
}
