package com.wealthe.dto.request;
import com.wealthe.enumeration.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
public record UpdateTransactionRequest(
 @NotNull Long accountId,@NotNull @DecimalMin("0.01") BigDecimal amount,@NotNull TransactionType type,
 @NotBlank String category,String description,LocalDate transactionDate) {}
