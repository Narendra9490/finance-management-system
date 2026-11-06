package com.finance.dto.response;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;

public record CustomerResponse(Long id, String fullName, String email, String phone, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {}
