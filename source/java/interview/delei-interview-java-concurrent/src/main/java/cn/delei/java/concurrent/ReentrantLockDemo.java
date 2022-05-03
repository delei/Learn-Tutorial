package cn.delei.java.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可重入独占锁 demo
 *
 * @author deleiguo
 */
public class ReentrantLockDemo {

    public static void main(String[] args) throws Exception {
        // nonFairLockDemo();
        fairLockDemo();
    }

    static void nonFairLockDemo() throws Exception {
        Lock lock = new ReentrantLock(false);
        genThread(lock, 5);
    }

    static void fairLockDemo() throws Exception {
        Lock lock = new ReentrantLock(true);
        genThread(lock, 5);
    }

    private static void genThread(Lock lock, int size) {
        for (int i = 0; i < size; i++) {
            new Thread(new ReentrantLockRunnable(lock), "DG-Thread-" + i).start();
        }
    }

    static class ReentrantLockRunnable implements Runnable {
        private final Lock lock;

        public ReentrantLockRunnable(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.printf("%s\t is running\n", Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
