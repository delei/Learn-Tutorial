package cn.delei.java.concurrent;

import cn.delei.PrintUtil;
import cn.delei.pojo.Person;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * JUC 原子类
 *
 * @author deleiguo
 */
public class AtomicDemo {

    public static void main(String[] args) {
        try {
//            incrementAndGetDemo(100);
//            atomicIntegerDemo();
//            atomicIntegerArrayDemo();
//            atomicReferenceDemo();
            ABADemo();
//            atomicStampedReferenceDemo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全自增
     *
     * @param threadSize 线程数
     * @throws Exception
     */
    static void incrementAndGetDemo(int threadSize) throws Exception {
        PrintUtil.printDivider("AtomicInteger 安全自增");
        ExecutorService executorService = Executors.newCachedThreadPool();
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        // 设置 CountDownLatch 的计数器为 threadSize，保证在主线程打印累加结果之前，100 个线程已经执行完累加
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() -> {
                atomicInteger.incrementAndGet();
                // 每一个线程执行完后。计数器减1
                countDownLatch.countDown();
            });
        }
        // 主线程等待，直到计数器减为0
        countDownLatch.await();
        executorService.shutdown();
        // 与普通的自增i++demo：ThreadUnsafeDemo中的autoIncrement方法进行对比
        System.out.println(threadSize + "之后的结果 count = " + atomicInteger.get());
    }

    /**
     * AtomicInteger Demo
     */
    static void atomicIntegerDemo() {
        PrintUtil.printDivider("AtomicInteger Demo");
        int value = 10;
        final AtomicInteger atimicI = new AtomicInteger(value);
        // 调用Unsafe类相关方法
        value = atimicI.getAndIncrement();
        value = atimicI.getAndIncrement();
        System.out.printf("getAndIncrement-->[value]:%s; [atimicI]:%s \n", value, atimicI);
        value = atimicI.getAndDecrement();
        System.out.printf("getAndDecrement-->[value]:%s; [atimicI]:%s \n", value, atimicI);
        value = atimicI.getAndAdd(3);
        System.out.printf("getAndAdd-->[value]:%s; [atimicI]:%s \n", value, atimicI);
        value = atimicI.getAndSet(8);
        System.out.printf("getAndSet-->[value]:%s; [atimicI]:%s \n", value, atimicI);
        System.out.printf("getAndSet-->[value]:%s; [atimicI]:%s \n", value, atimicI.get());
    }

    /**
     * AtomicIntegerArray Demo
     */
    static void atomicIntegerArrayDemo() {
        PrintUtil.printDivider("AtomicIntegerArray Demo");
        int value = 10;
        int[] numsArray = {20, 21, 22, 23, 24, 25, 26, 27, 28};
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(numsArray);
        // 调用VarHandle相关方法
        value = atomicIntegerArray.getAndSet(0, 10);
        System.out.printf("getAndSet-->[value]:%s; [atomicIntegerArray]:%s \n", value, atomicIntegerArray);
        value = atomicIntegerArray.getAndIncrement(1);
        System.out.printf("getAndIncrement-->[value]:%s; [atomicIntegerArray]:%s \n", value, atomicIntegerArray);
        value = atomicIntegerArray.getAndAdd(0, 10);
        System.out.printf("getAndAdd-->[value]:%s; [atomicIntegerArray]:%s \n", value, atomicIntegerArray);
    }

    /**
     * AtomicReference Demo
     */
    static void atomicReferenceDemo() {
        PrintUtil.printDivider("AtomicReference Demo");
        final AtomicReference<Person> atomicReference = new AtomicReference<>();
        // 调用VarHandle相关方法
        Person p1 = new Person();
        p1.setAge(20);
        p1.setName("张三");
        atomicReference.set(p1);
        System.out.printf("before compareAndSet-->[name]:%s\n", atomicReference.get().getName());
        System.out.printf("before compareAndSet-->[age]:%s\n", atomicReference.get().getAge());

        Person p2 = new Person();
        p2.setAge(18);
        p2.setName("李四");
        atomicReference.compareAndSet(p1, p2);
        System.out.printf("compareAndSet-->[name]:%s\n", atomicReference.get().getName());
        System.out.printf("compareAndSet-->[age]:%s", atomicReference.get().getAge());
    }

