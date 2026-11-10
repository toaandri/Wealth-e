package com.wealthe.dto.response;
import java.math.BigDecimal;
public record NetWorthResponse(Long userId,BigDecimal accountAssets,BigDecimal investmentAssets,BigDecimal otherAssets,BigDecimal totalAssets,BigDecimal totalLiabilities,BigDecimal netWorth) {}
