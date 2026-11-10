package com.wealthe.dto.response;
import com.wealthe.enumeration.AssetType; import java.math.BigDecimal;
public record AssetResponse(Long id,Long userId,String name,AssetType type,BigDecimal value) {}
