package com.wealthe.dashboard;
import com.wealthe.account.*;
import com.wealthe.transaction.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
 private final AccountRepository accounts; private final TransactionRepository tx;
 public DashboardController(AccountRepository a,TransactionRepository t){accounts=a;tx=t;}
 @GetMapping
 public DashboardDto dashboard(@RequestParam Long userId){
   List<Account> as=accounts.findByUserIdOrderByIdDesc(userId);
   BigDecimal total=as.stream().map(Account::getBalance).reduce(BigDecimal.ZERO,BigDecimal::add);
   var ts=tx.findByAccountUserIdOrderByTransactionDateDesc(userId);
   BigDecimal income=ts.stream().filter(x->x.getType()==TransactionType.INCOME).map(Transaction::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
   BigDecimal expenses=ts.stream().filter(x->x.getType()==TransactionType.EXPENSE).map(Transaction::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
   return new DashboardDto(userId,total,income,expenses,as.size(),ts.size());
 }
}
record DashboardDto(Long userId,BigDecimal totalBalance,BigDecimal totalIncome,BigDecimal totalExpenses,int accountCount,int transactionCount){}
