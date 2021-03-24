package cn.delei.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JUC 原子类
 *
 * @author deleiguo
 */
public class AtomicDemo {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        try {
            incrementAndGetDemo(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全自增
     *
     * @param threadSize 线程数
     * @throws Exception
     */
    static void incrementAndGetDemo(int threadSize) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 设置 CountDownLatch 的计数器为 threadSize，保证在主线程打印累加结果之前，100 个线程已经执行完累加
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() -> {
                atomicInteger.incrementAndGet();
                // 每一个线程执行完后。计数器减1
                countDownLatch.countDown();
            });
        }
        // 主线程等待，直到计数器减为0
        countDownLatch.await();
        executorService.shutdown();
        // 多执行几次，count最后的数量可能小于threadSize值
        System.out.println(threadSize + "之后的结果 count = " + atomicInteger.get());

    }
}
