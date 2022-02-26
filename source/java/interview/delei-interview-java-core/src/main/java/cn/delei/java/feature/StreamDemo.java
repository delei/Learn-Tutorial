package cn.delei.java.feature;

import cn.delei.pojo.Person;
import cn.delei.util.PrintUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
        List<Integer> intList = Arrays.asList(20, 10, 11, 9, 188, 35, 31);
        List<Person> objectList = personData(15);
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

        // 重用 Stream，使用 get 方法每次获取一个新的 stream
        Supplier<Stream<String>> strStreamSupplier = () -> strList.stream();
        Supplier<Stream<Integer>> intStreamSupplier = () -> intList.stream();
        Supplier<Stream<Person>> objectStreamSupplier = () -> objectList.stream();

        // ==> forEach
        PrintUtil.printDivider("forEach");
        strStreamSupplier.get().forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();
        strStreamSupplier.get().forEach(System.out::print);
        System.out.println();

        // ==> filter
        PrintUtil.printDivider("filter");
        long count = strStreamSupplier.get().filter(s -> "deleiguo".equals(s)).count();
        System.out.println("filter count:" + count);
        strStreamSupplier.get().filter(s -> s.startsWith("Java")).forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();
        objectStreamSupplier.get().filter(o -> o.getAge() > 40).forEach(System.out::println);

        // ==> limit
        PrintUtil.printDivider("limit");
        strStreamSupplier.get().limit(3).forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();

        // ==> map
        PrintUtil.printDivider("map");
        strStreamSupplier.get().map(s -> s + "-after").forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();
        strStreamSupplier.get().map(String::toUpperCase).forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();

        // ==> match
        PrintUtil.printDivider("match");
        System.out.println(strStreamSupplier.get().anyMatch(s -> s.startsWith("Java")));
        System.out.println(strStreamSupplier.get().allMatch(s -> s.startsWith("Java")));
        System.out.println(objectStreamSupplier.get().allMatch(s -> s.getName().startsWith("P")));

        // ==> skip
        PrintUtil.printDivider("skip");
        strStreamSupplier.get().skip(2).forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println();

        // ==> max
        PrintUtil.printDivider("max");
        Integer max = intStreamSupplier.get().max(Integer::compareTo).get();
        System.out.println("max=" + max);
        Person maxAgePerson = objectStreamSupplier.get().max(Comparator.comparingInt(Person::getAge)).get();
        System.out.println("max age=" + maxAgePerson.getAge());

        // ==> sorted
        PrintUtil.printDivider("sorted");
        intStreamSupplier.get().sorted().forEach(System.out::print);
        System.out.println();
        objectStreamSupplier.get().sorted(Comparator.comparingInt(Person::getAge)).forEach(System.out::print);
        System.out.println();

        // ==> collect
        PrintUtil.printDivider("sorted");
        strStreamSupplier.get().filter(s -> s.startsWith("Java")).collect(Collectors.toList()).forEach(System.out::print);
        System.out.println();
        objectStreamSupplier.get().filter(o -> o.getAge() > 30).collect(Collectors.toList()).forEach(System.out::print);
        System.out.println();
    }

    static List<Person> personData(int capacity) {
        if (capacity < 1) {
            return null;
        }
        List<Person> data = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            data.add(new Person("P" + i, RandomUtil.randomInt(1, 100), null));
        }
        return data;
    }
}
