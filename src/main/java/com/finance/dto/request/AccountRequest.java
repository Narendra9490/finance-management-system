package com.finance.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import com.finance.enums.*;

public record AccountRequest(@NotNull Long customerId, @NotNull AccountType accountType) {
}
