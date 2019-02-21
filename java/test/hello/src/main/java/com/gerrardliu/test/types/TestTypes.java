package com.gerrardliu.test.types;

public class TestTypes {
    public static void test(String[] args) {
        testFloat();
        testChar();
        testRound();
        testString();
    }

    //浮点型不准确
    private static void testFloat() {
        //二进制系统中无法精确的表示1/10，就像十进制系统中无法精确的表示1/3一样
        System.out.println(2.0 - 1.1);  //这个值不是0.9
    }

    //char类型的表示
    private static void testChar() {
        //char类型可以表示为十六进制unicode，可以出现在加引号的字符或字符串中
        System.out.println("Java\u2122");
        System.out.println('\u03C0');
    }

    //小数的舍入
    private static void testRound() {
        double x = 9.997;
        int nx = (int) x; //nx == 9， 舍弃小数点后面的部分
        int nx2 = (int) Math.round(x); //nx2 == 10，返回long型
        System.out.println(nx);
        System.out.println(nx2);
    }

    //字符串的遍历
    //java字符串由char值序列组成，char值是采用UTF-16表示unicode码点的代码单元
    //大多数字符使用一个代码单元，而辅助字符使用两个
    private static void testString() {
        //码点D+1D546(𝕆)为一个数学符号，编码为两个代码单元U+D835和U+DD46
        String sentence = "\uD835\uDD46hello";
        System.out.println(sentence);

        System.out.println(sentence.length());  //字符串长度为7
        System.out.println(sentence.codePointCount(0, sentence.length())); //共6个码点

        //打印每个字符
        for (int i = 0; i < sentence.length(); ) {
            int cp = sentence.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp) && i + 1 < sentence.length()) {
                System.out.print(sentence.charAt(i));
                System.out.println(sentence.charAt(i + 1));
                i += 2;
            } else {
                System.out.println(sentence.charAt(i));
                i++;
            }
        }

        //打印每一个codepoint值
        int[] codePoints = sentence.codePoints().toArray();
        for (int i = 0; i < codePoints.length; i++) {
            System.out.println(codePoints[i]);
        }

        //恢复成字符串
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < codePoints.length; i++) {
            builder.appendCodePoint(codePoints[i]);
        }
        System.out.println(builder.toString());
    }
}
