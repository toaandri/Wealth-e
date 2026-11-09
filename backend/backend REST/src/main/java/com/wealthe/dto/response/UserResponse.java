package com.wealthe.dto.response;

import java.time.Instant;

public record UserResponse(
        Long id,
        String email,
        String fullName,
        String currency,
        boolean active,
        Instant createdAt
) {
}
