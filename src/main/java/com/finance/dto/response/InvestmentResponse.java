package com.finance.dto.response;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import com.finance.enums.*;

public record InvestmentResponse(Long id, InvestmentType investmentType, BigDecimal principalAmount, BigDecimal expectedAnnualReturn, LocalDate startDate, LocalDate maturityDate, boolean matured, Long customerId) {}
