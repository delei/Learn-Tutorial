package cn.delei.java.concurrent;

import cn.delei.util.PrintUtil;

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
        printThread.start();
        TimeUnit.SECONDS.sleep(PRINT_TIME);

//        solution01();
//        solution02();
        solution03();
    }

    /**
     * 实现一个容器，提供两个方法，add，getSize
     * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束.
     */
    static void solution01() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountList t = new CountList();

        // thread 02
        new Thread(() -> {
            System.out.println("Thread-02 Start ==>");
            if (t.getSize() != 5) {
                try {
                    countDownLatch.await();
                    System.out.println("Thread-02 End ==>");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-02").start();

        // thread 01
        new Thread(() -> {
            System.out.println("Thread-01 Start ==>");
            for (int i = 0; i < 9; i++) {
                t.add(i);
                System.out.println("Thread-01 add " + i);
                if (t.getSize() == 5) {
                    System.out.println("Thread-01 countDown 提示");
                    countDownLatch.countDown();
                }
            }
            System.out.println("Thread-01 End ==>");
        }, "Thread-01").start();
    }

    static void solution02() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("运动员[" + Thread.currentThread().getName() + "]准备完毕，等待号令...");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("运动员[" + Thread.currentThread().getName() + "]开跑...");
            }).start();
        }
        System.out.println("裁判准备发令...");
        Thread.sleep(2000);
        countDownLatch.countDown();
        System.out.println("执行发令...");
        countDownLatch.countDown();
    }

    static void solution03() throws InterruptedException {
        //闸门
        CountDownLatch startSignal = new CountDownLatch(5);
        CountDownLatch doneSignal = new CountDownLatch(1);
        for (int i = 1; i < 6; i++) {
            threadList.add(new Thread(new CountWorker(startSignal, doneSignal), "T0" + i));
        }
        // start
        threadList.forEach(Thread::start);
        //
        System.out.println("==>");
        startSignal.countDown();
        doneSignal.await();
    }

    static class CountWorker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        CountWorker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.countDown();
                System.out.println("==> " + format.format(LocalTime.now()) + " Start!");
//                doneSignal.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
