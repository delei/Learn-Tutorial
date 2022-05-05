package cn.delei.java.concurrent;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock 读写锁 demo
 *
 * @author deleiguo
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        Innder innder = new Innder();
        //  一共启动6个线程，3个读线程，3个写线程
        for (int i = 0; i < 3; i++) {
            //启动1个读线程
            new Thread(() -> {
                while (true) {
                    innder.get();
                }
            }, "TR-" + i).start();
            //启动1个写线程
            new Thread(() -> {
                while (true) {
                    innder.put(RandomUtil.randomString(2));
                }
            }, "TW-" + i).start();
        }
    }


    static class Innder {

        // 模拟共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
        private String data = null;

        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        void get() {
            // 加读锁
            lock.readLock().lock();
            try {
                Thread.sleep(RandomUtil.randomLong(1000, 3000));
                System.out.println(Thread.currentThread().getName() + " #get :" + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        }

        void put(String d) {
            // 加读锁
            lock.writeLock().lock();
            try {
                Thread.sleep(RandomUtil.randomLong(1000, 3000));
                this.data = d;
                System.out.println(Thread.currentThread().getName() + " #put :" + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

}
