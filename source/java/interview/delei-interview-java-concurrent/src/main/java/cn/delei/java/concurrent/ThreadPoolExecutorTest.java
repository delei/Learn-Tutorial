package cn.delei.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        for(int i=0;i<3;i++){
            Thread workThread01 = new Thread(new CompareFieldThreadRunable());
            workThread01.setUncaughtExceptionHandler((thread,e)->{
                System.out.println("==========");
                e.printStackTrace();
            });
            executorService.execute(workThread01);
            countDownLatch.countDown();
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("Main Thread End");
    }

    static class CompareFieldThreadRunable implements Runnable{
        @Override
        public void run() {
//            Thread.currentThread().setUncaughtExceptionHandler((thread,e)->{
//                System.out.println("====run exception handler");
//                e.printStackTrace();
//            });
            System.out.println("CompareFieldThread run...");
            System.out.println(1/0);
        }
    }
}
