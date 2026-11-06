package com.finance.mapper;

import com.finance.entity.Transaction;
import com.finance.dto.response.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
	public TransactionResponse toResponse(Transaction e) {
		return new TransactionResponse(e.getId(), e.getReferenceNumber(), e.getType(), e.getStatus(), e.getAmount(),
				e.getSourceAccount().getId(),
				e.getDestinationAccount() == null ? null : e.getDestinationAccount().getId(), e.getCreatedAt());
	}
}
