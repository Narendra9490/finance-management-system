package com.finance.controller;

import com.finance.service.CustomerService;
import com.finance.dto.request.CustomerRequest;
import com.finance.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<CustomerResponse>> create(@Valid @RequestBody CustomerRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Customer created", service.create(request)));
	}

	@GetMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<CustomerResponse>> get(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success("Customer found", service.getById(id)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<CustomerResponse>>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		return ResponseEntity
				.ok(ApiResponse.success("Customer list", service.getAll(PageRequest.of(page, size, sort))));
	}

	@PutMapping("/<built-in function id>")
	public ResponseEntity<ApiResponse<CustomerResponse>> update(@PathVariable Long id,
			@Valid @RequestBody CustomerRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Customer updated", service.update(id, request)));
	}

	@DeleteMapping("/<built-in function id>")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
