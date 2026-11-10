package com.wealthe.dto.response;
import java.math.BigDecimal; import java.util.List;
public record AnalyticsResponse(BigDecimal totalIncome,BigDecimal totalExpenses,BigDecimal netCashFlow,
 List<CategoryStat> expensesByCategory,List<MonthlyStat> monthly) {
 public record CategoryStat(String category,BigDecimal amount){}
 public record MonthlyStat(String month,BigDecimal income,BigDecimal expenses,BigDecimal net){}
}
