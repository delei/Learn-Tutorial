package cn.delei.java.concurrent;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock可重入锁demo
 *
 * @author deleiguo
 */
public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread01 = new Thread(new ReentrantLockRunnable(), "ReentrantLockThread-01");
        Thread thread02 = new Thread(new ReentrantLockRunnable(), "ReentrantLockThread-02");
        thread01.start();
        thread02.start();
        thread01.join();
        thread02.join();
    }

    static class ReentrantLockRunnable implements Runnable {
        private final ReentrantLock lock = new ReentrantLock();
        private int value = 0;

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                try {
                    value++;
                    System.out.printf("%s\tvalue=%s\n", threadName, value);
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
