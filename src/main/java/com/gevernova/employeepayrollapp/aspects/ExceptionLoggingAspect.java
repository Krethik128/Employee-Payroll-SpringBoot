package com.gevernova.employeepayrollapp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ExceptionLoggingAspect {
   //centralized logging of exceptions
    private static final Logger logger = Logger.getLogger(ExceptionLoggingAspect.class.getName());

    // This pointcut matches all methods in your service package
    @AfterThrowing(pointcut = "execution(* com.gevernova.employeepayrollapp.services..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.severe("Exception in " + className + "." + methodName + "(): " + ex.getMessage());
    }
}

