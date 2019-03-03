package com.qu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * AOP for logging information;
 */

@Component
@Aspect
public class LogAspects {
    @Before("execution(* com.qu.aop.Calculator.div(..))")
    public void logStart(JoinPoint joinPoint){ //target method information
        System.out.println(joinPoint.getSignature().getName() + "div start......." + Arrays.asList(joinPoint.getArgs()));
    }

    @After("execution(* com.qu.aop.Calculator.div(..))")
    public void logEnd(JoinPoint joinPoint){
        System.out.printf(joinPoint.getSignature().getName() + "div end........");
    }

    @AfterReturning(pointcut = "execution(* com.qu.aop.Calculator.div(..))",
                    returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result){
        System.out.printf("div result....." + result +"  ");
    }

    @AfterThrowing(pointcut = "execution(* com.qu.aop.Calculator.div(..))",
                    throwing="exception")
    public void logException(JoinPoint joinPoint, Exception exception){
        System.out.println("run exception......" +  exception.getMessage());
    }
}
