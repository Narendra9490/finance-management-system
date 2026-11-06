package com.finance.repository;

import com.finance.entity.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import java.util.*;
import java.time.*;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query(name = "Transaction.findByReferenceNumber")
	Optional<Transaction> findByReferenceNumber(@Param("referenceNumber") String referenceNumber);

	Page<Transaction> findBySourceAccountIdOrDestinationAccountId(Long sourceId, Long destinationId, Pageable pageable);

	@Query(value = "SELECT type, SUM(amount) AS total_amount FROM transactions WHERE status='SUCCESS' GROUP BY type", nativeQuery = true)
	List<Object[]> summarizeByTypeNative();
}
