package com.finance.entity;

import com.finance.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import com.finance.entity.Customer;
import com.finance.enums.LoanStatus;

@Entity
@Table(name = "loans")
@NamedQuery(name = "Loan.findByCustomerId", query = "SELECT l FROM Loan l WHERE l.customer.id = :customerId")
@Getter
@Setter
@NoArgsConstructor
public class Loan extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal principalAmount;
	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal annualInterestRate;
	@Column(nullable = false)
	private Integer tenureMonths;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LoanStatus status = LoanStatus.APPLIED;
	private LocalDate startDate;
	private LocalDate nextDueDate;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
}
