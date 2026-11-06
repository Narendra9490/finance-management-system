package com.finance.service.impl;

import com.finance.service.InvestmentService;
import com.finance.repository.InvestmentRepository;
import com.finance.mapper.InvestmentMapper;
import com.finance.entity.Investment;
import com.finance.dto.request.InvestmentRequest;
import com.finance.dto.response.InvestmentResponse;
import com.finance.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.cache.annotation.*;
import java.util.*;
import com.finance.repository.CustomerRepository;
import com.finance.entity.Customer;

import com.finance.repository.CustomerRepository;

@Service
@Transactional
public class InvestmentServiceImpl implements InvestmentService {
	private final InvestmentRepository repository;
	private final InvestmentMapper mapper;
	private final CustomerRepository customers;

	public InvestmentServiceImpl(InvestmentRepository repository, InvestmentMapper mapper,
			CustomerRepository customers) {
		this.repository = repository;
		this.mapper = mapper;
		this.customers = customers;

	}

	private Investment map(InvestmentRequest r, Investment e) {
		Customer c = customers.findById(r.customerId())
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		e.setCustomer(c);
		e.setInvestmentType(r.investmentType());
		e.setPrincipalAmount(r.principalAmount());
		e.setExpectedAnnualReturn(r.expectedAnnualReturn());
		e.setStartDate(r.startDate());
		e.setMaturityDate(r.maturityDate());
		return e;
	}

	@Override
	public InvestmentResponse create(InvestmentRequest request) {
		Investment e = new Investment();
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "investments", key = "#id", condition = "#id > 0")
	public InvestmentResponse getById(Long id) {
		Investment e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Investment not found: " + id));
		return mapper.toResponse(e);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<InvestmentResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(mapper::toResponse);
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public InvestmentResponse update(Long id, InvestmentRequest request) {
		Investment e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Investment not found: " + id));
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public void delete(Long id) {
		Investment e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Investment not found: " + id));
		repository.delete(e);
	}
}
