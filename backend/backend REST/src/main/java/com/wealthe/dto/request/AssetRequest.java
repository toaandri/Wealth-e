package com.wealthe.dto.request;
import com.wealthe.enumeration.AssetType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record AssetRequest(@NotNull Long userId,@NotBlank String name,@NotNull AssetType type,@NotNull @DecimalMin("0.00") BigDecimal value) {}
