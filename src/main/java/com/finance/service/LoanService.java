package com.finance.service;

import com.finance.dto.request.LoanRequest;
import com.finance.dto.response.LoanResponse;
import org.springframework.data.domain.*;

public interface LoanService {
	LoanResponse create(LoanRequest request);

	LoanResponse getById(Long id);

	Page<LoanResponse> getAll(Pageable pageable);

	LoanResponse update(Long id, LoanRequest request);

	void delete(Long id);
}
