package com.finance.dto.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;

public record CustomerRequest(@NotBlank @Size(max=120) String fullName, @Email @NotBlank String email, @NotBlank String phone, String address) {}
