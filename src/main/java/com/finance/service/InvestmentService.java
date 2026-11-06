package com.finance.service;

import com.finance.dto.request.InvestmentRequest;
import com.finance.dto.response.InvestmentResponse;
import org.springframework.data.domain.*;

public interface InvestmentService {
	InvestmentResponse create(InvestmentRequest request);

	InvestmentResponse getById(Long id);

	Page<InvestmentResponse> getAll(Pageable pageable);

	InvestmentResponse update(Long id, InvestmentRequest request);

	void delete(Long id);
}
