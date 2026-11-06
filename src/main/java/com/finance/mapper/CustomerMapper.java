package com.finance.mapper;

import com.finance.entity.Customer;
import com.finance.dto.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
	public CustomerResponse toResponse(Customer e) {
		return new CustomerResponse(e.getId(), e.getFullName(), e.getEmail(), e.getPhone(), e.getAddress(),
				e.getCreatedAt(), e.getUpdatedAt());
	}
}
