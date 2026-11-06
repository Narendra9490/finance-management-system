package com.finance.service.impl;

import com.finance.service.TransactionService;
import com.finance.repository.TransactionRepository;
import com.finance.mapper.TransactionMapper;
import com.finance.entity.Transaction;
import com.finance.dto.request.TransactionRequest;
import com.finance.dto.response.TransactionResponse;
import com.finance.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.cache.annotation.*;
import java.util.*;
import java.math.*;
import java.util.UUID;
import com.finance.enums.*;
import com.finance.repository.AccountRepository;
import com.finance.entity.Account;

import com.finance.repository.AccountRepository;
import com.finance.enums.*;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	private final TransactionRepository repository;
	private final TransactionMapper mapper;

	private final AccountRepository accounts;

	public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper,
			AccountRepository accounts) {
		this.repository = repository;
		this.mapper = mapper;

		this.accounts = accounts;
	}

	private Transaction map(TransactionRequest r, Transaction e) {
		Account source = accounts.findByIdForUpdate(r.sourceAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Source account not found"));
		if (r.amount().compareTo(BigDecimal.ZERO) <= 0)
			throw new BusinessException("Amount must be positive");
		e.setType(r.type());
		e.setAmount(r.amount());
		e.setDescription(r.description());
		e.setSourceAccount(source);
		if (r.type() == TransactionType.DEPOSIT) {
			source.setBalance(source.getBalance().add(r.amount()));
		} else if (r.type() == TransactionType.WITHDRAWAL) {
			if (source.getBalance().compareTo(r.amount()) < 0)
				throw new BusinessException("Insufficient balance");
			source.setBalance(source.getBalance().subtract(r.amount()));
		} else if (r.type() == TransactionType.TRANSFER) {
			if (r.destinationAccountId() == null)
				throw new BusinessException("Destination account is required for transfer");
			Account dest = accounts.findByIdForUpdate(r.destinationAccountId())
					.orElseThrow(() -> new ResourceNotFoundException("Destination account not found"));
			if (source.getId().equals(dest.getId()))
				throw new BusinessException("Source and destination accounts must differ");
			if (source.getBalance().compareTo(r.amount()) < 0)
				throw new BusinessException("Insufficient balance");
			source.setBalance(source.getBalance().subtract(r.amount()));
			dest.setBalance(dest.getBalance().add(r.amount()));
			e.setDestinationAccount(dest);
		} else
			throw new BusinessException("Unsupported transaction type for posting");
		e.setReferenceNumber("TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase());
		e.setStatus(TransactionStatus.SUCCESS);
		return e;
	}

	@Override
	public TransactionResponse create(TransactionRequest request) {
		Transaction e = new Transaction();
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "investments", key = "#id", condition = "#id > 0")
	public TransactionResponse getById(Long id) {
		Transaction e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));
		return mapper.toResponse(e);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TransactionResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(mapper::toResponse);
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public TransactionResponse update(Long id, TransactionRequest request) {
		Transaction e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));
		map(request, e);
		return mapper.toResponse(repository.save(e));
	}

	@Override
	@CacheEvict(value = "investments", key = "#id")
	public void delete(Long id) {
		Transaction e = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));
		repository.delete(e);
	}
}
