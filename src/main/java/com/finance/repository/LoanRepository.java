package com.finance.repository;

import com.finance.entity.Loan;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import java.util.*;
import java.time.*;
import com.finance.enums.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	@Query(name = "Loan.findByCustomerId")
	List<Loan> findLoansByCustomer(@Param("customerId") Long customerId);

	@Query("SELECT l FROM Loan l WHERE l.status = :status AND l.nextDueDate <= :dueDate")
	List<Loan> findDueLoans(@Param("status") LoanStatus status, @Param("dueDate") LocalDate dueDate);
}
