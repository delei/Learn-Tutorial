package cn.delei.java.concurrent;

import cn.delei.util.SimulationUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier Demo
 *
 * @author deleiguo
 */
public class CyclicBarrierDemo {
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");

    public static void main(String[] args) {
//        cyclicBarrierDemo01();
        cyclicBarrierDemo02();
    }

    /**
     * 闯关，屏障计数值和玩家数量一致
     */
    static void cyclicBarrierDemo01() {
        int n = 3;
        final long[] rumeTimeAray = {2L, 4L, 6L};
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < n; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.printf("==> %s\t[玩家%s]\t 闯第01关\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                try {
                    SimulationUtil.runtime(rumeTimeAray[temp]); // 闯关时间
                    cyclicBarrier.await();
                    System.out.printf("==> %s\t[玩家%s]\t 闯第02关\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                    SimulationUtil.runtime(rumeTimeAray[temp]); // 闯关时间
                    cyclicBarrier.await();
                    System.out.printf("==> %s\t[玩家%s]\t 闯第03关\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 闯关，屏障计数值和玩家数量不一致
     */
    static void cyclicBarrierDemo02() {
        int n = 3;
        final long[] rumeTimeAray = {2L, 4L, 6L};
        // 尝试CyclicBarrier的计数不一样，1的时候，2的时候
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Thread(() -> {
            System.out.printf("==> %s\t[玩家%s]\t 屏障\n", format.format(LocalTime.now()), Thread.currentThread().getName());
        }));
        for (int i = 0; i < n; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.printf("==> %s\t[玩家%s]\t 闯01关\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                try {
                    SimulationUtil.runtime(rumeTimeAray[temp]); // 闯关时间
                    cyclicBarrier.await();
                    System.out.printf("==> %s\t[玩家%s]\t 闯02关\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                    SimulationUtil.runtime(rumeTimeAray[temp]); // 闯关时间
                    cyclicBarrier.await();
                    System.out.printf("==> %s\t[玩家%s]\t 闯03关\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
