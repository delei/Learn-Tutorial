package cn.delei.java.concurrent;

import cn.delei.util.PrintUtil;
import cn.delei.util.SimulationUtil;

import java.util.concurrent.*;

class WorkThreadRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("WorkThread run");
    }
}

class WorkCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "ok";
    }
}

public class ThreadDemo {

    private static int x = 0;
    private static int y = 0;
    private static boolean flag = false;

    void update() {
        System.out.println("X=" + x);
        System.out.println("Y=" + y);
        System.out.println("flag=" + flag);
    }

    public ThreadDemo() {
    }

    public ThreadDemo(int x, int y, boolean flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

    public static void main(String[] args) throws Exception {
        //currentThread();
        //threadCreate();
        //threadStartHappenBefore();
        //daemonThread();
        //daemonThread01();
    }

    /**
     * 启动main实际currentThread启动了多少Thread
     */
    static void currentThread() {
        PrintUtil.printDivider("所有线程状态");
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        while (currentGroup.getParent() != null) {
            // 返回此线程组的父线程组
            currentGroup = currentGroup.getParent();
        }
        // 此线程组中活动线程的估计数
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        // 把对此线程组中的所有活动子组的引用复制到指定数组中。
        currentGroup.enumerate(lstThreads);
        System.out.println("线程总数: " + noThreads);
        for (Thread thread : lstThreads) {
            System.out.printf("ID: %s\t 线程名称: %s\t 优先级: %s\t 线程状态: %s\n", thread.getId(), thread.getName(),
                    thread.getPriority(), thread.getState());
        }
    }

    /**
     * Thread 相关 JMM Happen-Before原则
     *
     * @throws Exception
     */
    static void threadStartHappenBefore() throws Exception {
        ThreadDemo threadDemo01 = new ThreadDemo();
        // 如果线程 A 执行操作 ThreadB.start() (启动线程B),
        // 那么 A 线程的 ThreadB.start() 操作 happens-before 于线程 B 中的任意操作，
        // 也就是说，主 线程 A 启动子线程 B 后，子线程 B 能看到主线程在启动子线程 B 前的操作
        PrintUtil.printDivider("Happen-Before:Thread start()");
        Thread thread01 = new Thread(threadDemo01::update, "Thread-01");
        threadDemo01.x = 20;
        threadDemo01.y = 10;
        threadDemo01.flag = true;
        thread01.start();
        System.out.println(Thread.currentThread().getName() + " End");
        System.out.println(thread01.getName() + " " + thread01.getState());

        PrintUtil.printDivider("Happen-Before:Thread join()");
        // 如果线程 A 执行操作 ThreadB.join() 并成功返回,
        // 那么线程 B 中的任意操作 happens-before 于线程 A 从 ThreadB.join() 操作成功返回，
        // 和 start 规则刚 好相反，主线程 A 等待子线程 B 完成，
        // 当子线程 B 完成后，主线程能够看到 子线程 B 的赋值操作
        ThreadDemo threadDemo02 = new ThreadDemo(0, 0, false);
        Thread thread02 = new Thread(threadDemo02::update, "Thread-02");
        thread02.start();
        thread02.join();
        threadDemo02.x = 21;
        threadDemo02.y = 11;
        threadDemo02.flag = true;
        System.out.println(thread02.getName() + " " + thread02.getState());
    }

    /**
     * 创建线程的方式
     *
     * @throws Exception
     */
    static void threadCreate() throws Exception {
        WorkThreadRunnable instance01 = new WorkThreadRunnable();
        Thread workThread01 = new Thread(instance01);
        workThread01.setName("WorkThread-01");

        WorkThreadRunnable instance02 = new WorkThreadRunnable();
        Thread workThread02 = new Thread(instance02);
        workThread02.setName("WorkThread-02");

        PrintUtil.printDivider("WorkThread-01");
        workThread01.start();
        printThreadInfo(workThread01);
        PrintUtil.printDivider("WorkThread-02");
        workThread02.start();
        printThreadInfo(workThread02);

        PrintUtil.printDivider("WorkCallableResult");
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> workCallableResult = executorService.submit(new WorkCallable());
        System.out.println(workCallableResult.get());

        FutureTask<String> futureTask = new FutureTask<>(new WorkCallable());
        executorService.submit(futureTask);
        System.out.println(futureTask.get());
    }

    /**
     * 守护线程
     */
    static void daemonThread01() {
        Thread daemonThread = new Thread(() -> {
            System.out.println("Daemon守护线程 Start!");
            try {
                SimulationUtil.runtime(9);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Daemon守护线程 End!");
        }, "Daemon-01");
        daemonThread.setDaemon(true);
        daemonThread.start();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " Start!!");
                try {
                    SimulationUtil.runtime(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " End!!");
            }).start();
        }
        System.out.println("Main Thread end!");

    }

    static void daemonThread() throws Exception {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("1-");
            }
        }, "T1-daemon");
        t1.setDaemon(true);
        t1.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("2-");
            }
        }, "T2-daemon");
        t2.setDaemon(true);
        t2.start();

        // 主线程模拟运行
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Main Thread end!");
    }

    static void printThreadInfo(Thread thread) {
        System.out.printf("%s\t%s\tState=%s\t\n", thread.getId(), thread.getName(), thread.getState());
    }
}
