package com.wealthe.service;
import com.wealthe.dto.response.DashboardResponse; import com.wealthe.entity.*; import com.wealthe.enumeration.TransactionType; import com.wealthe.repository.*; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.util.List;
@Service public class DashboardService{
 private final AccountRepository accounts; private final TransactionRepository tx; private final InvestmentRepository investments; private final AssetRepository assets; private final LiabilityRepository liabilities; private final BudgetRepository budgets; private final GoalRepository goals;
 public DashboardService(AccountRepository a,TransactionRepository t,InvestmentRepository i,AssetRepository as,LiabilityRepository l,BudgetRepository b,GoalRepository g){accounts=a;tx=t;investments=i;assets=as;liabilities=l;budgets=b;goals=g;}
 public DashboardResponse getDashboard(Long userId){
  List<Account> ac=accounts.findByUserIdOrderByIdDesc(userId); List<Transaction> tr=tx.findByAccountUserIdOrderByTransactionDateDesc(userId);
  BigDecimal balance=ac.stream().map(Account::getBalance).reduce(BigDecimal.ZERO,BigDecimal::add);
  BigDecimal income=sum(tr,TransactionType.INCOME),expense=sum(tr,TransactionType.EXPENSE);
  BigDecimal inv=investments.findByUserIdOrderByIdDesc(userId).stream().map(Investment::getCurrentValue).reduce(BigDecimal.ZERO,BigDecimal::add);
  BigDecimal other=assets.findByUserIdOrderByIdDesc(userId).stream().map(Asset::getValue).reduce(BigDecimal.ZERO,BigDecimal::add);
  BigDecimal debt=liabilities.findByUserIdOrderByIdDesc(userId).stream().map(Liability::getRemainingBalance).reduce(BigDecimal.ZERO,BigDecimal::add);
  BigDecimal totalAssets=balance.add(inv).add(other);
  return new DashboardResponse(userId,balance,income,expense,ac.size(),tr.size(),totalAssets,debt,totalAssets.subtract(debt),
   budgets.findByUserIdOrderByEndDateDesc(userId).size(),goals.findByUserIdOrderByDeadlineAsc(userId).size(),investments.findByUserIdOrderByIdDesc(userId).size(),assets.findByUserIdOrderByIdDesc(userId).size(),liabilities.findByUserIdOrderByIdDesc(userId).size());
 }
 private BigDecimal sum(List<Transaction> ts,TransactionType type){return ts.stream().filter(x->x.getType()==type).map(Transaction::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);}
}
