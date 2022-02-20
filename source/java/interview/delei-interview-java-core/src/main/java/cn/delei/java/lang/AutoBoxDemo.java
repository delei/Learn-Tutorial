package cn.delei.java.lang;

import cn.delei.util.PrintUtil;

/**
 * 自动拆装箱
 *
 * @author deleiguo
 */
public class AutoBoxDemo {
    public static void main(String[] args) {
        PrintUtil.printTitle("Integer");
        intTest();
        PrintUtil.printTitle("Double");
        //doubleTest();
    }

    static void intTest() {
        // 自动装箱
        Integer i1 = 127;
        Integer i2 = 127;
        Integer i3 = 128;
        Integer i4 = 128;
        Integer i5 = 0;

        // 构造函数从JDK9开始标记已过时，使用Integer.valueOf替代
        Integer i6 = new Integer(128);
        Integer i7 = new Integer(0);
        System.out.printf("%s == %s:%s \n", i1, i2, i1 == i2);
        System.out.printf("%s == %s:%s \n", i3, i4, i3 == i4);
        System.out.printf("%s == %s:%s \n", i3, i6, i3 == i6);

        // intValue 拆箱
        System.out.printf("i3.intValue() == i6:%s \n", i3.intValue() == i6);
        // 先进行拆箱，+运算，结果数值比较
        System.out.printf("128 == (i4+i5):%s \n", 128 == (i4 + i5));
        System.out.printf("128 == (i6+i7):%s \n", 128 == (i6 + i7));
        // 先进行拆箱，+运算结果后装箱，进行equals比较
        System.out.printf("i6.equals(i6+i7):%s \n", i6.equals(i6 + i7));
    }

    static void ternaryOperationTest() {
        // 三目运算中NPE
        Integer a = 1;

        Integer b = 2;
        Integer c = null;
        Boolean flag = false;
        // 条件运算符是右结合的，也就是说，从右向左分组计算
        // a*b的结果时int类型，那么c会强制拆箱为int类型，抛出NPE
        Integer result = flag ? a * b : c;
        System.out.println("三目运算:" + result);
    }

    static void doubleTest() {
        Double i1 = 100.0;
        Double i2 = 100.0;
        Double i3 = 200.0D;
        Double i4 = 200.0D;
        System.out.printf("%s == %s:%s \n", i1, i2, i1 == i2);
        System.out.printf("%s == %s:%s \n", i3, i4, i3 == i4);
    }
}
