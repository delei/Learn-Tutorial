package cn.delei.java.lang;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThreadDemo {
    public static void main(String[] args) throws Exception {
        // 打印主线程
        Thread currentThread = Thread.currentThread();
        System.out.printf("主线程为:ID[%s],Name[%s],Priority[%s]\n", currentThread.getId(), currentThread.getName()
                , currentThread.getPriority());
//        createThread();
        threadState();
    }

    /**
     * 实现/创建线程类
     */
    static void createThread() throws Exception {
        // Extend Thread
        Thread01 thread01 = new Thread01();
        thread01.setName("thread01");
        thread01.start();
        // Implement Runnable Interface
        ThreadRunnable threadRunnable = new ThreadRunnable();
        Thread thread02 = new Thread(threadRunnable);
        thread02.setName("thread02");
        thread02.start();
        // JDK8 Function Interface Java8函数式变成
        new Thread(() -> {
            System.out.println("Java 8 Thread");
        }).start();

        // Implement Callable Interface
        ThreadCallable threadCallable = new ThreadCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(threadCallable);
        Thread thread03 = new Thread(futureTask);
        thread03.setName("thread03");
        thread03.start();
        System.out.printf("ID[%s],Name[%s],Priority[%s],Get:%s", thread03.getId(), thread03.getName()
                , thread03.getPriority(), futureTask.get());
    }

    static void threadState() throws Exception {
        Thread testThread = new Thread(() -> {
            System.out.println("Java 8 Thread");
        });
        testThread.setName("testThread");
        // thread info
        System.out.printf("Thread:ID[%s],Name[%s],Priority[%s]\n", testThread.getId(), testThread.getName()
                , testThread.getPriority());
        System.out.printf("Thread:State[%s]\n", testThread.getState());
        // start();
        testThread.start();
        System.out.println(testThread.isAlive());
        System.out.printf("after start():State[%s]\n", testThread.getState());
        while (true) {
            if (!testThread.isAlive()) {
                System.out.printf("isAlive false:State[%s]\n", testThread.getState());
                return;
            }
        }

    }

    static class Thread01 extends Thread {
        @Override
        public void run() {
            System.out.println(super.getId() + "," + super.getName() + "Run");
        }
    }

    static class ThreadRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("ThreadRunnable Run");
        }
    }

    static class ThreadCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return 10;
        }
    }
}
