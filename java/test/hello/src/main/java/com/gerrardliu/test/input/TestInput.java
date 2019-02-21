package com.gerrardliu.test.input;

import java.io.Console;

public class TestInput {
    public static void test(String[] args) {
        //如果是从IDE运行，console为null
        Console console = System.console();
        if (console == null) {
            System.err.println("Couldn't get Console instance, maybe you're running this from within an IDE?");
            System.exit(0);
        }
        //从控制台读取输入
        String username = console.readLine("user name:");
        char[] password = console.readPassword("Password:"); //没有回显
        System.out.println("username=" + username);
        System.out.println("password=" + new String(password));
    }
}
