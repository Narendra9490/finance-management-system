package com.finance.controller;

import com.finance.entity.*;
import com.finance.repository.*;
import com.finance.dto.response.ApiResponse;
import com.finance.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/customers/{customerId}/documents")
public class DocumentController {
	private final CustomerRepository customers;
	private final DocumentRepository documents;
	private final Path uploadPath;

	public DocumentController(CustomerRepository c, DocumentRepository d, Path p) {
		customers = c;
		documents = d;
		uploadPath = p;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<Long>> upload(@PathVariable Long customerId,
			@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty())
			throw new IllegalArgumentException("File is empty");
		if (file.getSize() > 10 * 1024 * 1024)
			throw new IllegalArgumentException("Maximum file size is 10MB");
		String type = Objects.requireNonNullElse(file.getContentType(), "");
		if (!(type.equals("application/pdf") || type.startsWith("image/")))
			throw new IllegalArgumentException("Only PDF and image files are allowed");
		Customer customer = customers.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		String stored = UUID.randomUUID() + "_"
				+ Path.of(Objects.requireNonNullElse(file.getOriginalFilename(), "upload")).getFileName();
		Files.copy(file.getInputStream(), uploadPath.resolve(stored), StandardCopyOption.REPLACE_EXISTING);
		Document d = new Document();
		d.setCustomer(customer);
		d.setOriginalFileName(file.getOriginalFilename());
		d.setStoredFileName(stored);
		d.setContentType(type);
		d.setSize(file.getSize());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Document uploaded", documents.save(d).getId()));
	}

	@GetMapping("/{documentId}")
	public ResponseEntity<Resource> download(@PathVariable Long customerId, @PathVariable Long documentId)
			throws Exception {
		Document d = documents.findById(documentId).filter(x -> x.getCustomer().getId().equals(customerId))
				.orElseThrow(() -> new ResourceNotFoundException("Document not found"));
		Resource resource = new UrlResource(uploadPath.resolve(d.getStoredFileName()).toUri());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(d.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + d.getOriginalFileName() + "\"")
				.body(resource);
	}
}
