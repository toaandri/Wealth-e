package com.wealthe.entity;
import com.wealthe.enumeration.GoalStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
@Entity @Table(name="goals")
public class Goal {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private User user;
 @Column(nullable=false) private String name;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal targetAmount;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal currentAmount=BigDecimal.ZERO;
 @Column(nullable=false) private LocalDate deadline;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private GoalStatus status=GoalStatus.ACTIVE;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public User getUser(){return user;} public String getName(){return name;}
 public BigDecimal getTargetAmount(){return targetAmount;} public BigDecimal getCurrentAmount(){return currentAmount;} public LocalDate getDeadline(){return deadline;} public GoalStatus getStatus(){return status;}
 public void setUser(User v){user=v;} public void setName(String v){name=v;} public void setTargetAmount(BigDecimal v){targetAmount=v;} public void setCurrentAmount(BigDecimal v){currentAmount=v;}
 public void setDeadline(LocalDate v){deadline=v;} public void setStatus(GoalStatus v){status=v;}
}
