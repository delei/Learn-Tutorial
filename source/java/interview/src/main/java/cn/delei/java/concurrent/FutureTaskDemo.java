package cn.delei.java.concurrent;

import java.util.concurrent.*;

public class FutureTaskDemo {
    public static void main(String[] args) {
//        futureExecutorService();
//        futureTaskExecutorService();
        futureTaskThread();
    }

    /**
     * Future + ExecutorService
     */
    static void futureExecutorService() {
        Task task = new Task();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(task);
        executorService.shutdown();
    }

    /**
     * FutureTask + ExecutorService
     */
    static void futureTaskExecutorService() {
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);
        executorService.shutdown();
    }

    /**
     * FutureTask + Thread
     */
    static void futureTaskThread() {
        FutureTask<Integer> futureTask = new FutureTask<>(new Task());
        Thread thread = new Thread(futureTask, "Thread-01");
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");

        if (!futureTask.isDone()) {
            System.out.println("Thread task is not done!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int taskReault = 0;
        try {
            taskReault = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Task Result = " + taskReault);
    }

    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws InterruptedException {
            System.out.println("Task Call: Thread [" + Thread.currentThread().getName() + "] is running");
            int result = 0;
            for (int i = 0; i < 100; ++i) {
                result += i;
            }
            Thread.sleep(3000);
            return result;
        }
    }
}
