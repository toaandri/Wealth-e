package com.wealthe.mapper;

import com.wealthe.dto.response.UserResponse;
import com.wealthe.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getCurrency(),
                user.isActive(),
                user.getCreatedAt()
        );
    }
}
