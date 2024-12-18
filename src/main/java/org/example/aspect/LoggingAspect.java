package org.example.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Around("execution(* org.example.service.impl.*.*(..))")
    public String log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Начало выполнения метода");
        String result = (String) proceedingJoinPoint.proceed();
        logger.info("Конец выполнения метода");
        return result;
    }
}
