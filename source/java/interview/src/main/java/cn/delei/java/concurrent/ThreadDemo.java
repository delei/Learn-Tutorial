package cn.delei.java.concurrent;

import cn.delei.PrintUtil;

import java.util.concurrent.*;

class WorkThreadRunnable implements Runnable {

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
    public static void main(String[] args) throws Exception {

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

    private static void printThreadInfo(Thread thread) {
        System.out.println("线程唯一标识符：" + thread.getId());
        System.out.println("线程名称：" + thread.getName());
        System.out.println("线程状态：" + thread.getState());
        System.out.println("线程优先级：" + thread.getPriority());
    }
}
