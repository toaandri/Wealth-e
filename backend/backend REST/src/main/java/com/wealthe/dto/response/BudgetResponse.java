package com.wealthe.dto.response;
import com.wealthe.enumeration.BudgetPeriod; import java.math.BigDecimal; import java.time.LocalDate;
public record BudgetResponse(Long id,Long userId,String name,BigDecimal amount,BudgetPeriod period,LocalDate startDate,LocalDate endDate,BigDecimal spent,BigDecimal remaining) {}
