package com.wealthe.transaction;
import com.wealthe.account.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController @RequestMapping("/api/transactions")
public class TransactionController {
 private final TransactionRepository tx; private final AccountRepository accounts;
 public TransactionController(TransactionRepository t,AccountRepository a){tx=t;accounts=a;}
 @GetMapping public List<TransactionDto> byUser(@RequestParam Long userId){return tx.findByAccountUserIdOrderByTransactionDateDesc(userId).stream().map(TransactionDto::from).toList();}
 @GetMapping("/account/{accountId}") public List<TransactionDto> byAccount(@PathVariable Long accountId){return tx.findByAccountIdOrderByTransactionDateDesc(accountId).stream().map(TransactionDto::from).toList();}
 @PostMapping public ResponseEntity<TransactionDto> create(@Valid @RequestBody CreateTransaction r){
   Account a=accounts.findById(r.accountId()).orElseThrow(()->new IllegalArgumentException("Account not found"));
   Transaction t=new Transaction();t.setAccount(a);t.setAmount(r.amount());t.setType(r.type());t.setCategory(r.category());t.setDescription(r.description());t.setTransactionDate(r.transactionDate()==null?LocalDate.now():r.transactionDate());
   return ResponseEntity.status(HttpStatus.CREATED).body(TransactionDto.from(tx.save(t)));
 }
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(!tx.existsById(id))throw new IllegalArgumentException("Transaction not found");tx.deleteById(id);return ResponseEntity.noContent().build();}
}
record CreateTransaction(@NotNull Long accountId,@NotNull @DecimalMin("0.01") BigDecimal amount,@NotNull TransactionType type,@NotBlank String category,String description,LocalDate transactionDate){}
record TransactionDto(Long id,Long accountId,BigDecimal amount,TransactionType type,String category,String description,LocalDate transactionDate){
 static TransactionDto from(Transaction t){return new TransactionDto(t.getId(),t.getAccount().getId(),t.getAmount(),t.getType(),t.getCategory(),t.getDescription(),t.getTransactionDate());}
}
