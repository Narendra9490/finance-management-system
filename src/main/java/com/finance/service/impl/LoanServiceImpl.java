package com.finance.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dto.request.LoanRequest;
import com.finance.dto.response.LoanResponse;
import com.finance.entity.Customer;
import com.finance.entity.Loan;
import com.finance.enums.LoanStatus;
import com.finance.exception.ResourceNotFoundException;
import com.finance.mapper.LoanMapper;
import com.finance.repository.CustomerRepository;
import com.finance.repository.LoanRepository;
import com.finance.service.LoanService;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
	private final LoanRepository repository;
	private final LoanMapper mapper;
	private final CustomerRepository customers;

	public LoanServiceImpl(LoanRepository repository, LoanMapper mapper, CustomerRepository customers) {
		this.repository = repository;
		this.mapper = mapper;
		this.customers = customers;

	}

	private Loan map(LoanRequest r, Loan e) {
		Customer c = customers.findById(r.customerId())
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		e.setCustomer(c);
		e.setPrincipalAmount(r.principalAmount());
		e.setAnnualInterestRate(r.annualInterestRate());
		e.setTenureMonths(r.tenureMonths());
		if (e.getStatus() == null)
			e.setStatus(LoanStatus.APPLIED);
		return e;
	}

	@Override
	public LoanResponse create(LoanRequest request) {
		Loan e = new Loan();
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "investments", key = "#id", condition = "#id > 0")
	public LoanResponse getById(Long id) {
		Loan e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + id));
		return mapper.toResponse(e);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<LoanResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(mapper::toResponse);
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public LoanResponse update(Long id, LoanRequest request) {
		Loan e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + id));
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public void delete(Long id) {
		Loan e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + id));
		repository.delete(e);
	}
}
