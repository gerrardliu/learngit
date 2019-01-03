package com.springinaction.springidol;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.Map;

public class SpringIdol {
    public static void main(String[] args) {
        System.out.println("hello");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringIdolConfig.class);

        Map performers = ctx.getBeansOfType(Performer.class);
        Iterator<Map.Entry<String, Performer>> it = performers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Performer> entry = it.next();
            System.out.println(entry.getKey() + " begin:");
            entry.getValue().perform();
        }
    }
}
