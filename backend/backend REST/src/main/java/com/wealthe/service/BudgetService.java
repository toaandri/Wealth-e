package com.wealthe.service;
import com.wealthe.dto.request.BudgetRequest; import com.wealthe.dto.response.BudgetResponse; import com.wealthe.entity.*; import com.wealthe.enumeration.TransactionType;
import com.wealthe.exception.ResourceNotFoundException; import com.wealthe.repository.*; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.List;
@Service public class BudgetService {
 private final BudgetRepository repo; private final UserService users; private final TransactionRepository tx;
 public BudgetService(BudgetRepository r,UserService u,TransactionRepository t){repo=r;users=u;tx=t;}
 public List<BudgetResponse> findAll(Long userId){return repo.findByUserIdOrderByEndDateDesc(userId).stream().map(this::response).toList();}
 public BudgetResponse create(BudgetRequest r){if(r.endDate().isBefore(r.startDate()))throw new IllegalArgumentException("endDate must be after startDate");Budget b=new Budget();b.setUser(users.getById(r.userId()));b.setName(r.name());b.setAmount(r.amount());b.setPeriod(r.period());b.setStartDate(r.startDate());b.setEndDate(r.endDate());return response(repo.save(b));}
 public BudgetResponse update(Long id,BudgetRequest r){Budget b=get(id);b.setName(r.name());b.setAmount(r.amount());b.setPeriod(r.period());b.setStartDate(r.startDate());b.setEndDate(r.endDate());return response(repo.save(b));}
 public void delete(Long id){repo.delete(get(id));}
 public Budget get(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Budget not found"));}
 private BudgetResponse response(Budget b){BigDecimal spent=tx.findByAccountUserIdOrderByTransactionDateDesc(b.getUser().getId()).stream().filter(t->t.getType()==TransactionType.EXPENSE&&!t.getTransactionDate().isBefore(b.getStartDate())&&!t.getTransactionDate().isAfter(b.getEndDate())).map(Transaction::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);return new BudgetResponse(b.getId(),b.getUser().getId(),b.getName(),b.getAmount(),b.getPeriod(),b.getStartDate(),b.getEndDate(),spent,b.getAmount().subtract(spent));}
}
