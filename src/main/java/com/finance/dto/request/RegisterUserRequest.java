package com.finance.dto.request;

import jakarta.validation.constraints.*;
import com.finance.enums.Role;

public record RegisterUserRequest(@NotBlank @Size(min = 4, max = 80) String username,
		@NotBlank @Size(min = 8, max = 100) String password, @NotNull Role role) {
}
