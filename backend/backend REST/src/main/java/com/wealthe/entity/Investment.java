package com.wealthe.entity;
import com.wealthe.enumeration.InvestmentType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity @Table(name="investments")
public class Investment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private User user;
 @Column(nullable=false) private String name;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private InvestmentType type;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal investedAmount;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal currentValue;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public User getUser(){return user;} public String getName(){return name;} public InvestmentType getType(){return type;}
 public BigDecimal getInvestedAmount(){return investedAmount;} public BigDecimal getCurrentValue(){return currentValue;} public Instant getCreatedAt(){return createdAt;}
 public void setUser(User v){user=v;} public void setName(String v){name=v;} public void setType(InvestmentType v){type=v;} public void setInvestedAmount(BigDecimal v){investedAmount=v;} public void setCurrentValue(BigDecimal v){currentValue=v;}
}
