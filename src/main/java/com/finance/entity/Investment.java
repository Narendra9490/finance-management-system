package com.finance.entity;

import com.finance.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import com.finance.entity.Customer;
import com.finance.enums.InvestmentType;

@Entity
@Table(name = "investments")
@Getter
@Setter
@NoArgsConstructor
public class Investment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private InvestmentType investmentType;
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal principalAmount;
	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal expectedAnnualReturn;
	private LocalDate startDate;
	private LocalDate maturityDate;
	@Column(nullable = false)
	private boolean matured = false;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
}
