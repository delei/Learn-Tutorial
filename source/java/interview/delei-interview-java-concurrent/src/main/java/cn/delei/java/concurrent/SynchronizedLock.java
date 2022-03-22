package cn.delei.java.concurrent;

import cn.delei.util.PrintUtil;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class SynchronizedLock {

    public static void main(String[] args) throws Exception {
        //lock00();
        lock01();
    }

    /**
     * [无锁]==>[偏向锁]
     * biasable 状态：可偏向状态
     * biased 状态：偏向状态，带有线程ID
     *
     * @throws Exception
     */
    static void lock00() throws Exception {
        // JDK1.8默认开启偏向锁延迟，延迟时间大约4s，然后“偏向锁”才开启
        // 延迟5秒
        TimeUnit.SECONDS.sleep(5);

        Object lock = new Object();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        PrintUtil.printDivider();
        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
    }

    /**
     * [偏向锁]==>[轻量级锁]
     * 内存布局：biasable --> biased --> thin lock --> non-biasable --> thin lock
     * 偏向锁升级为轻量级锁: 在执行完成同步代码后释放锁，变为无锁不可偏向状态
     *
     * @throws Exception
     */
    static void lock01() throws Exception {

        TimeUnit.SECONDS.sleep(5);

        Object lock = new Object();
        PrintUtil.printDivider("L01:未进入同步块");
        // 初始可偏向状态
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        synchronized (lock) {
            PrintUtil.printDivider("L02:进入同步块");
            // 偏向主线程后，主线程退出同步代码块
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }

        Thread t01 = new Thread(() -> {
            synchronized (lock) {
                PrintUtil.printDivider("新线程t01获取锁");
                // 新的线程进入同步代码块，升级成了轻量级锁
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        });
        t01.start();
        t01.join();
        PrintUtil.printDivider("主线程再次查看锁对象");
        // 新线程轻量级锁退出同步代码块，主线程查看，变为不可偏向状态
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        synchronized (lock) {
            PrintUtil.printDivider("主线程再次进入同步块");
            // 由于对象不可偏向，主线程再次进入同步块，就会用轻量级锁
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
    }

}
