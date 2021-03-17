package cn.delei.java.lang;

import cn.delei.PrintUtil;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/**
 * 值传递
 */
public class PassValueDemo {
    public static void main(String[] args) {
        int ia = 1;
        swap(ia);
        System.out.println("main中swap后的ia:" + ia);
        PrintUtil.printDivider();
        User user = new User();
        user.setAge(18);
        user.setName("Delei");
        userCross01(user);
        System.out.println("main中userCross01后的user:" + user.toString());
        PrintUtil.printDivider();
        User user01 = new User();
        user01.setAge(18);
        user01.setName("Delei");
        userCross02(user01);
        System.out.println("main中的userCross02后的user01:" + user01.toString());
        PrintUtil.printDivider();
        int[] arrays = {1, 2, 3, 4};
        intArrayCross(arrays);
        System.out.println("main中的intArrayCross后的arrays:" + Arrays.toString(arrays));
    }

    /**
     * 基础数据类型
     *
     * @param a
     */
    static void swap(int a) {
        System.out.println("传入的a:" + a);
        a = 10;
    }

    /**
     * 自定义对象传递
     *
     * @param user
     */
    static void userCross01(User user) {
        System.out.println("传入的user:" + user.toString());
        user.setAge(20);
        user.setName("Guo");
        System.out.println("方法内改变值后的user:" + user.toString());
    }

    /**
     * 自定义对象值传递
     *
     * @param user
     */
    static void userCross02(User user) {
        System.out.println("传入的user:" + user.toString());
        user = new User();// 重点在此
        user.setName("DeleiGuo");
        System.out.println("方法内改变指后的user:" + user.toString());
    }

    /**
     * 数组
     *
     * @param arrays
     */
    static void intArrayCross(int[] arrays) {
        System.out.println("传入的arrays:" + Arrays.toString(arrays));
        arrays[1] = 10;
    }

    static class User {
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
