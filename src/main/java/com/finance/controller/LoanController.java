package com.finance.controller;

import com.finance.service.LoanService;
import com.finance.dto.request.LoanRequest;
import com.finance.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
	private final LoanService service;

	public LoanController(LoanService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<LoanResponse>> create(@Valid @RequestBody LoanRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Loan created", service.create(request)));
	}

	@GetMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<LoanResponse>> get(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success("Loan found", service.getById(id)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<LoanResponse>>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		return ResponseEntity.ok(ApiResponse.success("Loan list", service.getAll(PageRequest.of(page, size, sort))));
	}

	@PutMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<LoanResponse>> update(@PathVariable Long id,
			@Valid @RequestBody LoanRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Loan updated", service.update(id, request)));
	}

	@DeleteMapping("/<built-in function id>")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
