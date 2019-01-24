package com.gerrardliu.test.school.service;

import com.gerrardliu.test.school.model.School;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class SchoolServiceTest {

    private static ApplicationContext applicationContext;
    private static SchoolService schoolService;

    @BeforeClass
    public static void init() {
        try {
            applicationContext = new ClassPathXmlApplicationContext("school-servlet.xml");
            schoolService = (SchoolService) applicationContext.getBean("schoolService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() {
        List<School> list = schoolService.findAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
        for (School school : list) {
            System.out.println(school.getName());
        }
    }

    @Test
    public void testFindAllByPage() {
        List<School> list = schoolService.findAllByPage(0, 3);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
        for (School school : list) {
            System.out.println(school.getName());
        }
    }

    @Test
    public void testFindById() {
        School school = schoolService.findById(1);
        Assert.assertNotNull(school);
        System.out.println(school.getName());
    }
}
