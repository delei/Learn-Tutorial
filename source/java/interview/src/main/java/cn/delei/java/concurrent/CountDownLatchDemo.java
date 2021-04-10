package cn.delei.java.concurrent;

import cn.delei.util.PrintUtil;
import cn.delei.util.SimulationUtil;

import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * CountDownLatch Demo
 *
 * @author deleiguo
 */
public class CountDownLatchDemo {
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");
    private final static long PRINT_TIME = 2L;
    private static List<Thread> threadList;

    public static void main(String[] args) throws Exception {
        threadList = new ArrayList<>();
        // 守护线程，定时输入线程状态
        Thread printThread = new Thread(() -> {
            for (; ; ) {
                PrintUtil.printDivider(format.format(LocalTime.now()) + " 守护线程输出");
                for (Thread thread : threadList) {
                    System.out.printf("线程id:%s\t 线程名称:%s\t 优先级:%s\t 线程状态:%s\n", thread.getId(), thread.getName(),
                            thread.getPriority(), thread.getState());
                }
                try {
                    // 每隔秒
                    TimeUnit.SECONDS.sleep(PRINT_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "printThread");
        printThread.setDaemon(true);
//        printThread.start();
//        TimeUnit.SECONDS.sleep(PRINT_TIME);

        countDownDemo01();
//        runningRace01();
//        runningRace02();
//        runningRace03();
    }

    /**
     * 实现一个容器，提供两个方法，add，getSize
     * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束.
     */
    static void countDownDemo01() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountList t = new CountList();

        // thread 01
        new Thread(() -> {
            System.out.println(format.format(LocalTime.now()) + " Thread-01 Start ==>");
            for (int i = 0; i < 9; i++) {
                t.add(i);
                try {
                    SimulationUtil.runtime(1); // 模拟运行1秒，方便看时间
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(format.format(LocalTime.now()) + " Thread-01 add " + i);
                if (t.getSize() == 5) {
                    System.out.println(format.format(LocalTime.now()) + " Thread-01 countDown 提示");
                    countDownLatch.countDown();
                }
            }
            System.out.println(format.format(LocalTime.now()) + " Thread-01 End ==>");
        }, "Thread-01").start();

        // thread 02
        new Thread(() -> {
            System.out.println(format.format(LocalTime.now()) + " Thread-02 Start ==>");
            if (t.getSize() != 5) {
                try {
                    countDownLatch.await();
                    // 计数器减为0，则说明找到了，线程2执行结束
                    System.out.println(format.format(LocalTime.now()) + " Thread-02 End ==>");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-02").start();
    }

    /**
     * 运动员起跑控制(一个门闩)
     *
     * @throws Exception
     */
    static void runningRace01() throws Exception {
        // 控制不要抢跑，要枪响之后才能跑
        CountDownLatch countDownLatch = new CountDownLatch(3); // 改成1或者2试试效果？
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.printf("==> %s\t[运动员%s]\t 上起跑线了\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("==> %s\t[运动员%s]\t Running\n", format.format(LocalTime.now()), Thread.currentThread().getName());
            }).start();
        }
        countDownLatch.countDown(); // 裁判员发令，开枪了
        System.out.println("==> " + format.format(LocalTime.now()) + " 全体运动员上起跑线,裁判员倒计时3秒...");
        TimeUnit.SECONDS.sleep(3); // 等裁判员3秒
        countDownLatch.countDown(); // 裁判员发令，开枪了
        System.out.println("==> " + format.format(LocalTime.now()) + " 裁判员信号弹发令，所有运动员开始跑...");
        countDownLatch.countDown(); // 运动员听到枪响之后才能跑
    }

    /**
     * 运动员起跑控制(多个门闩)
     *
     * @throws InterruptedException
     */
    static void runningRace02() throws InterruptedException {
        int n = 3;
        CountDownLatch playerLatch = new CountDownLatch(n); // 运动员
        CountDownLatch judgeLatch = new CountDownLatch(1); // 裁判
        for (int i = 0; i < n; i++) {
            new Thread(() -> {
                System.out.printf("==> %s\t[运动员%s]\t 上起跑线了\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                try {
                    playerLatch.countDown();
                    judgeLatch.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.printf("==> %s\t[运动员%s]\t Running\n", format.format(LocalTime.now()), Thread.currentThread().getName());
            }).start();
        }
        playerLatch.await(); // 等运动员全部上起跑线
        System.out.println("==> " + format.format(LocalTime.now()) + " 全体运动员准备完毕,裁判员倒计时3秒...");
        TimeUnit.SECONDS.sleep(3); // 等裁判员3秒
        judgeLatch.countDown(); // 裁判员发令，开枪了
    }

    /**
     * 运动员跑步比赛，等所有运动员完成比赛后宣布成绩
     *
     * @throws Exception
     */
    static void runningRace03() throws Exception {
        int n = 3;
        final long[] rumeTimeAray = {2L, 4L, 6L};
        CountDownLatch countDownLatch = new CountDownLatch(n); // 计数器
        // 启动3 不同运行时间的线程
        for (int i = 0; i < n; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.printf("==> %s\t[运动员%s]\t 冲出起跑线\n", format.format(LocalTime.now()),
                        Thread.currentThread().getName());
                try {
                    SimulationUtil.runtime(rumeTimeAray[temp]); // 运动正在跑步
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.printf("==> %s\t[运动员%s]\t 跑完全程计时 %s秒\n", format.format(LocalTime.now()),
                        Thread.currentThread().getName(), rumeTimeAray[temp]);
            }).start();
        }
        countDownLatch.await(); // 等待所有运动员到达终点
        System.out.println("==> " + format.format(LocalTime.now()) + " 比赛结束，裁判开始宣布成绩.");
    }


    static class CountList {
        private volatile List<Integer> list = new ArrayList<>();

        public void add(int n) {
            list.add(n);
        }

        public int getSize() {
            return list.size();
        }
    }
}
