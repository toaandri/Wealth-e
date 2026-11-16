package com.wealthe.service;

import com.wealthe.exception.ResourceNotFoundException;

final class ResourceOwnership {

    private ResourceOwnership() {
    }

    static void requireOwner(Long authenticatedUserId, Long resourceUserId) {
        if (!authenticatedUserId.equals(resourceUserId)) {
            throw new ResourceNotFoundException("Resource not found");
        }
    }
}
