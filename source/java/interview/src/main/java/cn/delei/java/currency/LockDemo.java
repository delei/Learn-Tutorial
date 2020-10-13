package cn.delei.java.currency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockExample {
    private Lock lock = new ReentrantLock();

    private volatile int num = 0;

    public void add() {
        lock.lock();
        try {
            num++;
        } finally {
            lock.unlock(); // 确保释放锁，从而避免发生死锁。
        }
    }

    public int get() {
        return num;
    }
}

public class LockDemo {

    public static void main(String[] args) {
        LockExample lockExample = new LockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> lockExample.add());
        }
        executorService.shutdown();
        System.out.print(lockExample.get());
    }
}
