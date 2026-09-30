package com.sp.jobportal.aspects;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAndPerformanceAspect {

	// @Around("@annotation(com.sp.jobportal.aspects.LogAspect)")
	@Around("execution(* com.sp.jobportal..*.*(..))")
	public Object logAndMeasureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		String methodName = joinPoint.getSignature().toShortString();
		Object[] methodArgs = joinPoint.getArgs();
		log.info("➡️ Entering method: {}", methodName);
		log.info("📥 Arguments: {}", Arrays.toString(methodArgs));
		// Proceed with actual business method
		Object result = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - startTime;
		log.info("✅ Method executed successfully: {}", methodName);
		log.info("⏱ Execution time: {} ms", executionTime);
		return result;
	}
}
