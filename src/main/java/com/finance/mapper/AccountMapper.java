package com.finance.mapper;

import com.finance.entity.Account;
import com.finance.dto.response.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
	public AccountResponse toResponse(Account e) {
		return new AccountResponse(e.getId(), e.getAccountNumber(), e.getAccountType(), e.getStatus(), e.getBalance(),
				e.getCustomer().getId(), e.getCreatedAt());
	}
}
