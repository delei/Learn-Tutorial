package cn.delei.java.concurrent;

import cn.delei.util.SimulationUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * Semaphore信号量 Demo
 *
 * @author deleiguo
 */
public class SemaphoreDemo {
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");

    public static void main(String[] args) throws Exception {
        semaphoreDemo01();
//        semaphoreDemo02(3, 3);
//        semaphoreDemo02(5, 2);
//        semaphoreDemo02(2, 5);
//        demo01();
//        demo02();
//        demo03();
    }

    /**
     * 单一线程信号量简单demo，main线程
     */
    static void semaphoreDemo01() {
        final Semaphore semaphore = new Semaphore(2);
        try {
            System.out.println("01==> availablePermits:" + semaphore.availablePermits());
            semaphore.release();
            System.out.println("02==> availablePermits:" + semaphore.availablePermits());
            semaphore.acquire(3);
            System.out.println("03==> availablePermits:" + semaphore.availablePermits());
            semaphore.release();
            semaphore.release(2);
            System.out.println("04==> availablePermits:" + semaphore.availablePermits());
            semaphore.acquire(4);
            System.out.println("05==> availablePermits:" + semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 简单demo
     *
     * @param n 线程数量
     * @param s 信号量数量
     */
    static void semaphoreDemo02(int n, int s) throws Exception {
        if (n < 1 || s < 1) {
            throw new IllegalArgumentException();
        }
        // 适当调整上述两个变量的值
        // 01: 线程数量 = 信号量数量
        // 02: 线程数量 < 信号量数量
        // 03: 线程数量 > 信号量数量
        final Semaphore semaphore = new Semaphore(s);
        final ExecutorService executorService = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            executorService.execute(new Thread(() -> {
                System.out.printf("==> %s\t 线程%s Start\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                try {
                    semaphore.acquire();
                    SimulationUtil.runtime(2);
                    // 每次执行完之后进行release
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.printf("==> %s\t 线程%s End\n", format.format(LocalTime.now()), Thread.currentThread().getName());

            }));
        }
        executorService.shutdown();
    }

    /**
     * 线程数量超过了信号数量 ,不调用release
     */
    static void demo01() {
        final int n = 5;
        final Semaphore semaphore = new Semaphore(3);
        final ExecutorService executorService = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            executorService.execute(new Thread(() -> {
                System.out.printf("==> %s\t 线程%s Start\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                try {
                    semaphore.acquire();
                    SimulationUtil.runtime(2);
                    // --> 不调用release
//                    semaphore.release(); // 如果注释此行，即不进行release，那么剩余超过信号量的线程会阻塞，不会继续往下执行
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.printf("==> %s\t 线程%s End\n", format.format(LocalTime.now()), Thread.currentThread().getName());

            }));
        }
        executorService.shutdown();
    }

    /**
     * 重复调用acquire，次数比信号量的数量大
     * 结果：线程阻塞
     */
    static void demo02() {
        final int s = 2;
        final Semaphore semaphore = new Semaphore(s);
        new Thread(() -> {
            System.out.printf("==> %s\t 线程%s Start\n", format.format(LocalTime.now()), Thread.currentThread().getName());
            try {
                for (int i = 0; i < s + 1; i++) {
                    semaphore.acquire();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("==> %s\t 线程%s End\n", format.format(LocalTime.now()), Thread.currentThread().getName());
        }, "T01").start();
    }

    static void demo03() {
        final Semaphore semaphore = new Semaphore(1);
        // 1个线程调用一次acquire方法，然后调用两次release方法
        new Thread(() -> {
            System.out.printf("==> %s\t 线程%s Start\n", format.format(LocalTime.now()), Thread.currentThread().getName());
            try {
                semaphore.acquire();
                SimulationUtil.runtime(2);
                // release方法会添加令牌，并不会以初始化的大小为准
                semaphore.release();
                semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("==> %s\t 线程%s End\n", format.format(LocalTime.now()), Thread.currentThread().getName());
        }, "T01").start();

        // 另外一个线程调用acquire(2)方法
        new Thread(() -> {
            System.out.printf("==> %s\t 线程%s Start\n", format.format(LocalTime.now()), Thread.currentThread().getName());
            try {
                semaphore.acquire(2);
                SimulationUtil.runtime(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("==> %s\t 线程%s End\n", format.format(LocalTime.now()), Thread.currentThread().getName());
        }, "T02").start();
    }
}
