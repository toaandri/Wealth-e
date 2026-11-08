package com.wealthe.account;
import com.wealthe.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/api/accounts")
public class AccountController {
 private final AccountRepository accounts; private final UserRepository users;
 public AccountController(AccountRepository a,UserRepository u){accounts=a;users=u;}
 @GetMapping public List<AccountDto> all(@RequestParam Long userId){return accounts.findByUserIdOrderByIdDesc(userId).stream().map(AccountDto::from).toList();}
 @GetMapping("/{id}") public AccountDto one(@PathVariable Long id){return AccountDto.from(accounts.findById(id).orElseThrow(()->new IllegalArgumentException("Account not found")));}
 @PostMapping public ResponseEntity<AccountDto> create(@Valid @RequestBody CreateAccount r){
   var u=users.findById(r.userId()).orElseThrow(()->new IllegalArgumentException("User not found"));
   Account a=new Account();a.setUser(u);a.setName(r.name());a.setType(r.type());a.setCurrency(r.currency()==null?u.getCurrency():r.currency().toUpperCase());a.setBalance(r.balance()==null?BigDecimal.ZERO:r.balance());
   return ResponseEntity.status(HttpStatus.CREATED).body(AccountDto.from(accounts.save(a)));
 }
 @PutMapping("/{id}") public AccountDto update(@PathVariable Long id,@Valid @RequestBody UpdateAccount r){
   Account a=accounts.findById(id).orElseThrow(()->new IllegalArgumentException("Account not found"));
   a.setName(r.name());a.setType(r.type()); if(r.currency()!=null)a.setCurrency(r.currency().toUpperCase()); return AccountDto.from(accounts.save(a));
 }
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(!accounts.existsById(id))throw new IllegalArgumentException("Account not found");accounts.deleteById(id);return ResponseEntity.noContent().build();}
}
record CreateAccount(@NotNull Long userId,@NotBlank String name,@NotNull AccountType type,String currency,BigDecimal balance){}
record UpdateAccount(@NotBlank String name,@NotNull AccountType type,String currency){}
record AccountDto(Long id,String name,AccountType type,BigDecimal balance,String currency,Long userId){
 static AccountDto from(Account a){return new AccountDto(a.getId(),a.getName(),a.getType(),a.getBalance(),a.getCurrency(),a.getUser().getId());}
}
