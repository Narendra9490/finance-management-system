package com.finance.scheduler;

import com.finance.repository.InvestmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Component
@Slf4j
public class InvestmentScheduler {
	private final InvestmentRepository investments;

	public InvestmentScheduler(InvestmentRepository investments) {
		this.investments = investments;
	}

	@Scheduled(cron = "0 15 8 * * *")
	@Transactional
	public void markMaturedInvestments() {
		var due = investments.findMaturingInvestments(LocalDate.now());
		due.forEach(i -> i.setMatured(true));
		log.info("Marked {} investments as matured", due.size());
	}
}
