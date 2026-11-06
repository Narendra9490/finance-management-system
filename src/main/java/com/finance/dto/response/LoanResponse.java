package com.finance.dto.response;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import com.finance.enums.*;

public record LoanResponse(Long id, BigDecimal principalAmount, BigDecimal annualInterestRate, Integer tenureMonths, LoanStatus status, LocalDate startDate, LocalDate nextDueDate, Long customerId) {}
