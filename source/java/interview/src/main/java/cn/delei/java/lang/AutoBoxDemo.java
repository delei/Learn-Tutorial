package cn.delei.java.lang;

public class AutoBoxDemo {
    public static void main(String[] args) {
        // 自动装箱
        Integer i1 = 127;
        Integer i2 = 127;
        Integer i3 = 128;
        Integer i4 = 128;
        Integer i5 = 0;

        // 构造函数从JDK9开始标记已过时，使用Integer.valueOf替代
        Integer i6 = new Integer(128);
        Integer i7 = new Integer(0);
        System.out.printf("i1 == i2:%s \n", i1 == i2);
        System.out.printf("i3 == i4:%s \n", i3 == i4);
        System.out.printf("i3 == i6:%s \n", i3 == i6);
        // intValue 拆箱
        System.out.printf("i3.intValue() == i6:%s \n", i3.intValue() == i6);
        // 先进性拆箱，+运算，结果数值比较
        System.out.printf("128 == (i4+i5):%s \n", 128 == (i4 + i5));
        System.out.printf("128 == (i6+i7):%s \n", 128 == (i6 + i7));
        // 先进行拆箱，+运算结果后装箱，进行equals比较
        System.out.printf("i6.equals(i6+i7):%s \n", i6.equals(i6 + i7));


    }
}
