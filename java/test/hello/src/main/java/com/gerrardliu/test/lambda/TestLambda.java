package com.gerrardliu.test.lambda;

import java.util.Arrays;
import java.util.Comparator;

public class TestLambda {
    public static void test(String[] args) {
        //test1();
        //test2();
        test3();
        //test4();
    }

    //测试1: 无参数的lambda表达式
    // repeat1的第二个参数是lambda表达式，类型为预定义的函数式接口Runable
    public static void test1() {
        repeat1(10, () -> System.out.println("hello world"));
    }

    //测试2: 有一个参数的lambda表达式
    // repeat2的第二个参数是lambda表达式，类型为自定义的函数式接口
    public static void test2() {
        repeat2(10, i -> System.out.println("count down:" + (9 - i)));
    }

    //测试3: 按字符串长短排序
    //Arrays.sort第二个参数需要一个Comparator实例
    //Comparator是只有一个方法的接口，可以提供一个lambda表达式
    public static void test3() {
        String[] words = {"abc", "ab", "abcd", "a"};
        //Arrays.sort(words, (first, second) -> first.length() - second.length());
        //Arrays.sort(words, Comparator.comparingInt(word -> word.length()));
        Arrays.sort(words, Comparator.comparingInt(String::length).reversed());
        System.out.println(Arrays.toString(words));
    }

    //测试4:方法引用
    //
    public static void test4() {
        String[] words = {"abc", "Ab", "abcd", "a"};
        //String::compareToIgnoreCase等同于(x,y)->x.compareToIgnoreCase(y)
        Arrays.sort(words, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(words));
    }

    //函数式接口:
    //对于只有一个抽象方法的接口，需要这种接口的对象时，就可以提供一个lambda表达式。这种接口叫做函数式接口。
    //Runnable是一个常用的函数式接口
    public static void repeat1(int n, Runnable action) {
        for (int i = 0; i < n; i++) action.run();
    }

    //自定义函数式接口:
    //下面的注解不是必须的，任何有一个抽象方法的接口都是函数式接口
    //如果自己设计的接口，其中只有一个抽象方法，使用下面的注解有两个好处：
    //1）如果无意中添加了另一个非抽象方法，编译器会报错
    //2) javadoc页里会指出你的接口是一个函数式接口
    @FunctionalInterface
    public interface IntConsumer {
        void accept(int value);
    }

    //IntConsumer是自定义的接口
    public static void repeat2(int n, IntConsumer action) {
        for (int i = 0; i < n; i++) action.accept(i);
    }
}
