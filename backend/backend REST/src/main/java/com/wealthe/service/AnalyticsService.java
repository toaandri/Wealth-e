package com.wealthe.service;
import com.wealthe.dto.response.AnalyticsResponse; import com.wealthe.entity.Transaction; import com.wealthe.enumeration.TransactionType; import com.wealthe.repository.TransactionRepository; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.time.LocalDate; import java.time.YearMonth; import java.util.*;
@Service public class AnalyticsService{
 private final TransactionRepository repo; public AnalyticsService(TransactionRepository r){repo=r;}
 public AnalyticsResponse get(Long userId,LocalDate from,LocalDate to){
  LocalDate f=from==null?LocalDate.MIN:from, t=to==null?LocalDate.MAX:to;
  List<Transaction> xs=repo.findByAccountUserIdOrderByTransactionDateDesc(userId).stream().filter(x->!x.getTransactionDate().isBefore(f)&&!x.getTransactionDate().isAfter(t)).toList();
  BigDecimal income=sum(xs,TransactionType.INCOME), expense=sum(xs,TransactionType.EXPENSE);
  Map<String,BigDecimal> cat=new TreeMap<>(); xs.stream().filter(x->x.getType()==TransactionType.EXPENSE).forEach(x->cat.merge(x.getCategory(),x.getAmount(),BigDecimal::add));
  Map<YearMonth,BigDecimal[]> months=new TreeMap<>(); for(Transaction x:xs){BigDecimal[] a=months.computeIfAbsent(YearMonth.from(x.getTransactionDate()),k->new BigDecimal[]{BigDecimal.ZERO,BigDecimal.ZERO}); if(x.getType()==TransactionType.INCOME)a[0]=a[0].add(x.getAmount());else a[1]=a[1].add(x.getAmount());}
  List<AnalyticsResponse.MonthlyStat> monthly=months.entrySet().stream().map(e->new AnalyticsResponse.MonthlyStat(e.getKey().toString(),e.getValue()[0],e.getValue()[1],e.getValue()[0].subtract(e.getValue()[1]))).toList();
  List<AnalyticsResponse.CategoryStat> categories=cat.entrySet().stream().map(e->new AnalyticsResponse.CategoryStat(e.getKey(),e.getValue())).toList();
  return new AnalyticsResponse(income,expense,income.subtract(expense),categories,monthly);
 }
 private BigDecimal sum(List<Transaction> xs,TransactionType type){return xs.stream().filter(x->x.getType()==type).map(Transaction::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);}
}
