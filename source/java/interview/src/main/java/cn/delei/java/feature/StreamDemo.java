package cn.delei.java.feature;

import cn.delei.util.PrintUtil;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Stream API Demo
 *
 * @author deleiguo
 * @since 1.8
 */
public class StreamDemo {
    public static void main(String[] args) {
        List<String> strList = Arrays.asList("delei", "guo", "Java", "PHP", "Go", "JavaScript", "", "", "Java",
                "deleiguo", "Go", "PHP");

        /*
         *  无法重用此 Stream 对象
         *  java.lang.IllegalStateException: stream has already been operated upon or closed
         *
         * 运行如下代码会报上述错误
         * <code>
         *  streamSupplier.get().forEach(System.out::println);
         *  streamSupplier.get().forEach(System.out::println);
         * </code>
         */
        Stream<String> strStream = strList.stream();

        // 重用 Stream，使用 get 方法每次获取一个新的 stream
        Supplier<Stream<String>> streamSupplier = () -> strList.stream();

        // ==> forEach
        PrintUtil.printDivider("forEach");
        streamSupplier.get().forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();
        streamSupplier.get().forEach(System.out::print);
        System.out.println();

        // ==> filter
        PrintUtil.printDivider("filter");
        long count = streamSupplier.get().filter(s -> "deleiguo".equals(s)).count();
        System.out.println("filter count:" + count);
        streamSupplier.get().filter(s -> s.startsWith("Java")).forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();

        // ==> limit
        PrintUtil.printDivider("limit");
        streamSupplier.get().limit(3).forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();

        // ==> map
        PrintUtil.printDivider("map");
        streamSupplier.get().map(s -> s + "-after").forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();

        // ==> match
        PrintUtil.printDivider("match");
        System.out.println(streamSupplier.get().anyMatch(s -> s.startsWith("Java")));
        System.out.println(streamSupplier.get().allMatch(s -> s.startsWith("Java")));

        // ==> skip
        PrintUtil.printDivider("skip");
        streamSupplier.get().skip(2).forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();
    }
}
