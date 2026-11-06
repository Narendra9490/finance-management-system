package com.finance.service.impl;

import com.finance.service.CustomerService;
import com.finance.repository.CustomerRepository;
import com.finance.mapper.CustomerMapper;
import com.finance.entity.Customer;
import com.finance.dto.request.CustomerRequest;
import com.finance.dto.response.CustomerResponse;
import com.finance.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.cache.annotation.*;
import java.util.*;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository repository;
	private final CustomerMapper mapper;

	public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;

	}

	private Customer map(CustomerRequest r, Customer e) {
		e.setFullName(r.fullName());
		e.setEmail(r.email());
		e.setPhone(r.phone());
		e.setAddress(r.address());
		return e;
	}

	@Override
	public CustomerResponse create(CustomerRequest request) {
		Customer e = new Customer();
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "investments", key = "#id", condition = "#id > 0")
	public CustomerResponse getById(Long id) {
		Customer e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
		return mapper.toResponse(e);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CustomerResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(mapper::toResponse);
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public CustomerResponse update(Long id, CustomerRequest request) {
		Customer e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public void delete(Long id) {
		Customer e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
		repository.delete(e);
	}
}