    /**
     * ABA 问题
     */
    static void ABADemo() {
        PrintUtil.printDivider("ABA 问题");
        final AtomicInteger atomicInteger = new AtomicInteger(20);
        // 线程01
        new Thread(() -> {
            System.out.printf("%s\t Tread Start!\n", Thread.currentThread().getName());
            // 暂停线程3秒，让线程02同时启动
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean flag = atomicInteger.compareAndSet(20, 21);
            if (flag) {
                flag = atomicInteger.compareAndSet(21, 20);
                if (flag) {
                    System.out.printf("%s\t 完成ABA操作:20->21->20\n", Thread.currentThread().getName());
                }
            }

        }, "Thread01").start();

        // 线程02
        new Thread(() -> {
            System.out.printf("%s\t Tread Start!\n", Thread.currentThread().getName());
            // 暂停10秒钟线程02，保证线程01完成了一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 此处认为值并无变化，修改成功
            // ABA实际上已经变化过一次了，部分业务场景中可能不应该允许成功
            // 简单判断值在需要判断是否有改变的场景不适用，例如判断是否有变化(修改过)，判断是否已存在修改等
            boolean flag = atomicInteger.compareAndSet(20, 30);
            System.out.printf("%s\t compareAndSet result=%s, value = %s", Thread.currentThread().getName(), flag, atomicInteger.get());
        }, "Thread02").start();
    }

    /**
     * 使用AtomicStampedReference 解决ABA问题
     * <p>对比ABADemo方法进行理解，主要是stamp的变化理解</p>
     */
    static void atomicStampedReferenceDemo() {
        PrintUtil.printDivider("使用AtomicStampedReference 解决ABA 问题");
        int initialStamp = 1;
        int value = 20;
        final AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(value, initialStamp);

        // 线程01
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            int stamp = atomicStampedReference.getStamp();
            System.out.printf("%s\t第1次的版本号=%s\n", threadName, atomicStampedReference.getStamp());
            // 暂停1秒钟线程01，保证线程02拿到版本号与线程01相同，即两个线程同时已启动
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 让线程01进行完整ABA操作，此时线程02模拟处于等待状态
            boolean flag = atomicStampedReference.compareAndSet(20, 21,
                    stamp, stamp + 1);
            System.out.printf("%s\t第1次compareAndSet\tflag=%s\t值=%s\t实际版本=%s\n", threadName,
                    flag, atomicStampedReference.getReference(), atomicStampedReference.getStamp());
            stamp = atomicStampedReference.getStamp();
            flag = atomicStampedReference.compareAndSet(21, 20, stamp,
                    stamp + 1);
            System.out.printf("%s\t第2次compareAndSet\tflag=%s\t值=%s\t实际版本=%s\n", threadName,
                    flag, atomicStampedReference.getReference(), atomicStampedReference.getStamp());
        }, "Thread01").start();

        // 线程02
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            int stamp = atomicStampedReference.getStamp();
            System.out.printf("%s\t第1次的版本号=%s\n", threadName, atomicStampedReference.getStamp());
            // 暂停3秒钟线程02，保证线程01完成ABA操作
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 暂停结束，即代表某一个线程完成了ABA操作，但是改线程版本还是第一次获取的
            PrintUtil.printDivider();
            System.out.printf("%s\t等待ABA操作完成时\t 值=%s\t实际版本=%s\n", threadName,
                    atomicStampedReference.getReference(), atomicStampedReference.getStamp());
            // 这时的flag为false，
            // 这里不使用atomicStampedRef.getStamp()重新赋值，便于观察ABA之后的版本差异，这样才能CAS失败
            boolean flag = atomicStampedReference.compareAndSet(20, 31,
                    stamp, stamp + 1);
            System.out.printf("%s\t进行compareAndSet\t flag=%s\t值=%s\t版本=%s\n", threadName,
                    flag, atomicStampedReference.getReference(), stamp);

            // 如果使用最新的stamp，即可成功，需要理解版本如何增加
//            flag = atomicStampedReference.compareAndSet(20, 31,
//                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
//            System.out.printf("%s\t进行compareAndSet\t flag=%s\t值=%s\t版本=%s\n", threadName,
//                    flag, atomicStampedReference.getReference(), atomicStampedReference.getStamp());
        }, "Thread02").start();
    }
}
