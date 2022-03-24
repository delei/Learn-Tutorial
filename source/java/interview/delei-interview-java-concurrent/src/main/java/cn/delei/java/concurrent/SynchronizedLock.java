package cn.delei.java.concurrent;

import cn.delei.util.PrintUtil;
import cn.hutool.core.date.StopWatch;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 利用 jol 查看 Synchronized 锁升级
 */
public class SynchronizedLock {

    /**
     * 运行前根据不同方法设置以下的JVM参数
     * -XX:-UseBiasedLocking  关闭偏向锁（默认打开）
     * -XX:BiasedLockingStartUpDelay=0 设置延迟时间
     * -XX:+UseHeavyMonitors  设置重量级锁
     * -XX:+DoEscapeAnalysis 开启逃逸分析
     * -XX:+EliminateLocks 开启锁消除(必须在-server模式下)
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // JDK1.8 默认开启偏向锁延迟，延迟时间大约4s
        // TimeUnit.SECONDS.sleep(5L);

        // lock01();
        // lock02();
        // lock03();
        // lock03HashCode();

        // -server -XX:+DoEscapeAnalysis -XX:+EliminateLocks
        // lockElimination();
        // lockCoarsening();
    }

    /**
     * [无锁]==>[偏向锁]
     * biasable 状态：可偏向状态
     * biased 状态：偏向状态，带有线程ID
     *
     * @throws Exception
     */
    static void lock01() throws Exception {

        Object lock = new Object();
        ClassLayout classLayout = ClassLayout.parseInstance(lock);

        System.out.println(classLayout.toPrintable());

        PrintUtil.printDivider();
        synchronized (lock) {
            System.out.println(classLayout.toPrintable());
        }
    }

    /**
     * [偏向锁]==>[轻量级锁]
     * biasable --> biased --> thin lock --> non-biasable --> thin lock
     * 偏向锁升级为轻量级锁: 在执行完成同步代码后释放锁，变为无锁不可偏向状态
     *
     * @throws Exception
     */
    static void lock02() throws Exception {

        Object lock = new Object();
        ClassLayout classLayout = ClassLayout.parseInstance(lock);

        PrintUtil.printDivider("L01:未进入同步块");
        // 初始可偏向状态
        System.out.println(classLayout.toPrintable());

        synchronized (lock) {
            PrintUtil.printDivider("L02:进入同步块");
            // 偏向主线程后，主线程退出同步代码块
            System.out.println(classLayout.toPrintable());
        }

        Thread t01 = new Thread(() -> {
            synchronized (lock) {
                PrintUtil.printDivider("新线程t01获取锁");
                // 新的线程进入同步代码块，升级成了轻量级锁
                System.out.println(classLayout.toPrintable());
            }
        });
        t01.start();
        t01.join();
        PrintUtil.printDivider("主线程再次查看锁对象");
        // 新线程轻量级锁退出同步代码块，主线程查看，变为不可偏向状态
        System.out.println(classLayout.toPrintable());

        synchronized (lock) {
            PrintUtil.printDivider("主线程再次进入同步块");
            // 由于对象不可偏向，主线程再次进入同步块，就会用轻量级锁
            System.out.println(classLayout.toPrintable());
        }
    }

    /**
     * 重量级锁
     * biasable --> fat lock
     *
     * @throws Exception
     */
    static void lock03() throws Exception {

        Object lock = new Object();
        ClassLayout classLayout = ClassLayout.parseInstance(lock);

        PrintUtil.printDivider("L01:未进入同步块");
        // 初始可偏向状态
        System.out.println(classLayout.toPrintable());

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    try {
                        // 模拟执行时间
                        TimeUnit.SECONDS.sleep(2L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(4L);
        PrintUtil.printDivider("所有线程未执行完期间，查看锁对象");
        System.out.println(classLayout.toPrintable());

        TimeUnit.SECONDS.sleep(7L);
        PrintUtil.printDivider("所有线程完成，再次查看锁对象");
        System.out.println(classLayout.toPrintable());
    }

    /**
     * 重量级锁 hash值
     *
     * @throws Exception
     */
    static void lock03HashCode() throws Exception {
        InnerData innerData = new InnerData("aa", "C100");
        ClassLayout classLayout = ClassLayout.parseInstance(innerData);
        PrintUtil.printDivider("L01:未进入同步块");
        System.out.println(classLayout.toPrintable());

        synchronized (innerData) {
            // 轻量级锁 thin lock
            System.out.println(classLayout.toPrintable());
            innerData.hashCode();
            // 重量级锁 fat lock
            System.out.println(classLayout.toPrintable());
        }
    }

    /**
     * 锁消除
     * StringBuffer#append 方法 synchronized
     *
     * @return String
     * @throws Exception
     */
    static void lockElimination() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Lock Elimination");
        for (int i = 0; i < 10000000; i++) {
            appendStr("aa", "bb", "cc");
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static String appendStr(String s1, String s2, String s3) {
        return new StringBuffer().append(s1).append(s2).append(s3).toString();
    }

    /**
     * 锁粗化
     * StringBuffer#append 方法 synchronized
     *
     * @throws Exception
     */
    static void lockCoarsening() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Lock Coarsening");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 1000000; i++) {
            stringBuffer.append("i");
        }
        System.out.println(stringBuffer.toString());
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    static class InnerData {
        private String code;
        private String name;

        public InnerData(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}

