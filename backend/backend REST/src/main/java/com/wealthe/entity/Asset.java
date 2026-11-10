package com.wealthe.entity;
import com.wealthe.enumeration.AssetType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity @Table(name="assets")
public class Asset {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private User user;
 @Column(nullable=false) private String name;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private AssetType type;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal value;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public User getUser(){return user;} public String getName(){return name;} public AssetType getType(){return type;} public BigDecimal getValue(){return value;} public Instant getCreatedAt(){return createdAt;}
 public void setUser(User v){user=v;} public void setName(String v){name=v;} public void setType(AssetType v){type=v;} public void setValue(BigDecimal v){value=v;}
}
