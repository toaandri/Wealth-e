package com.wealthe.controller.rest;
import com.wealthe.dto.response.AnalyticsResponse; import com.wealthe.service.AnalyticsService; import org.springframework.format.annotation.DateTimeFormat; import org.springframework.web.bind.annotation.*; import java.time.LocalDate;
@RestController @RequestMapping("/api/analytics") public class AnalyticsRestController{
 private final AnalyticsService s; public AnalyticsRestController(AnalyticsService s){this.s=s;}
 @GetMapping public AnalyticsResponse get(@RequestParam Long userId,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate from,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate to){return s.get(userId,from,to);}
}
