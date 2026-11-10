package com.wealthe.dto.response;
import com.wealthe.enumeration.InvestmentType; import java.math.BigDecimal;
public record InvestmentResponse(Long id,Long userId,String name,InvestmentType type,BigDecimal investedAmount,BigDecimal currentValue,BigDecimal gainLoss,BigDecimal gainLossPercent) {}
