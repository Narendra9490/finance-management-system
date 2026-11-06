package com.finance.service;

import com.finance.dto.request.AccountRequest;
import com.finance.dto.response.AccountResponse;
import org.springframework.data.domain.*;

public interface AccountService {
	AccountResponse create(AccountRequest request);

	AccountResponse getById(Long id);

	Page<AccountResponse> getAll(Pageable pageable);

	AccountResponse update(Long id, AccountRequest request);

	void delete(Long id);
}
