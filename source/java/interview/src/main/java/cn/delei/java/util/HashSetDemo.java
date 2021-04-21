package cn.delei.java.util;

import cn.delei.pojo.Person;
import cn.delei.util.PrintUtil;

import java.util.HashSet;

/**
 * HashSet Demo
 *
 * @author deleiguo
 */
public class HashSetDemo {
    public static void main(String[] args) {
        add();
    }

    static void add() {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(null);
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(1);
        hashSet.add(null);
        hashSet.add(10);
        hashSet.forEach(System.out::println);
        PrintUtil.printDivider();
        HashSet<Person> personHashSet = new HashSet<>();
        
        personHashSet.add(new Person("deleiguo", 28, "111111"));
        personHashSet.add(new Person("deleiguo", 28, "222222"));
        personHashSet.add(new Person("delei", 28, "222222"));
        personHashSet.add(new Person("guo", 28, "111111"));
        personHashSet.forEach(System.out::println);
    }
}
