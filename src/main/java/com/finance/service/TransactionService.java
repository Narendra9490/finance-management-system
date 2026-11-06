package com.finance.service;

import com.finance.dto.request.TransactionRequest;
import com.finance.dto.response.TransactionResponse;
import org.springframework.data.domain.*;

public interface TransactionService {
	TransactionResponse create(TransactionRequest request);

	TransactionResponse getById(Long id);

	Page<TransactionResponse> getAll(Pageable pageable);

	TransactionResponse update(Long id, TransactionRequest request);

	void delete(Long id);
}
