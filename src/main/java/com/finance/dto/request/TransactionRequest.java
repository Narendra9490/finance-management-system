package com.finance.dto.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import com.finance.enums.*;

public record TransactionRequest(@NotNull TransactionType type, @NotNull @Positive BigDecimal amount, @NotNull Long sourceAccountId, Long destinationAccountId, String description) {}
