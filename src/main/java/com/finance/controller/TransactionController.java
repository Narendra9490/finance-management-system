package com.finance.controller;

import com.finance.service.TransactionService;
import com.finance.dto.request.TransactionRequest;
import com.finance.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	private final TransactionService service;

	public TransactionController(TransactionService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<TransactionResponse>> create(@Valid @RequestBody TransactionRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Transaction created", service.create(request)));
	}

	@GetMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<TransactionResponse>> get(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success("Transaction found", service.getById(id)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<TransactionResponse>>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		return ResponseEntity
				.ok(ApiResponse.success("Transaction list", service.getAll(PageRequest.of(page, size, sort))));
	}

	@PutMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<TransactionResponse>> update(@PathVariable Long id,
			@Valid @RequestBody TransactionRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Transaction updated", service.update(id, request)));
	}

	@DeleteMapping("/<built-in function id>")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
