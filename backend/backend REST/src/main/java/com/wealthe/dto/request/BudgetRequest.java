package com.wealthe.dto.request;
import com.wealthe.enumeration.BudgetPeriod;
import jakarta.validation.constraints.*;
import java.math.BigDecimal; import java.time.LocalDate;
public record BudgetRequest(@NotNull Long userId,@NotBlank String name,@NotNull @DecimalMin("0.01") BigDecimal amount,
 @NotNull BudgetPeriod period,@NotNull LocalDate startDate,@NotNull LocalDate endDate) {}
