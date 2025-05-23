package cn.delei.java.concurrent;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal Demo
 *
 * @author deleiguo
 */
public class ThreadLocalDemo {

    /**
     * 类似“全局“
     */
    public static final ThreadLocal<Integer> THREAD_LOCAL = ThreadLocal.withInitial(() -> 0);
    public static final ThreadLocal<byte[]> THREAD_BYTE_LOCAL = ThreadLocal.withInitial(() -> new byte[1]);
    public static final InheritableThreadLocal<Integer> INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal();

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");

    public static void main(String[] args) throws Exception {
        // hashIncrement();
        // demo01();
        // demo02();
        // inheritableThreadLocalDemo();

        // VM options: -Xms5m -Xmx5m
        threadLocalOOMDemo();
    }

    /**
     * ThreadLocalMap中的HASH_INCREMENT变量
     * 为什么是0x61c88647，黄金分割
     */
    static void hashIncrement() {
        final int HASH_INCREMENT = 0x61c88647;
        int n = 5;
        int max = 2 << (n - 1);
        for (int i = 0; i < max; i++) {
            System.out.print(i * HASH_INCREMENT & (max - 1));
            System.out.print(" ");
        }
        System.out.println();
    }

    static void demo01() throws InterruptedException {
        THREAD_LOCAL.set(1);
        System.out.println("Main Thread:" + THREAD_LOCAL.get());
        TimeUnit.SECONDS.sleep(2);
        new Thread(() -> {
            try {
                System.out.println("T01 Thread get:" + THREAD_LOCAL.get());
                THREAD_LOCAL.set(100);
                System.out.println("T01 Thread after set:" + THREAD_LOCAL.get());
                System.out.println("T01 Thread End!");
            } finally {
                THREAD_LOCAL.remove();
            }
        }, "T01").start();

        new Thread(() -> {
            try {
                System.out.println("T02 Thread get:" + THREAD_LOCAL.get());
                THREAD_LOCAL.set(200);
                System.out.println("T02 Thread after set:" + THREAD_LOCAL.get());
                System.out.println("T02 Thread End!");
            } finally {
                THREAD_LOCAL.remove();
            }
        }, "T02").start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Main Thread get:" + THREAD_LOCAL.get());
    }

    /**
     * 线程池中的线程复用
     * 线程池在复用线程执行任务时使用被之前的线程操作过的 value 对象
     *
     * @throws Exception
     */
    static void demo02() throws Exception {
        int n = 10;
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 4,
                0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < n; i++) {
            poolExecutor.execute(() -> {
                try {
                    int before = get();
                    increment();
                    int after = get();
                    System.out.printf("==> %s\t 线程%-20s\t before=%s \t after=%s\n", format.format(LocalTime.now()),
                            Thread.currentThread().getName(), before, after);
                } finally {
                    // 注释这一句的前后不同效果
                    remove(); //清理线程本地存储
                }
            });
        }
        poolExecutor.shutdown();
        System.out.println("==> Main End!");
    }

    static void inheritableThreadLocalDemo() {
        THREAD_LOCAL.set(10);
        INHERITABLE_THREAD_LOCAL.set(100);

        System.out.println("Main Thread get:" + THREAD_LOCAL.get());
        System.out.println("Main Thread get:" + INHERITABLE_THREAD_LOCAL.get());

        new Thread(() -> {
            System.out.println("T01 Thread get:" + THREAD_LOCAL.get());
            System.out.println("T01 Inheritable Thread get:" + INHERITABLE_THREAD_LOCAL.get());
        }, "T01").start();
    }

    static void threadLocalOOMDemo() throws Exception {

        ThreadPoolExecutor t = new ThreadPoolExecutor(4, 4, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            t.execute(new Thread(() -> {
                ThreadLocal threadLocal = new ThreadLocal();
                for (int j = 0; j < 5; j++) {
                    threadLocal.set(new int[1024 * 1024]);
                }
                // 尝试分别取消如下的注释代码试一下
                threadLocal = null;
                // threadLocal.remove();
            }));
        }
        t.shutdown();
    }

    static int get() {
        return THREAD_LOCAL.get();
    }

    static void set(int value) {
        THREAD_LOCAL.set(value);
    }

    static void increment() {
        THREAD_LOCAL.set(get() + 1);
    }

    static void remove() {
        THREAD_LOCAL.remove();
    }
}
