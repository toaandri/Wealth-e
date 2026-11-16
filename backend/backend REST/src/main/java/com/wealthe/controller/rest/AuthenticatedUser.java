package com.wealthe.controller.rest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;

final class AuthenticatedUser {

    private AuthenticatedUser() {
    }

    static Long id(UserDetails principal) {
        return Long.valueOf(principal.getUsername());
    }

    static void requireClaim(Long requestedUserId, Long authenticatedUserId) {
        if (!authenticatedUserId.equals(requestedUserId)) {
            throw new AccessDeniedException("The requested user does not match the authenticated user");
        }
    }
}
