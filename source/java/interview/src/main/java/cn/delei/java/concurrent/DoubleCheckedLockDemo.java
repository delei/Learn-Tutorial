package cn.delei.java.concurrent;

/**
 * DCL(Double-checked Locking) 案例
 *
 * @author deleiguo
 */
public class DoubleCheckedLockDemo {
    private DoubleCheckedLockDemo() {
    }

    private volatile static DoubleCheckedLockDemo instance;

    /**
     * Q: 为什么，synchronized 也有可见性的特点，还需要 volatile 关键字
     * A: synchronized 的有序性，不是 volatile 的防止指令重排序.
     * 如果不加 volatile 关键字可能导致的结果，就是第一个线程在初始化初始化对象，
     * 设置 instance 指向内存地址时。第二个线程进入时，有指令重排。
     * 在判断 if (instance == null) 时就会有出错的可能，因为这会可能 instance 可能还没有初始化成功
     *
     * @return
     */
    public DoubleCheckedLockDemo getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLockDemo.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLockDemo();
                }
            }
        }
        return instance;
    }
}
