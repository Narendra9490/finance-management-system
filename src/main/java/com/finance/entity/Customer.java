package com.finance.entity;

import com.finance.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import com.finance.entity.Account;
import com.finance.entity.Loan;
import com.finance.entity.Investment;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 120)
	private String fullName;
	@Column(nullable = false, unique = true, length = 150)
	private String email;
	@Column(nullable = false, length = 20)
	private String phone;
	@Column(length = 300)
	private String address;
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Account> accounts = new ArrayList<>();
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Loan> loans = new ArrayList<>();
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Investment> investments = new ArrayList<>();
}
