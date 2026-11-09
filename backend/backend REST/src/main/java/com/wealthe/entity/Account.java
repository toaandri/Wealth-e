package com.wealthe.entity;
import com.wealthe.enumeration.AccountType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name="accounts")
public class Account {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private AccountType type;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal balance=BigDecimal.ZERO;
 @Column(nullable=false) private String currency="MGA";
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private User user;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getName(){return name;} public AccountType getType(){return type;}
 public BigDecimal getBalance(){return balance;} public String getCurrency(){return currency;} public User getUser(){return user;} public Instant getCreatedAt(){return createdAt;}
 public void setName(String v){name=v;} public void setType(AccountType v){type=v;} public void setBalance(BigDecimal v){balance=v;} public void setCurrency(String v){currency=v;} public void setUser(User v){user=v;}
}
