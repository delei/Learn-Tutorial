package cn.delei.java.concurrent;

import cn.delei.util.PrintUtil;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * 多线程有关多个线程顺序执行的问题
 *
 * @author deleiguo
 */
public class ThreadOrderDemo {
    private static final int PRINT_N = 100000;
    private static Thread threadA, threadB, threadC;
    /*
     * 使用标志位来停止线程
     */
    private static volatile boolean interrupted = false;

    public static void main(String[] args) throws Exception {
        /*
         * 第一个问题：
         * 三个线程分别打印 A，B，C，要求这三个线程一起运行，打印 n 次，输出形如“ABCABCABC....”的字符串
         * 三个线程开始正常输出后，主线程若检测到用户任意的输入则停止三个打印线程的工作，整体退出
         */
        question01S1();
//        question01S2();

    }

    /**
     * 第一个问题的解决方案01
     * 使用信号量Semaphore控制
     * 使用volatile 修饰的标识字段interrupted来控制线程终止
     */
    static void question01S1() {
        PrintUtil.printDivider("信号量");
        System.out.println("输入任意键可退出");
        // 信号量，A信号量设为1，从A开始打印
        final Semaphore semaphoreA = new Semaphore(1);
        final Semaphore semaphoreB = new Semaphore(0);
        final Semaphore semaphoreC = new Semaphore(0);

        threadA = new Thread(() -> {
            while (!interrupted) {
                try {
                    semaphoreA.acquire(); // s1信号量减1，当s1为0则无法继续获取信号量
                    System.out.println("A");
                    semaphoreB.release(); // s2信号量加1，初始值为0
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A");

        threadB = new Thread(() -> {
            while (!interrupted) {
                try {
                    semaphoreB.acquire(); // s1信号量减1，当s1为0则无法继续获取信号量
                    System.out.println("B");
                    semaphoreC.release(); // s2信号量加1，初始值为0
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B");

        threadC = new Thread(() -> {
            while (!interrupted) {
                try {
                    semaphoreC.acquire(); // s1信号量减1，当s1为0则无法继续获取信号量
                    System.out.println("C");
                    semaphoreA.release(); // s2信号量加1，初始值为0
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "C");

        threadA.start();
        threadB.start();
        threadC.start();
        // 主线程
        // 一旦主线程完成执行，JVM 就会终止
        Scanner scan = new Scanner(System.in);
        String input;
        do {
            input = scan.next();
        } while (input == null);
        interrupted = true;
        System.out.println("exit");
    }

    /**
     * 第一个问题的解决方案02
     * 使用LockSupport的park和unpark来控制
     * 通过线程类的isInterrupted方法来控制线程终止
     */
    static void question01S2() throws Exception {
        PrintUtil.printDivider("LockSupport");
        threadA = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("A");
                LockSupport.unpark(threadB);
                LockSupport.park();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }, "A");

        threadB = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
                System.out.println("B");
                LockSupport.unpark(threadC);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }, "B");

        threadC = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
                System.out.println("C");
                LockSupport.unpark(threadA);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }, "C");

        threadA.start();
        threadB.start();
        threadC.start();
        // 主线程
        Scanner scan = new Scanner(System.in);
        String input;
        do {
            input = scan.next();
            // 中断线程
            threadA.interrupt();
            threadB.interrupt();
            threadC.interrupt();
        } while (input == null);
        System.out.println("exit");
    }
}
