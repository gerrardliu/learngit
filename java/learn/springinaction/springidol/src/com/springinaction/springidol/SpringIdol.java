package com.springinaction.springidol;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIdol {
    public static void main(String[] args) {
        System.out.println("hello");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");

        Performer duke = (Performer) ctx.getBean("duke");
        duke.perform();

        Performer kenny = (Performer) ctx.getBean("kenny");
        kenny.perform();

        Performer benny = (Performer) ctx.getBean("benny");
        benny.perform();

        Performer hank = (Performer) ctx.getBean("hank");
        hank.perform();

    }
}
