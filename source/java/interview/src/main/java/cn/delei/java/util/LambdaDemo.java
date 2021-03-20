package cn.delei.java.util;

import java.util.Arrays;
import java.util.List;

/**
 * Lambda Demo
 * @author deleiguo
 */
public class LambdaDemo {

    @FunctionalInterface
    interface InterFaceA {
        default void say() {
            System.err.println("default say");
        }

        void methodA();
    }

    public static void main(String[] args) {
        List<String> dataList = Arrays.asList("a", "b", "c");
        dataList.forEach(t -> t.length());
        dataList.forEach(String::length);
        dataList.forEach(System.out::println);

        Arrays.asList("a", "b", "c").sort((t1, t2) -> t1.compareTo(t2));

        InterFaceA interFaceA = () -> System.err.println("");
        interFaceA.say();
    }
}
