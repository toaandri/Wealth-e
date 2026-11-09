package com.wealthe.service;

import com.wealthe.dto.request.LoginRequest;
import com.wealthe.dto.request.RegisterRequest;
import com.wealthe.dto.response.AuthResponse;
import com.wealthe.dto.response.UserResponse;
import com.wealthe.entity.User;
import com.wealthe.exception.BusinessException;
import com.wealthe.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.email().toLowerCase())) {
            throw new BusinessException("Email already registered");
        }
        User user = new User();
        user.setEmail(request.email().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());
        user.setCurrency(request.currency() == null ? "MGA" : request.currency().toUpperCase());
        return UserMapper.toResponse(userService.save(user));
    }

    public AuthResponse login(LoginRequest request) {
        User user = userService.getByEmail(request.email().toLowerCase());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Invalid credentials");
        }
        return new AuthResponse("demo-session-" + user.getId(), UserMapper.toResponse(user));
    }
}
