package com.springinaction.springidol;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    @Pointcut("execution(* com.springinaction.springidol.Performer.perform(..))")
    public void performance() {
    }

    @Before("performance()")
    public void takeSeats() {
        System.out.println("The audience is taking their seats.");
    }

    @Before("performance()")
    public void turnOffCellPhone() {
        System.out.println("The audience is turning off their cellphone");
    }

    @AfterReturning("performance()")
    public void applaud() {
        System.out.println("clap clap clap");
    }

    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println("Boo! We want our money back!");
    }

    //@Around("performance()")
    public void watchPerformance(ProceedingJoinPoint joinPoint) {
        try {
            takeSeats();
            turnOffCellPhone();
            long start = System.currentTimeMillis();
            joinPoint.proceed();
            long end = System.currentTimeMillis();
            applaud();
            System.out.println("The performance took " + (end - start) + " miliseconds.");
        } catch (Throwable t) {
            demandRefund();
        }
    }
}
