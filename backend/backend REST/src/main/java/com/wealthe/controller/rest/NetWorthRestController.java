package com.wealthe.controller.rest;
import com.wealthe.dto.response.NetWorthResponse; import com.wealthe.service.NetWorthService; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/net-worth") public class NetWorthRestController{
 private final NetWorthService s; public NetWorthRestController(NetWorthService s){this.s=s;}
 @GetMapping public NetWorthResponse get(@RequestParam Long userId){return s.get(userId);}
}
