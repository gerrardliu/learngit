package com.gerrardliu.test.array;

import java.util.Arrays;

public class TestArray {

    public static void test(String[] args) {
        //这种初始化不需要使用new
        int[] smallPrimes = {2, 3, 5, 7, 11, 13};
        //两个引用指向同一个地址
        int[] luckyNumbers = smallPrimes;
        //拷贝到一个新数组中
        int[] copiedLuckyNumbers = Arrays.copyOf(smallPrimes, smallPrimes.length);

        //打印原始数组
        System.out.println(Arrays.toString(smallPrimes)); //如果是二维数组，使用Arrays.deepToString()打印
        System.out.println(Arrays.toString(luckyNumbers));
        System.out.println(Arrays.toString(copiedLuckyNumbers));

        //修改第一个数组中的元素
        smallPrimes[5] = 12;

        //第二个数组中的元素也改了，第三个不变
        System.out.println("after modification:");
        System.out.println(Arrays.toString(smallPrimes));
        System.out.println(Arrays.toString(luckyNumbers)); //
        System.out.println(Arrays.toString(copiedLuckyNumbers));
    }
}
