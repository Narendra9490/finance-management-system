package com.finance.mapper;

import com.finance.entity.Loan;
import com.finance.dto.response.LoanResponse;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {
	public LoanResponse toResponse(Loan e) {
		return new LoanResponse(e.getId(), e.getPrincipalAmount(), e.getAnnualInterestRate(), e.getTenureMonths(),
				e.getStatus(), e.getStartDate(), e.getNextDueDate(), e.getCustomer().getId());
	}
}
