package com.finance.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	@Pointcut("execution(* com.finance.service..*(..))")
	public void serviceMethods() {
	}

	@Around("serviceMethods()")
	public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			Object result = joinPoint.proceed();
			log.info("{} completed in {} ms", joinPoint.getSignature(), System.currentTimeMillis() - start);
			return result;
		} catch (Throwable ex) {
			log.error("{} failed: {}", joinPoint.getSignature(), ex.getMessage());
			throw ex;
		}
	}
}
