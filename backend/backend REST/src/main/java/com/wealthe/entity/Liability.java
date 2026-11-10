package com.wealthe.entity;
import com.wealthe.enumeration.LiabilityType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
@Entity @Table(name="liabilities")
public class Liability {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private User user;
 @Column(nullable=false) private String name;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private LiabilityType type;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal principalAmount;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal remainingBalance;
 @Column(nullable=false,precision=8,scale=4) private BigDecimal interestRate=BigDecimal.ZERO;
 private LocalDate dueDate;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public User getUser(){return user;} public String getName(){return name;} public LiabilityType getType(){return type;}
 public BigDecimal getPrincipalAmount(){return principalAmount;} public BigDecimal getRemainingBalance(){return remainingBalance;} public BigDecimal getInterestRate(){return interestRate;} public LocalDate getDueDate(){return dueDate;} public Instant getCreatedAt(){return createdAt;}
 public void setUser(User v){user=v;} public void setName(String v){name=v;} public void setType(LiabilityType v){type=v;} public void setPrincipalAmount(BigDecimal v){principalAmount=v;} public void setRemainingBalance(BigDecimal v){remainingBalance=v;} public void setInterestRate(BigDecimal v){interestRate=v;} public void setDueDate(LocalDate v){dueDate=v;}
}
