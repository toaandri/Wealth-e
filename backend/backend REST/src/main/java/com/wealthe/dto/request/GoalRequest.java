package com.wealthe.dto.request;
import com.wealthe.enumeration.GoalStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal; import java.time.LocalDate;
public record GoalRequest(@NotNull Long userId,@NotBlank String name,@NotNull @DecimalMin("0.01") BigDecimal targetAmount,
 @NotNull @DecimalMin("0.00") BigDecimal currentAmount,@NotNull LocalDate deadline,GoalStatus status) {}
