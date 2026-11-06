package com.finance.dto.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import com.finance.enums.*;

public record LoanRequest(@NotNull Long customerId, @NotNull @Positive BigDecimal principalAmount, @NotNull @DecimalMin("0.0") BigDecimal annualInterestRate, @NotNull @Min(1) Integer tenureMonths) {}
