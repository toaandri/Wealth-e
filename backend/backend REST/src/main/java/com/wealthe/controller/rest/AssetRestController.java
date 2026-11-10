package com.wealthe.controller.rest;
import com.wealthe.dto.request.AssetRequest; import com.wealthe.dto.response.AssetResponse; import com.wealthe.service.AssetService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/assets") public class AssetRestController{
 private final AssetService s; public AssetRestController(AssetService s){this.s=s;} @GetMapping public List<AssetResponse> all(@RequestParam Long userId){return s.all(userId);} @GetMapping("/{id}") public AssetResponse one(@PathVariable Long id){return s.one(id);}
 @PostMapping public ResponseEntity<AssetResponse> create(@Valid @RequestBody AssetRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));}
 @PutMapping("/{id}") public AssetResponse update(@PathVariable Long id,@Valid @RequestBody AssetRequest r){return s.update(id,r);} @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){s.delete(id);return ResponseEntity.noContent().build();}
}
