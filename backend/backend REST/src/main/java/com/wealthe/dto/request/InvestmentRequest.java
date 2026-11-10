package com.wealthe.dto.request;
import com.wealthe.enumeration.InvestmentType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record InvestmentRequest(@NotNull Long userId,@NotBlank String name,@NotNull InvestmentType type,
 @NotNull @DecimalMin("0.00") BigDecimal investedAmount,@NotNull @DecimalMin("0.00") BigDecimal currentValue) {}
