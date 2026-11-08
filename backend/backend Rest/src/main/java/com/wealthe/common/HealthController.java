package com.wealthe.common;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.time.Instant;

@RestController
@RequestMapping("/api")
public class HealthController {
    @GetMapping("/health")
    public Map<String,Object> health() {
        return Map.of("status","UP","service","wealth-e-backend","timestamp",Instant.now());
    }
}
