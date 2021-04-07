package cn.delei.java.concurrent;

import cn.delei.PrintUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor Demo
 *
 * @author deleiguo
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        // 获取 CPU（jvm虚拟机可用核心数）
        // 并非都能返回你所期望的数值，返回值可变
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("当前 CPU 最大可用核数为:" + processors);

//        createThreadPoolExecutor(5, 10,50, 1L,
//                1L, 10, false);
        paramsThreadPoolExecutor();
    }

    /**
     * 通用创建
     *
     * @param corePoolSize           核心线程数
     * @param maximumPoolSize        最大线程池数
     * @param queueCapacity          队列大小
     * @param keepAliveTime          线程空闲存活时间
     * @param sleepSeconds           单个任务执行时间
     * @param taskSize               任务总数
     * @param allowCoreThreadTimeOut 是否回收在保活时间后依然没没有任务执行核心线程
     */
    static void createThreadPoolExecutor(int corePoolSize, int maximumPoolSize, int queueCapacity, long keepAliveTime,
                                         long sleepSeconds, int taskSize, boolean allowCoreThreadTimeOut) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueCapacity));
        threadPoolExecutor.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
        for (int i = 0; i < taskSize; i++) {
            threadPoolExecutor.execute(new SimpleRunnable("C" + i, sleepSeconds));
        }
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()) {
            monitorThreadPoolExecutor(threadPoolExecutor);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished all threads");
    }

    static void monitorThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {

        System.out.printf("==> 核心线程数:%-10s \t 线程数:%-10s \t总线程数:%-10s \t活动线程数:%-10s \t 执行完成线程数:%-10s \t 当前排队线程数:%-10s\t\n",
                threadPoolExecutor.getCorePoolSize(),threadPoolExecutor.getPoolSize(),
                threadPoolExecutor.getTaskCount(), threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getCompletedTaskCount(), threadPoolExecutor.getQueue().size());

    }

    /**
     * 调整参数查看现象
     */
    static void paramsThreadPoolExecutor() {
        // 默认
        long sleepSeconds = 1L;
        int corePoolSize = 8;
        int maximumPoolSize = 16;
        int queueCapacity = 10;
        long keepAliveTime = 1L;
        int taskSize = 10;
        boolean allowCoreThreadTimeOut = false;
        // 最小设置
        sleepSeconds = 60L;
        corePoolSize = 2;
        maximumPoolSize = 4;
        queueCapacity = 10;
        taskSize = 20;
        createThreadPoolExecutor(corePoolSize, maximumPoolSize, queueCapacity, keepAliveTime, sleepSeconds,
                taskSize, allowCoreThreadTimeOut);

    }

    static class SimpleRunnable implements Runnable {
        private String command;
        private long sleepSeconds = 1L;

        public SimpleRunnable(String command) {
            this.command = command;
        }

        public SimpleRunnable(String command, long sleepSeconds) {
            this.command = command;
            this.sleepSeconds = sleepSeconds;
        }

        void processCommand() {
            try {
                TimeUnit.SECONDS.sleep(sleepSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.printf("线程%s\t 启动,Command:%s \n", name, command);
            processCommand();//模拟任务执行时间
            System.out.printf("线程%s\t 结束,Command:%s \n", name, command);
        }
    }
}
