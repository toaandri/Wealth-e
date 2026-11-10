package com.wealthe.controller.rest;
import com.wealthe.dto.request.LiabilityRequest; import com.wealthe.dto.response.LiabilityResponse; import com.wealthe.service.LiabilityService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/liabilities") public class LiabilityRestController{
 private final LiabilityService s; public LiabilityRestController(LiabilityService s){this.s=s;} @GetMapping public List<LiabilityResponse> all(@RequestParam Long userId){return s.all(userId);} @GetMapping("/{id}") public LiabilityResponse one(@PathVariable Long id){return s.one(id);}
 @PostMapping public ResponseEntity<LiabilityResponse> create(@Valid @RequestBody LiabilityRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));}
 @PutMapping("/{id}") public LiabilityResponse update(@PathVariable Long id,@Valid @RequestBody LiabilityRequest r){return s.update(id,r);} @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){s.delete(id);return ResponseEntity.noContent().build();}
}
