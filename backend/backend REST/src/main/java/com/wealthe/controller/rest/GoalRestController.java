package com.wealthe.controller.rest;

import java.util.List;

import com.wealthe.dto.request.GoalRequest;
import com.wealthe.dto.response.GoalResponse;
import com.wealthe.service.GoalService;
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
@RequestMapping("/api/goals")
public class GoalRestController {

    private final GoalService service;

    public GoalRestController(GoalService service) {
        this.service = service;
    }

    @GetMapping
    public List<GoalResponse> all(@RequestParam Long userId, @AuthenticationPrincipal UserDetails principal) {
        Long authenticatedUserId = AuthenticatedUser.id(principal);
        AuthenticatedUser.requireClaim(userId, authenticatedUserId);
        return service.all(authenticatedUserId);
    }

    @GetMapping("/{id}")
    public GoalResponse one(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
        return service.one(id, AuthenticatedUser.id(principal));
    }

    @PostMapping
    public ResponseEntity<GoalResponse> create(
            @Valid @RequestBody GoalRequest request,
            @AuthenticationPrincipal UserDetails principal
    ) {
        AuthenticatedUser.requireClaim(request.userId(), AuthenticatedUser.id(principal));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public GoalResponse update(
            @PathVariable Long id,
            @Valid @RequestBody GoalRequest request,
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
