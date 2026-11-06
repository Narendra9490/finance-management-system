package com.finance.entity;

import com.finance.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import com.finance.entity.Customer;
import com.finance.enums.AccountType;
import com.finance.enums.AccountStatus;

@Entity
@Table(name = "accounts", indexes = @Index(name = "idx_account_number", columnList = "account_number", unique = true))
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "account_number", nullable = false, unique = true, length = 24)
	private String accountNumber;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountType accountType;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountStatus status = AccountStatus.ACTIVE;
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal balance = BigDecimal.ZERO;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
}
