package com.finance.config;

import org.springframework.context.annotation.*;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@Configuration
public class AuditingConfig {
	@Bean
	public AuditorAware<String> auditorAware() {
		return () -> {
			var auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal()))
				return Optional.of("SYSTEM");
			return Optional.of(auth.getName());
		};
	}
}
