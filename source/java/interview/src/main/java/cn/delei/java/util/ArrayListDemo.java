package cn.delei.java.util;

import cn.delei.PrintUtil;
import cn.hutool.core.date.StopWatch;

import java.util.*;

public class ArrayListDemo {

    public static ArrayList<Integer> dataList = new ArrayList<>();

    public static void main(String[] args) {
//        print();
//        subList();
        failFast();
    }


    /**
     * 遍历
     */
    static void print() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            dataList.add(i);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("普通 For 循环");
        for (int i = 0; i < dataList.size(); i++) {
        }
        stopWatch.stop();
        stopWatch.start("增强 For 循环");
        for (Integer i : dataList) {
        }
        stopWatch.stop();

        stopWatch.start("Foreach 循环");
        dataList.forEach(System.out::println);
        stopWatch.stop();

        stopWatch.start("Iterator 循环");
        Iterator<Integer> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        stopWatch.stop();

        stopWatch.start("Steam 循环");
        dataList.stream().forEach(System.out::println);
        stopWatch.stop();

        System.err.println(stopWatch.prettyPrint());
    }

    static void subList() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(i);
        }
        List<Integer> subList = dataList.subList(1, 5);
        subList.add(20);
        subList.remove(3);
        subList.forEach(System.out::println);
        PrintUtil.printDivider();
        dataList.forEach(System.out::println);
        // 强制转换
        ArrayList<Integer> transferList = (ArrayList<Integer>) subList;
    }

    /**
     * fail-fast
     */
    static void failFast() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(i);
        }
        // 非Iterator遍历(单线程)
        PrintUtil.printDivider("非Iterator遍历(单线程)");
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 3) {
                dataList.remove(i);
            }
            System.out.println(dataList.get(i));
        }
        PrintUtil.printDivider("Iterator 遍历(单线程)");
        // Iterator 单线程
        try {
            int index = 0;
            Iterator<Integer> iterator = dataList.iterator();
            while (iterator.hasNext()) {
                if (index == 3) {
                    dataList.remove(index);
                }
                System.out.println(iterator.next());
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintUtil.printDivider("Iterator 遍历(多线程)");
        // Iterator 多线程
        Thread01 thread01 = new Thread01();
        Thread02 thread02 = new Thread02();
        thread01.setName("thread01");
        thread02.setName("thread02");
        thread01.start();
        thread02.start();
    }

    static class Thread01 extends Thread {
        @Override
        public void run() {
            Iterator<Integer> iterator = dataList.iterator();
            while (iterator.hasNext()) {
                System.out.println(this.getName() + ":" + iterator.next());
            }
            super.run();
        }
    }

    static class Thread02 extends Thread {
        int i = 0;

        @Override
        public void run() {
            try {
                while (i < 10) {
                    System.out.println(this.getName() + ":" + i);
                    if (i == 3) {
                        dataList.remove(i);
                    }
                    Thread.sleep(2000);
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
