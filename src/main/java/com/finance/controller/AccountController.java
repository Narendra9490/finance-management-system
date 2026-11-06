package com.finance.controller;

import com.finance.service.AccountService;
import com.finance.dto.request.AccountRequest;
import com.finance.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	private final AccountService service;

	public AccountController(AccountService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<AccountResponse>> create(@Valid @RequestBody AccountRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Account created", service.create(request)));
	}

	@GetMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<AccountResponse>> get(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success("Account found", service.getById(id)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<AccountResponse>>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		return ResponseEntity.ok(ApiResponse.success("Account list", service.getAll(PageRequest.of(page, size, sort))));
	}

	@PutMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<AccountResponse>> update(@PathVariable Long id,
			@Valid @RequestBody AccountRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Account updated", service.update(id, request)));
	}

	@DeleteMapping("/<built-in function id>")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
