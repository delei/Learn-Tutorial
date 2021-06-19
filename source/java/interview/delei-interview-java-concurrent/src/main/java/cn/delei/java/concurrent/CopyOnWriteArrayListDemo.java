package cn.delei.java.concurrent;

import java.util.Iterator;
import java.util.concurrent.*;

/**
 * CopyOnWriteArrayList Demo
 *
 * @author deleiguo
 */
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        demo02();
    }

    static void demo01() {
        final CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        final int n = 10;
        for (int i = 0; i < n; i++) {
            copyOnWriteArrayList.add(i);
        }

        new Thread(() -> {
            System.out.println("T1 Start!");
            for (int i = n + 10; i < n + 20; i++) {
                copyOnWriteArrayList.add(i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T10").start();

        Iterator<Integer> iterator = copyOnWriteArrayList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        iterator = copyOnWriteArrayList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }

    static void demo02() {
        int nThreads = 5;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(nThreads, nThreads
                , 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        final CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        final int n = 10;
        for (int i = 0; i < n; i++) {
            copyOnWriteArrayList.add(i);
        }
        CountDownLatch countDownLatch = new CountDownLatch(nThreads);
        for (int i = 1; i < nThreads; i++) {
            final int temp = i;
            threadPoolExecutor.execute(() -> {
                final int s = temp * n;
                for (int j = s; j < s + 10; j++) {
                    copyOnWriteArrayList.add(j);
                }
                countDownLatch.countDown();
                System.out.println(s + " End");
            });
        }
        countDownLatch.countDown();
        try {
            countDownLatch.await();
            threadPoolExecutor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterator<Integer> iterator = copyOnWriteArrayList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        System.out.println("Main End");
    }
}
