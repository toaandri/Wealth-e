package com.wealthe.controller.rest;

import com.wealthe.dto.response.DashboardResponse;
import com.wealthe.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    private final DashboardService dashboardService;

    public DashboardRestController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse dashboard(@RequestParam Long userId, @AuthenticationPrincipal UserDetails principal) {
        Long authenticatedUserId = AuthenticatedUser.id(principal);
        AuthenticatedUser.requireClaim(userId, authenticatedUserId);
        return dashboardService.getDashboard(authenticatedUserId);
    }
}
