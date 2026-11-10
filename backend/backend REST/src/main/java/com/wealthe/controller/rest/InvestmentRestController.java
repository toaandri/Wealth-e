package com.wealthe.controller.rest;
import com.wealthe.dto.request.InvestmentRequest; import com.wealthe.dto.response.InvestmentResponse; import com.wealthe.service.InvestmentService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/investments") public class InvestmentRestController{
 private final InvestmentService s; public InvestmentRestController(InvestmentService s){this.s=s;} @GetMapping public List<InvestmentResponse> all(@RequestParam Long userId){return s.all(userId);}
 @GetMapping("/{id}") public InvestmentResponse one(@PathVariable Long id){return s.one(id);} @PostMapping public ResponseEntity<InvestmentResponse> create(@Valid @RequestBody InvestmentRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));}
 @PutMapping("/{id}") public InvestmentResponse update(@PathVariable Long id,@Valid @RequestBody InvestmentRequest r){return s.update(id,r);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){s.delete(id);return ResponseEntity.noContent().build();}
}
