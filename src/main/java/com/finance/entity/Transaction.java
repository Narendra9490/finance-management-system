package com.finance.entity;

import com.finance.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import com.finance.entity.Account;
import com.finance.enums.TransactionType;
import com.finance.enums.TransactionStatus;

@Entity
@Table(name = "transactions", indexes = @Index(name = "idx_transaction_reference", columnList = "reference_number", unique = true))
@NamedQuery(name = "Transaction.findByReferenceNumber", query = "SELECT t FROM Transaction t WHERE t.referenceNumber = :referenceNumber")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "reference_number", nullable = false, unique = true, length = 40)
	private String referenceNumber;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType type;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionStatus status;
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;
	@Column(length = 300)
	private String description;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "source_account_id", nullable = false)
	private Account sourceAccount;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destination_account_id")
	private Account destinationAccount;
}
