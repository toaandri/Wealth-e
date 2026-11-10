package com.wealthe.dto.request;
import com.wealthe.enumeration.LiabilityType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal; import java.time.LocalDate;
public record LiabilityRequest(@NotNull Long userId,@NotBlank String name,@NotNull LiabilityType type,
 @NotNull @DecimalMin("0.00") BigDecimal principalAmount,@NotNull @DecimalMin("0.00") BigDecimal remainingBalance,
 @DecimalMin("0.00") BigDecimal interestRate,LocalDate dueDate) {}
