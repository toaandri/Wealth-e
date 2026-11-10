package com.wealthe.controller.rest;
import com.wealthe.dto.request.CreateTransactionRequest; import com.wealthe.dto.request.UpdateTransactionRequest; import com.wealthe.dto.response.TransactionResponse;
import com.wealthe.service.TransactionService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/transactions")
public class TransactionRestController {
 private final TransactionService service; public TransactionRestController(TransactionService s){service=s;}
 @GetMapping public List<TransactionResponse> byUser(@RequestParam Long userId){return service.findByUser(userId);}
 @GetMapping("/account/{accountId}") public List<TransactionResponse> byAccount(@PathVariable Long accountId){return service.findByAccount(accountId);}
 @PostMapping public ResponseEntity<TransactionResponse> create(@Valid @RequestBody CreateTransactionRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));}
 @PutMapping("/{id}") public TransactionResponse update(@PathVariable Long id,@Valid @RequestBody UpdateTransactionRequest r){return service.update(id,r);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}
