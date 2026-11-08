package com.wealthe.user;

import java.time.Instant;

public record UserDto(
        Long id,
        String email,
        String fullName,
        String currency,
        boolean active,
        Instant createdAt
) {

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getCurrency(),
                user.isActive(),
                user.getCreatedAt()
        );
    }
}