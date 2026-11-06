package com.finance.dto.response;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import com.finance.enums.*;

public record TransactionResponse(Long id, String referenceNumber, TransactionType type, TransactionStatus status, BigDecimal amount, Long sourceAccountId, Long destinationAccountId, LocalDateTime createdAt) {}
