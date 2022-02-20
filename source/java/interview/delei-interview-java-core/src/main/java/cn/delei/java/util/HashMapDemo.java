package cn.delei.java.util;

import cn.delei.util.HashCodeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * java.util.HashMap Demo
 *
 * @author deleiguo
 */
public class HashMapDemo {

    public static HashMap<String, String> hashMap;

    public static void main(String[] args) {
        //opera();
        calcInitCapacity();
    }

    /**
     * 遍历
     */
    static void opera() {
        try {
            int capacity = 7;
            hashMap = new HashMap();
            // 使用反射
            Class mapClass = hashMap.getClass();
            Field tableField = mapClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Field thresholdField = mapClass.getDeclaredField("threshold");
            thresholdField.setAccessible(true);
            Field sizeField = mapClass.getDeclaredField("size");
            sizeField.setAccessible(true);
            Method capacityMethod = mapClass.getDeclaredMethod("capacity");
            capacityMethod.setAccessible(true);
            Method loadFactorMethod = mapClass.getDeclaredMethod("loadFactor");
            loadFactorMethod.setAccessible(true);

            System.out.println("加载因子：" + loadFactorMethod.invoke(hashMap));
            for (int i = 0; i < capacity; i++) {
                hashMap.put(String.valueOf(i), String.valueOf(i));
                System.out.printf("容量：%s  阀值：%s  元素数量：%s\n", capacityMethod.invoke(hashMap)
                        , thresholdField.get(hashMap), hashMap.size());
            }
            // put 相同 hashcode 的 key
            List<String> hashSting = HashCodeUtil.generateN(11);
            int h = hashSting.get(0).hashCode();
            int hash = h ^ (h >>> 16);
            System.out.println("Hashcode:" + hash);
            for (String s : hashSting) {
                hashMap.put(s, s);
                System.out.printf("容量：%s  阀值：%s  元素数量：%s\n", capacityMethod.invoke(hashMap)
                        , thresholdField.get(hashMap), hashMap.size());
            }
            hashMap.get(hashSting.get(3));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JDK7 安全问题，需使用特定的JDK
     */
    static void concurrentLoopIssue() {
        HashMap<Integer, String> dataMap = new HashMap<>(2, 0.75f);
        dataMap.put(5, "a");
        new Thread("T01") {
            @Override
            public void run() {
                dataMap.put(7, "B");
                System.out.println(dataMap);
            }
        }.start();

        new Thread("T02") {
            @Override
            public void run() {
                dataMap.put(3, "C");
                System.out.println(dataMap);
            }
        }.start();
    }

    static void calcInitCapacity() {
        System.out.println(MapUtil.calcHashMapInitCapacity(7));
        System.out.println(MapUtil.calcHashMapInitCapacity(10));
        System.out.println(MapUtil.calcHashMapInitCapacity(16));
        System.out.println(MapUtil.calcHashMapInitCapacity(25));
    }
}
