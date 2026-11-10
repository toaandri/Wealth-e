package com.wealthe.dto.response;
import com.wealthe.enumeration.LiabilityType; import java.math.BigDecimal; import java.time.LocalDate;
public record LiabilityResponse(Long id,Long userId,String name,LiabilityType type,BigDecimal principalAmount,BigDecimal remainingBalance,BigDecimal interestRate,LocalDate dueDate) {}
