package com.wealthe.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="users")
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private String fullName;
    @Column(nullable=false) private String currency = "MGA";
    @Column(nullable=false) private boolean active = true;
    @Column(nullable=false, updatable=false) private Instant createdAt = Instant.now();

    public Long getId(){return id;} public String getEmail(){return email;}
    public String getPassword(){return password;} public String getFullName(){return fullName;}
    public String getCurrency(){return currency;} public boolean isActive(){return active;}
    public Instant getCreatedAt(){return createdAt;}
    public void setEmail(String v){email=v;} public void setPassword(String v){password=v;}
    public void setFullName(String v){fullName=v;} public void setCurrency(String v){currency=v;}
}
