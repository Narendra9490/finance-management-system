package com.finance.service;

import com.finance.dto.request.CustomerRequest;
import com.finance.dto.response.CustomerResponse;
import org.springframework.data.domain.*;

public interface CustomerService {
	CustomerResponse create(CustomerRequest request);

	CustomerResponse getById(Long id);

	Page<CustomerResponse> getAll(Pageable pageable);

	CustomerResponse update(Long id, CustomerRequest request);

	void delete(Long id);
}
