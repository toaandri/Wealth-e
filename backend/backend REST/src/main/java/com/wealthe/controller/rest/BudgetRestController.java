package com.wealthe.controller.rest;
import com.wealthe.dto.request.BudgetRequest; import com.wealthe.dto.response.BudgetResponse; import com.wealthe.service.BudgetService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/budgets")
public class BudgetRestController{
 private final BudgetService service; public BudgetRestController(BudgetService s){service=s;}
 @GetMapping public List<BudgetResponse> all(@RequestParam Long userId){return service.findAll(userId);}
 @GetMapping("/{id}") public BudgetResponse one(@PathVariable Long id){return service.findAll(service.get(id).getUser().getId()).stream().filter(x->x.id().equals(id)).findFirst().orElseThrow();}
 @PostMapping public ResponseEntity<BudgetResponse> create(@Valid @RequestBody BudgetRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));}
 @PutMapping("/{id}") public BudgetResponse update(@PathVariable Long id,@Valid @RequestBody BudgetRequest r){return service.update(id,r);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}
