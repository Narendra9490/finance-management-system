package com.finance.scheduler;

import com.finance.repository.LoanRepository;
import com.finance.enums.LoanStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@Slf4j
public class LoanScheduler {
	private final LoanRepository loans;

	public LoanScheduler(LoanRepository loans) {
		this.loans = loans;
	}

	@Scheduled(cron = "0 0 8 * * *")
	public void reportDueLoans() {
		var due = loans.findDueLoans(LoanStatus.ACTIVE, LocalDate.now().plusDays(7));
		log.info("Loans due within 7 days: {}", due.size());
	}
}
