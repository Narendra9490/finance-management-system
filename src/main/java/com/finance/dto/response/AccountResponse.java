package com.finance.dto.response;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import com.finance.enums.*;

public record AccountResponse(Long id, String accountNumber, AccountType accountType, AccountStatus status, BigDecimal balance, Long customerId, LocalDateTime createdAt) {}
