package com.wealthe.entity;
import com.wealthe.enumeration.BudgetPeriod;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
@Entity @Table(name="budgets")
public class Budget {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private User user;
 @Column(nullable=false) private String name;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal amount;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private BudgetPeriod period;
 @Column(nullable=false) private LocalDate startDate;
 @Column(nullable=false) private LocalDate endDate;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public User getUser(){return user;} public String getName(){return name;}
 public BigDecimal getAmount(){return amount;} public BudgetPeriod getPeriod(){return period;} public LocalDate getStartDate(){return startDate;} public LocalDate getEndDate(){return endDate;}
 public void setUser(User v){user=v;} public void setName(String v){name=v;} public void setAmount(BigDecimal v){amount=v;}
 public void setPeriod(BudgetPeriod v){period=v;} public void setStartDate(LocalDate v){startDate=v;} public void setEndDate(LocalDate v){endDate=v;}
}
