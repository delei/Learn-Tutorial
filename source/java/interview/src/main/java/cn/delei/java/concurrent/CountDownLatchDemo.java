package cn.delei.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch Demo
 *
 * @author deleiguo
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception {
//        solution01();
        solution02();
    }

    /**
     * 实现一个容器，提供两个方法，add，getSize
     * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束.
     */
    static void solution01() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountList t = new CountList();

        // thread 02
        new Thread(() -> {
            System.out.println("Thread-02 Start ==>");
            if (t.getSize() != 5) {
                try {
                    countDownLatch.await();
                    System.out.println("Thread-02 End ==>");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-02").start();

        // thread 01
        new Thread(() -> {
            System.out.println("Thread-01 Start ==>");
            for (int i = 0; i < 9; i++) {
                t.add(i);
                System.out.println("Thread-01 add " + i);
                if (t.getSize() == 5) {
                    System.out.println("Thread-01 countDown 提示");
                    countDownLatch.countDown();
                }
            }
            System.out.println("Thread-01 End ==>");
        }, "Thread-01").start();
    }

    static void solution02() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("运动员[" + Thread.currentThread().getName() + "]准备完毕，等待号令...");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("运动员[" + Thread.currentThread().getName() + "]开跑...");
            }).start();
        }
        System.out.println("裁判准备发令...");
        Thread.sleep(2000);
        countDownLatch.countDown();
        System.out.println("执行发令...");
        countDownLatch.countDown();
    }

    static class CountList {
        private volatile List<Integer> list = new ArrayList<>();

        public void add(int n) {
            list.add(n);
        }

        public int getSize() {
            return list.size();
        }
    }
}
