package com.springinaction.springidol;

import org.aspectj.lang.ProceedingJoinPoint;

public class Audience {

    public void takeSeats() {
        System.out.println("The audience is taking their seats.");
    }

    public void turnOffCellPhone() {
        System.out.println("The audience is turning off their cellphone");
    }

    public void applaud() {
        System.out.println("clap clap clap");
    }

    public void demandRefund() {
        System.out.println("Boo! We want our money back!");
    }

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
