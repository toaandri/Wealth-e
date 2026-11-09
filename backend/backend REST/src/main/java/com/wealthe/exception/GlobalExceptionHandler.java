package com.wealthe.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class, IllegalArgumentException.class})
    ResponseEntity<?> badRequest(RuntimeException e) {
        return ResponseEntity.badRequest().body(
                Map.of("timestamp", Instant.now(), "status", 400, "message", e.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return ResponseEntity.badRequest().body(
                Map.of("timestamp", Instant.now(), "status", 400, "message", msg)
        );
    }
}
