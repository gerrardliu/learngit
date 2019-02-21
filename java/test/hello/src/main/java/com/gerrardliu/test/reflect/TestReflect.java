package com.gerrardliu.test.reflect;

import com.gerrardliu.test.HelloWorld;

public class TestReflect {
    public static void test(String[] args) {
        //第一个参数是类名全路径，含完整包名的
        if (args.length < 1) {
            System.err.printf("usage: exe ClassName%n");
            System.exit(1); //这会终止整个虚拟机
        }
        //找到指定的类
        Class<?> cl = null;
        try {
            cl = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("class not found");
            System.exit(1);
        }
        //实例化找到的类
        HelloWorld hello = null;
        try {
            hello = (HelloWorld) cl.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(hello.sayHello());
    }
}
