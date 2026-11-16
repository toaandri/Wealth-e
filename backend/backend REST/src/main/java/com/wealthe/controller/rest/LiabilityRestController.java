package com.wealthe.controller.rest;

import java.util.List;

import com.wealthe.dto.request.LiabilityRequest;
import com.wealthe.dto.response.LiabilityResponse;
import com.wealthe.service.LiabilityService;
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
@RequestMapping("/api/liabilities")
public class LiabilityRestController {

    private final LiabilityService service;

    public LiabilityRestController(LiabilityService service) {
        this.service = service;
    }

    @GetMapping
    public List<LiabilityResponse> all(@RequestParam Long userId, @AuthenticationPrincipal UserDetails principal) {
        Long authenticatedUserId = AuthenticatedUser.id(principal);
        AuthenticatedUser.requireClaim(userId, authenticatedUserId);
        return service.all(authenticatedUserId);
    }

    @GetMapping("/{id}")
    public LiabilityResponse one(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
        return service.one(id, AuthenticatedUser.id(principal));
    }

    @PostMapping
    public ResponseEntity<LiabilityResponse> create(
            @Valid @RequestBody LiabilityRequest request,
            @AuthenticationPrincipal UserDetails principal
    ) {
        AuthenticatedUser.requireClaim(request.userId(), AuthenticatedUser.id(principal));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public LiabilityResponse update(
            @PathVariable Long id,
            @Valid @RequestBody LiabilityRequest request,
            @AuthenticationPrincipal UserDetails principal
    ) {
        AuthenticatedUser.requireClaim(request.userId(), AuthenticatedUser.id(principal));
        return service.update(id, request, AuthenticatedUser.id(principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
        service.delete(id, AuthenticatedUser.id(principal));
        return ResponseEntity.noContent().build();
    }
}
