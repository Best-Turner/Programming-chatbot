package org.example.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LogManager.getLogger(LoggingAspect.class);


    @Pointcut("execution(* org.example.service..*(..))")
    public void logServiceLayer() {
    }

    @Pointcut("execution(* org.example.repository..*(..))")
    public void logRepositoryLayer() {
    }

    @Before("logServiceLayer()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getStaticPart().getSignature().getDeclaringTypeName();
        logger.debug("Начало работы {} {} с параметрами {}", className, methodName, Arrays.asList(args));
    }

    @After("logServiceLayer()")
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.debug("Метод {} завершил работу", methodName);
    }

    @Around("logRepositoryLayer()")
    public int logRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getStaticPart().getSignature().getDeclaringTypeName();
        logger.debug("Начало работы {} {} с параметрами {}", className, methodName, Arrays.asList(args));
        int proceed = (int) joinPoint.proceed();
        logger.debug("Метод {} завершил работу", methodName);
        return proceed;
    }
}