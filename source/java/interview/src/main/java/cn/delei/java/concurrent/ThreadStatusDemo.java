package cn.delei.java.concurrent;

import cn.delei.PrintUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 线程状态Demo
 *
 * @author deleiguo
 */
public class ThreadStatusDemo {
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
        printThread.start();
        TimeUnit.SECONDS.sleep(PRINT_TIME);
//        sleepDemo01();
//        sleepDemo02();
//        joinDemo01();
        joinDemo02();
    }

    /**
     * 主线程Sleep
     *
     * @throws Exception
     */
    static void sleepDemo01() throws Exception {
        Thread t01 = new Thread(() -> {
            System.out.println(format.format(LocalTime.now()) + "T01 Start");
        }, "T01");
        threadList.add(t01);
        Thread t02 = new Thread(() -> {
            System.out.println(format.format(LocalTime.now()) + "T02 Start");
        }, "T02");
        threadList.add(t02);
        // sleep
        t01.start();
        try {
            // 主线程Main Thread sleep
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t02.start();
    }

    /**
     * 线程内Sleep
     *
     * @throws Exception
     */
    static void sleepDemo02() throws Exception {
        Thread t01 = new Thread(() -> {
            System.out.printf("==>%-15s\t T01 Start\n", format.format(LocalTime.now()));
            System.out.printf("==>%-15s\t T01 End\n", format.format(LocalTime.now()));
        }, "T01");
        threadList.add(t01);
        Thread t02 = new Thread(() -> {
            System.out.printf("==>%-15s\t T02 Start\n", format.format(LocalTime.now()));
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("==>%-15s\t T02 End\n", format.format(LocalTime.now()));
        }, "T02");
        threadList.add(t02);
        // sleep
        t01.start();
        t02.start();
    }

    static void joinDemo01() {
        System.out.printf("==>%-15s\t Main Start\n", format.format(LocalTime.now()));
        Thread t01 = new Thread(() -> {
            System.out.printf("==>%-15s\t T01 Start\n", format.format(LocalTime.now()));
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("==>%-15s\t T01 End\n", format.format(LocalTime.now()));
        }, "T01");
        threadList.add(t01);
        t01.start();
        try {
            t01.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("==>%-15s\t Main End\n", format.format(LocalTime.now()));
    }

    static void joinDemo02() {
        System.out.printf("==>%-15s\t Main Start\n", format.format(LocalTime.now()));
        Thread t01 = new Thread(() -> {
            System.out.printf("==>%-15s\t T01 Start\n", format.format(LocalTime.now()));
            try {
                simulationRuntime(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("==>%-15s\t T01 End\n", format.format(LocalTime.now()));
        }, "T01");
        threadList.add(t01);

        Thread t02 = new Thread(() -> {
            System.out.printf("==>%-15s\t T02 Start\n", format.format(LocalTime.now()));
            try {
                simulationRuntime(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("==>%-15s\t T02 End\n", format.format(LocalTime.now()));
        }, "T02");
        threadList.add(t02);

        t01.start();
        t02.start();
        try {
            // 主线程Main Thread sleep
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t02.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("==>%-15s\t Main End\n", format.format(LocalTime.now()));
    }

    /**
     * 模拟运行时间
     *
     * @param second 运行时间单位为秒
     * @throws Exception
     */
    static void simulationRuntime(long second) throws Exception {
        if (second < 1) {
            throw new IllegalArgumentException("second must more than 1");
        }
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusSeconds(second);
        while (LocalDateTime.now().isBefore(end)) {
        }
    }
}
