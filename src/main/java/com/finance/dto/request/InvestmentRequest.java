package com.finance.dto.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import com.finance.enums.*;

public record InvestmentRequest(@NotNull Long customerId, @NotNull InvestmentType investmentType, @NotNull @Positive BigDecimal principalAmount, @NotNull @DecimalMin("0.0") BigDecimal expectedAnnualReturn, LocalDate startDate, LocalDate maturityDate) {}
