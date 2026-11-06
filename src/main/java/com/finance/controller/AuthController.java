package com.finance.controller;

import com.finance.dto.request.RegisterUserRequest;
import com.finance.entity.AppUser;
import com.finance.repository.UserRepository;
import com.finance.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final UserRepository users;
	private final PasswordEncoder encoder;

	public AuthController(UserRepository users, PasswordEncoder encoder) {
		this.users = users;
		this.encoder = encoder;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterUserRequest r) {
		if (users.findByUsername(r.username()).isPresent())
			return ResponseEntity.badRequest().body(ApiResponse.success("Username already exists", null));
		AppUser u = new AppUser();
		u.setUsername(r.username());
		u.setPassword(encoder.encode(r.password()));
		u.setRole(com.finance.enums.Role.CUSTOMER);
		users.save(u);
		return ResponseEntity.status(HttpStatus.CREATED).body(
				ApiResponse.success("User registered. Use HTTP Basic for authenticated requests.", u.getUsername()));
	}

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<String>> me(org.springframework.security.core.Authentication auth) {
		return ResponseEntity.ok(ApiResponse.success("Authenticated user", auth.getName()));
	}
}
