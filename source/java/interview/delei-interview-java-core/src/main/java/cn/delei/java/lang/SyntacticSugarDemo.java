package cn.delei.java.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * 语法糖
 *
 * @author deleiguo
 */
public class SyntacticSugarDemo {

    public static void main(String[] args) {
        switchString();
        autoBoxing();
        assertDemo();
    }

    /**
     * switch 对 String 支持
     */
    static void switchString() {
        String a = "1";
        switch (a) {
            case "1":
                System.out.println(a);
                break;
            case "2":
                System.out.println("2");
                break;
            default:
                break;
        }
    }

    /**
     * 自动拆箱装箱
     */
    static void autoBoxing() {
        Integer i1 = 10;
        int i2 = 11;
        Integer i3 = i2;
    }

    /**
     * 断言
     */
    static void assertDemo() {
        int a = 1;
        int b = 1;
        assert a == b;
        assert a != b : "Hollis";
    }

    /**
     * 可变参数
     */
    static void varargsParameter(String... params) {
        System.out.println(params.length);
    }

    static void forEach() {
        List<String> dataList = new ArrayList<>();
        dataList.add("1");
        dataList.add("1");
        dataList.add("1");
        dataList.add("1");
        dataList.forEach(System.out::println);
    }
}
