package com.finance.controller;

import com.finance.service.InvestmentService;
import com.finance.dto.request.InvestmentRequest;
import com.finance.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investments")
public class InvestmentController {
	private final InvestmentService service;

	public InvestmentController(InvestmentService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<InvestmentResponse>> create(@Valid @RequestBody InvestmentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Investment created", service.create(request)));
	}

	@GetMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<InvestmentResponse>> get(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success("Investment found", service.getById(id)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<InvestmentResponse>>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		return ResponseEntity
				.ok(ApiResponse.success("Investment list", service.getAll(PageRequest.of(page, size, sort))));
	}

	@PutMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<InvestmentResponse>> update(@PathVariable Long id,
			@Valid @RequestBody InvestmentRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Investment updated", service.update(id, request)));
	}

	@DeleteMapping("/<built-in function id>")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
