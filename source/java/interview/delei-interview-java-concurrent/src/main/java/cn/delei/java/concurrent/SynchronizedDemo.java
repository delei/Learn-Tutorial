package cn.delei.java.concurrent;

public class SynchronizedDemo {

    /**
     * 同步方法
     */
    public synchronized void lockMethod() {
        System.out.println("Hello World");
    }

    /**
     * 同步静态方法
     */
    public static synchronized void lockStaticMethod() {
        System.out.println("Hello World");
    }

    /**
     * 同步代码块
     */
    public void lockCode() {
        synchronized (SynchronizedDemo.class) {
            System.out.println("Hello World");
        }
    }
}
