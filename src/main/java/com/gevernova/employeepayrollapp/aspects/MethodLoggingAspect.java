package com.gevernova.employeepayrollapp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class MethodLoggingAspect {
    // centralized informational logging
    private static final Logger logger = Logger.getLogger(MethodLoggingAspect.class.getName());

    // This pointcut matches all methods in your service package
    private final String servicePointcut = "execution(* com.gevernova.employeepayrollapp.services..*(..))";

    @Before(servicePointcut)
    public void logMethodEntry(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Attempting to execute " + className + "." + methodName + "()");
    }

    @AfterReturning(pointcut = servicePointcut, returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Successfully executed " + className + "." + methodName + "(). Returned: " + (result != null ? result.getClass().getSimpleName() : "void/null"));
    }
}