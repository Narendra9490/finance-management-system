package com.finance.service.impl;

import com.finance.service.AccountService;
import com.finance.repository.AccountRepository;
import com.finance.mapper.AccountMapper;
import com.finance.entity.Account;
import com.finance.dto.request.AccountRequest;
import com.finance.dto.response.AccountResponse;
import com.finance.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.cache.annotation.*;
import java.util.*;
import java.math.*;
import java.util.UUID;
import com.finance.repository.CustomerRepository;
import com.finance.entity.Customer;

import com.finance.repository.CustomerRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	private final AccountRepository repository;
	private final AccountMapper mapper;
	private final CustomerRepository customers;

	public AccountServiceImpl(AccountRepository repository, AccountMapper mapper, CustomerRepository customers) {
		this.repository = repository;
		this.mapper = mapper;
		this.customers = customers;

	}

	private Account map(AccountRequest r, Account e) {
		Customer c = customers.findById(r.customerId())
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + r.customerId()));
		e.setCustomer(c);
		e.setAccountType(r.accountType());
		if (e.getAccountNumber() == null)
			e.setAccountNumber("FM" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase());
		if (e.getBalance() == null)
			e.setBalance(BigDecimal.ZERO);
		return e;
	}

	@Override
	public AccountResponse create(AccountRequest request) {
		Account e = new Account();
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "investments", key = "#id", condition = "#id > 0")
	public AccountResponse getById(Long id) {
		Account e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
		return mapper.toResponse(e);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AccountResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(mapper::toResponse);
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public AccountResponse update(Long id, AccountRequest request) {
		Account e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public void delete(Long id) {
		Account e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
		repository.delete(e);
	}
}
