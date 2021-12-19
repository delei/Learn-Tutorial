package cn.delei.java;

/**
 * 基础面试题案例
 *
 * @author deleiguo
 */
public class CaseTest {
    public static void main(String[] args) {
        aliyun757502();
    }

    /**
     * [这 10 道 Java 测试题,据说阿里 P7 的正确率只有 50%]
     * https://developer.aliyun.com/article/757502
     */
    private static void aliyun757502() {
        /*
         * Q01: 以下代码的执行结果？
         * 答案：true
         * 题解：首先浮点数是由符号位、指数位、有效数字三个部分组成，而0.125f、0.125d均可以精确的表示出来，
         *      不存在精度丢失，因而a-b==0.0
         */
        float a = 0.125f;
        double b = 0.125d;
        System.out.println((a - b) == 0.0);

        /*
         * Q02: 以下代码的执行结果？
         * 答案：false
         * 题解：类似十进制里面的分数1/3，就是无限循环数，无法精确表示出来，同理浮点数里有些数值也没法精确表示出来
         *      0.8-0.7 = 0.10000000000000009
         *      0.7-0.6 = 0.09999999999999998
         */
        double c = 0.8;
        double d = 0.7;
        double e = 0.6;
        System.out.println((c - d) == (d - e));

        /*
         * Q03: 以下代码的执行结果？
         * 答案：Infinity
         * 题解：在整型运算中，除数是不能为0的，否则直接运行异常。但是在浮点数运算中，引入了无限这个概念
         *      java.lang.Double源码中：
         *      public static final double POSITIVE_INFINITY = 1.0 / 0.0;
         */
        System.out.println(1.0 / 0);

        /*
         * Q04: 以下代码的执行结果？
         * 答案：NaN
         * 题解：NaN表示非数字，它与任何值都不相等，甚至不等于它自己
         *      java.lang.Double 源码中
         *      public static final double NaN = 0.0d / 0.0;
         */
        System.out.println(0.0 / 0.0);

        /*
         * 方法匹配顺序如下：
         *  - 精确匹配
         *  - 基本数据类型（自动转换成更大范围）
         *  - 封装类（自动拆箱与装箱）
         *  - 子类向上转型依次匹配
         *  - 可变参数匹配
         */

        /*
         * Q06: 以下代码的执行最终会调用 InnerTestClass 类的哪个方法？
         * 答案：编译出错，Ambiguous method call. Both
         * 题解：子类向上转型，两者的父类都是object类（null默认类型是object），因而会同时匹配上两者
         */
        InnerTestClass innerTestClass1 = new InnerTestClass();
        //innerTestClass1.f(null);

        /*
         * Q07: 以下代码的执行最终会调用 InnerTestClass 类的哪个方法？
         * 答案：调用 private void f(double d) 方法
         * 题解：基本数据类型（自动转换成更大范围）
         */
        InnerTestClass innerTestClass2 = new InnerTestClass();
        innerTestClass2.f(1);

        /* Q08: 以下代码的编程结果？
         * 答案：编译报错，直接抛出异常
         * 题解：在Java编程语言的设计者的判断中，这比静默跳过整个开关语句要合理，
         *      因为使用null作为开关标签的话，编写的代码将永远不会执行
         */
        String str = null;
        //switch (str) {
        //    case null:
        //        break;
        //}

        /*
         * Q09: 以下的代码调用是否编译正确？
         * 答案: 编译正确
         * 题解: 尖括号里的每个元素都指代一种未知类型，在定义处只具备执行Object方法的能力
         *      在编译期间，所有的泛型信息都会被擦除，编译后，get()的两个参数是Object，返回值也是Object
         */
        //get();

        /*
         * Q10: 如下集合编程时，当往里put 10000个元素时，需要 resize 几次?
         * 答案: 1次
         * 题解: 指定容量构造 HashMap 时，会调用了一次resize方法，即此时内部数据结构的实际容量为16384
         *      计算扩容阀值：16384 * 0.75 = 12288 > 10000 ，当put 第10000个元素，无需扩容,所以为构造时进行了 1 次 resize。
         */
        //HashMap map = new HashMap(10000);
    }

    <String, T, Alibaba> String get(String string, T t) {
        return string;
    }

    static class InnerTestClass {
        private void f(String str) {
            System.out.print("String:" + str);
        }

        private void f(Integer i) {
            System.out.print("Integer:" + i);
        }

        private void f(double d) {
            System.out.print("double:" + d);
        }
    }
}
