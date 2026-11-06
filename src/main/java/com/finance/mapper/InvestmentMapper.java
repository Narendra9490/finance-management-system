package com.finance.mapper;

import com.finance.entity.Investment;
import com.finance.dto.response.InvestmentResponse;
import org.springframework.stereotype.Component;

@Component
public class InvestmentMapper {
	public InvestmentResponse toResponse(Investment e) {
		return new InvestmentResponse(e.getId(), e.getInvestmentType(), e.getPrincipalAmount(),
				e.getExpectedAnnualReturn(), e.getStartDate(), e.getMaturityDate(), e.isMatured(),
				e.getCustomer().getId());
	}
}
