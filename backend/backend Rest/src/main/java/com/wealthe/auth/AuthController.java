package com.wealthe.auth;

import com.wealthe.user.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository repo; private final PasswordEncoder encoder;
    public AuthController(UserRepository repo, PasswordEncoder encoder){this.repo=repo;this.encoder=encoder;}

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest r){
        if(repo.existsByEmail(r.email().toLowerCase())) throw new IllegalArgumentException("Email already registered");
        User u=new User(); u.setEmail(r.email().toLowerCase()); u.setPassword(encoder.encode(r.password()));
        u.setFullName(r.fullName()); u.setCurrency(r.currency()==null?"MGA":r.currency().toUpperCase());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.from(repo.save(u)));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest r){
        User u=repo.findByEmail(r.email().toLowerCase()).orElseThrow(()->new IllegalArgumentException("Invalid credentials"));
        if(!encoder.matches(r.password(),u.getPassword())) throw new IllegalArgumentException("Invalid credentials");
        return new AuthResponse("demo-session-"+u.getId(),UserDto.from(u));
    }
}
record RegisterRequest(@Email @NotBlank String email,@NotBlank @Size(min=6) String password,@NotBlank String fullName,String currency){}
record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
record AuthResponse(String token,UserDto user){}
