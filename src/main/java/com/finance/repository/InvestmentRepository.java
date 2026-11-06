package com.finance.repository;

import com.finance.entity.Investment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import java.util.*;
import java.time.*;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
	List<Investment> findByCustomerId(Long customerId);

	@Query("SELECT i FROM Investment i WHERE i.matured = false AND i.maturityDate <= :date")
	List<Investment> findMaturingInvestments(@Param("date") LocalDate date);
}
