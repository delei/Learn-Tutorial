package cn.delei.java.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport Demo
 *
 * @author deleiguo
 */
public class LockSupportDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new LockSupportThread(), "LockSupportThread");
        thread.start();
        try {
            //充分运行线程LockSupportThread后，中断该线程，
            TimeUnit.SECONDS.sleep(3);
            //该线程能从park()方法返回
            //thread.interrupt();
            LockSupport.unpark(thread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class LockSupportThread implements Runnable {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.printf("%s\t start\n", name);
            // 阻塞自己
            LockSupport.park();
            // 等待unpart后继续运行
            System.out.printf("%s\t 继续运行\n", name);
        }
    }

}
