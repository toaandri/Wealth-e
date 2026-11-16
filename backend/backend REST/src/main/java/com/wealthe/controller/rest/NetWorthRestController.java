package com.wealthe.controller.rest;

import com.wealthe.dto.response.NetWorthResponse;
import com.wealthe.service.NetWorthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/net-worth")
public class NetWorthRestController {

    private final NetWorthService service;

    public NetWorthRestController(NetWorthService service) {
        this.service = service;
    }

    @GetMapping
    public NetWorthResponse get(@RequestParam Long userId, @AuthenticationPrincipal UserDetails principal) {
        Long authenticatedUserId = AuthenticatedUser.id(principal);
        AuthenticatedUser.requireClaim(userId, authenticatedUserId);
        return service.get(authenticatedUserId);
    }
}
