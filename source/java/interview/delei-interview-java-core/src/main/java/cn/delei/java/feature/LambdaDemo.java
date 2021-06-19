package cn.delei.java.feature;

import cn.delei.util.PrintUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Lambda 函数编程 Demo
 *
 * @author deleiguo
 * @since 1.8
 */
public class LambdaDemo {
    public static void main(String[] args) {
        List<String> strList = Arrays.asList("delei", "guo", "Java", "PHP", "Go");
        List<Integer> intList = Arrays.asList(new Integer[]{10, 8, 21, 33});
        // ==> 构造
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " Start");
        }, "T01");

        Collections.sort(intList, Integer::compareTo);
        Collections.sort(intList, (Integer i1, Integer i2) -> {
            return i1 - i2;
        });
        PrintUtil.printDivider("forEach");
        // ==> forEach
        strList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.print(s + " ");
            }
        });
        System.out.println();
        strList.forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(10, 10);
        map.forEach((k, v) -> System.out.println("key=" + k + " ,value=" + v));

        PrintUtil.printDivider("方法引用");
        // ==> 方法引用，由::双冒号操作符标识
        strList.forEach(System.out::println);

        PrintUtil.printDivider("Predicate");
        // ==> Predicate
        Predicate<String> conditon = (str) -> str.length() > 4;
        filter(strList, conditon);
        conditon = (str) -> str.startsWith("delei");
        filter(strList, conditon);
        conditon = (str) -> true;
        filter(strList, conditon);
        conditon = (str) -> false;
        filter(strList, conditon);
    }

    static void filter(List<String> list, Predicate<String> predicate) {
        list.stream().filter((s) -> (predicate.test(s))).forEach((s) -> {
            System.out.println(s + " ");
        });
    }

}


