package com.wealthe.dto.response;
import com.wealthe.enumeration.GoalStatus; import java.math.BigDecimal; import java.time.LocalDate;
public record GoalResponse(Long id,Long userId,String name,BigDecimal targetAmount,BigDecimal currentAmount,BigDecimal progressPercent,LocalDate deadline,GoalStatus status) {}
