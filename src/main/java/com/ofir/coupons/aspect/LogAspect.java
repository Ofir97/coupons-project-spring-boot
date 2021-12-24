package com.ofir.coupons.aspect;

import java.io.IOException;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.ofir.coupons.annotations.LogOperation;
import com.ofir.coupons.utils.Logger;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
	
	private final Logger logger;

	@AfterReturning("@annotation(logOperation)")
	public void logOperationToFile(LogOperation logOperation) {
		try {
			logger.logOperationAndPrint(logOperation.msg());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@AfterThrowing(pointcut = "within(com.ofir.coupons..*)", throwing = "ex")
	public void logExceptionToFile(Throwable ex) {
		try {
			logger.logException(ex.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
