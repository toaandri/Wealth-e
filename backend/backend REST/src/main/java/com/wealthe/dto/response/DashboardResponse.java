package com.wealthe.dto.response;
import java.math.BigDecimal;
public record DashboardResponse(Long userId,BigDecimal totalBalance,BigDecimal totalIncome,BigDecimal totalExpenses,int accountCount,int transactionCount,
 BigDecimal totalAssets,BigDecimal totalLiabilities,BigDecimal netWorth,int budgetCount,int goalCount,int investmentCount,int assetCount,int liabilityCount){}
