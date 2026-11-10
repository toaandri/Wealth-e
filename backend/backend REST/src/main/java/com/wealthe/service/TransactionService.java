package com.wealthe.service;
import com.wealthe.dto.request.CreateTransactionRequest; import com.wealthe.dto.request.UpdateTransactionRequest;
import com.wealthe.dto.response.TransactionResponse; import com.wealthe.entity.Account; import com.wealthe.entity.Transaction;
import com.wealthe.enumeration.TransactionType; import com.wealthe.exception.ResourceNotFoundException; import com.wealthe.mapper.TransactionMapper;
import com.wealthe.repository.TransactionRepository; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.List;
@Service
public class TransactionService {
 private final TransactionRepository transactionRepository; private final AccountService accountService;
 public TransactionService(TransactionRepository r,AccountService a){transactionRepository=r;accountService=a;}
 public List<TransactionResponse> findByUser(Long userId){return transactionRepository.findByAccountUserIdOrderByTransactionDateDesc(userId).stream().map(TransactionMapper::toResponse).toList();}
 public List<TransactionResponse> findByAccount(Long accountId){return transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId).stream().map(TransactionMapper::toResponse).toList();}
 @Transactional public TransactionResponse create(CreateTransactionRequest r){
  Account a=accountService.getById(r.accountId()); applyBalance(a,r.type(),r.amount());
  Transaction t=new Transaction(); t.setAccount(a);t.setAmount(r.amount());t.setType(r.type());t.setCategory(r.category());t.setDescription(r.description());
  t.setTransactionDate(r.transactionDate()==null?LocalDate.now():r.transactionDate());
  transactionRepository.save(t); return TransactionMapper.toResponse(t);
 }
 @Transactional public TransactionResponse update(Long id,UpdateTransactionRequest r){
  Transaction t=transactionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Transaction not found"));
  reverseBalance(t.getAccount(),t.getType(),t.getAmount());
  Account a=accountService.getById(r.accountId()); applyBalance(a,r.type(),r.amount());
  t.setAccount(a);t.setAmount(r.amount());t.setType(r.type());t.setCategory(r.category());t.setDescription(r.description());
  t.setTransactionDate(r.transactionDate()==null?LocalDate.now():r.transactionDate());
  return TransactionMapper.toResponse(transactionRepository.save(t));
 }
 @Transactional public void delete(Long id){
  Transaction t=transactionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Transaction not found"));
  reverseBalance(t.getAccount(),t.getType(),t.getAmount()); transactionRepository.delete(t);
 }
 private void applyBalance(Account a,TransactionType type,BigDecimal amount){a.setBalance(type==TransactionType.INCOME?a.getBalance().add(amount):a.getBalance().subtract(amount));}
 private void reverseBalance(Account a,TransactionType type,BigDecimal amount){a.setBalance(type==TransactionType.INCOME?a.getBalance().subtract(amount):a.getBalance().add(amount));}
}
