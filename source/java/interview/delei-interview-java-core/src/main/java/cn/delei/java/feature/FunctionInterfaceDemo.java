package cn.delei.java.feature;

import cn.delei.util.PrintUtil;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 函数式编程Demo
 *
 * @author deleiguo
 * @since 1.8
 */
public class FunctionInterfaceDemo {
    public static void main(String[] args) {
        jdkFunction();
        customDefined();
    }

    static void jdkFunction() {
        PrintUtil.printDivider("Consumer");
        Consumer<String> c1 = System.out::println;
        Consumer<String> c2 = s -> System.out.println(s + "-Consumer");
        c1.accept("111");
        c2.accept("222");
        c1.andThen(c1).andThen(c2).accept("3333");

        PrintUtil.printDivider("Function");
        Function<Integer, Integer> f = s -> s * 2;
        Function<Integer, Integer> g = s -> s + 2;
        /*
         * 下面表示在执行F时，先执行G，并且执行F时使用G的输出当作输入。
         * 相当于以下代码：
         * Integer a = g.apply(1);
         * System.out.println(f.apply(a));
         */
        System.out.println(f.compose(g).apply(1));
        /*
         * 表示执行F的Apply后使用其返回的值当作输入再执行G的Apply；
         * 相当于以下代码
         * Integer a = f.apply(1);
         * System.out.println(g.apply(a));
         */
        System.out.println(f.andThen(g).apply(1));
        // identity方法会返回一个不进行任何处理的Function，即输出与输入值相等；
        System.out.println(Function.identity().apply("a"));

        PrintUtil.printDivider("Predicate");
        List<String> strList = Arrays.asList("Java", "JavaScript", "C", "C++", "C#", "PHP", "Go");
        Predicate<String> conditon = (str) -> str.length() > 6;
        filter(strList, conditon);
        filter(strList, s -> s.startsWith("C"));
        Predicate<String> p1 = s -> s.equals("Java");
        Predicate<String> p2 = s -> s.startsWith("JavaScript");
        // negate: 用于对原来的Predicate做取反处理；
        System.out.println(p1.negate().test("JavaScript"));
        // and: 针对同一输入值，多个Predicate均返回True时返回True，否则返回False；
        System.out.println(p1.and(p2).test("JavaScript"));
        // or: 针对同一输入值，多个Predicate只要有一个返回True则返回True，否则返回False
        System.out.println(p1.or(p2).test("Java"));

        PrintUtil.printDivider("Supplier");
        Supplier<String> supplier = () -> "sss";
        System.out.println(supplier.get());
    }

    static void customDefined() {
        PrintUtil.printDivider("FunctionalInterface");
        LambdaFunctionalInterface<String> l1 = s -> s + "-1";
        System.out.println(l1.func("aaa"));
    }

    static void filter(List<String> list, Predicate<String> predicate) {
        list.stream().filter((s) -> (predicate.test(s))).forEach((s) -> {
            System.out.println(s + " #");
        });
        System.out.println("");
    }
}
