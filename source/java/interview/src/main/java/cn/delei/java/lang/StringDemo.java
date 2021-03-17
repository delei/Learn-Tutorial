package cn.delei.java.lang;

import cn.delei.PrintUtil;
import cn.hutool.core.date.StopWatch;

import java.nio.charset.Charset;

public class StringDemo {
    public static void main(String[] args) {
//        stringObject();
//        stringIntern();
//        stringSplice();
//        stringNewMethod();
//        stringLength();
//        stringAppend(100);
//        stringAppend(10000);
//        stringAppend(100000);
    }

    /**
     * 构造
     */
    static void stringObject() {
        PrintUtil.printDivider("String Object");
        String a = "hello"; // 放在常量池中
        String b = "hello"; //  从常量池中查找

        String newA = new String("hello"); // newA 为一个引用
        String newB = new String("hello"); // newB为另一个引用,对象的内容一样

        System.out.println("a==b ? :" + (a == b));
        System.out.println("newA==newB ? :" + (newA == newB));
        System.out.println("a==newA ? :" + (a == newA));
        // String.equals 是重写过的
        System.out.println("a.equals(b) ? :" + (a.equals(b)));
        System.out.println("a.equals(newA) ? :" + (a.equals(newA)));
    }

    /**
     * intern
     */
    static void stringIntern() {
        PrintUtil.printDivider("String Object intern method");
        String a = "hello";
        String b = "hello";
        String newA = new String("hello");
        String newB = new String("hello");
        System.out.println("a.intern()==b.intern() ? : " + (a.intern() == b.intern()));
        System.out.println("newA.intern()==newB.intern() ? :" + (newA.intern() == newB.intern()));
        System.out.println("a.intern()==newA.intern() ? :" + (a.intern() == newA.intern()));
        System.out.println("a=a.intern() ? :" + (a == a.intern()));
        System.out.println("newA==newA.intern() ? : " + (newA == newA.intern()));
    }

    /**
     * 字符串拼接
     */
    static void stringSplice() {
        PrintUtil.printDivider("String Object splice");
        String a = "hello";
        String b = "hello";
        String c = "hel";
        String d = "lo";
        final String finalc = "hel";
        final String finalgetc = getc();

        System.out.println("a==\"hel\"+\"lo\" ? :" + (a == "hel" + "lo"));
        System.out.println("a==c+d ? : " + (a == c + d));
        System.out.println("a==c+\"lo\" ? : " + (a == c + "lo"));
        System.out.println("a==finalc+\"lo\" ? :" + (a == finalc + "lo"));
        System.out.println("a==finalgetc+\"lo\" ? :" + (a == finalgetc + "lo"));
    }

    /**
     * 11 新增的一些方法
     */
    static void stringNewMethod() {
        PrintUtil.printDivider("Since JDK11 String method");
        String str = " woshidage ";
        String newA = new String("");
        System.out.printf("首位空白:%s\n", str.strip());
        System.out.printf("去除尾部空白:%s\n", str.stripTrailing());
        System.out.printf("去除首部空白:%s\n", str.stripLeading());
        System.out.printf("复制几遍字符串:%s\n", str.repeat(2));
        System.out.printf("行数统计:%s\n", str.lines().count());
        str = "";
        System.out.printf("判断字符串是空白:%s\n", str.isBlank());
        System.out.printf("判断字符串是否为空:%s\n", str.isEmpty());
        str = " ";
        System.out.printf("空格字符串是空白:%s\n", str.isBlank());
        System.out.printf("空格字符串是否为空:%s\n", str.isEmpty());
        str = null;
        System.out.printf("null字符串是空白:%s\n", str.isBlank());
        System.out.printf("null字符串是否为空:%s\n", str.isEmpty());
    }

    /**
     * 字符串长度
     */
    static void stringLength() {
        PrintUtil.printDivider("Since length");
        System.out.printf("获取系统默认的字符编码:%s\n", Charset.defaultCharset());
        String str = "aa112";
        String charsetName = "UTF-8";
        System.out.printf("英文数字字符串长度:%s\n", str.length());
        System.out.printf("英文数字字节长度:%s\n", str.getBytes(Charset.forName(charsetName)).length);
        str = "中国";
        System.out.printf("中文字符串长度:%s\n", str.length());
        System.out.printf("中文字节长度:%s\n", str.getBytes(Charset.forName(charsetName)).length);
        str = null;
        System.out.printf("null字符串长度:%s\n", str.length());
    }

    /**
     * 效率
     *
     * @return
     */
    static void stringAppend(int size) {
        // String 效率最低
        // StringBuilder 和 StringBuffer 没有绝对的效率优先，看实际的处理性能，但整体相差不大
        size = size > 0 ? size : 100000;
        StopWatch stopWatch = new StopWatch("String生成数据量:" + size);
        stopWatch.start("String +");
        String str = "a";
        for (int i = 0; i < size; i++) {
            str += i;
        }
        stopWatch.stop();

        stopWatch.start("String concat");
        str = "a";
        for (int i = 0; i < size; i++) {
            str.concat(i + "");
        }
        stopWatch.stop();

        stopWatch.start("StringBuilder append");
        // 确定大小的情况下，指定大小，避免扩容的性能
        StringBuilder stringBuilder = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            stringBuilder.append(i);
        }
        stopWatch.stop();

        stopWatch.start("StringBuffer append");
        StringBuffer stringBuffer = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            stringBuffer.append(i);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    static String getc() {
        return "hel";
    }

}
