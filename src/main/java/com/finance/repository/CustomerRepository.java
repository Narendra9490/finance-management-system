package com.finance.repository;

import com.finance.entity.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import java.util.*;
import java.time.*;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByEmail(String email);

	@Query("SELECT c FROM Customer c WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%',:name,'%'))")
	Page<Customer> searchByName(@Param("name") String name, Pageable pageable);
}
